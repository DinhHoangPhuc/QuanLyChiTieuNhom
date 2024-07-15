package com.quanlychitieunhom.CreateChi.UI.Display;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.quanlychitieunhom.CreateChi.UI.State.ChiModel;
import com.quanlychitieunhom.CreateChi.UI.State.CreateChiViewModel;
import com.quanlychitieunhom.R;
import com.quanlychitieunhom.Uitls.SharedReferenceUtils;
import com.quanlychitieunhom.Uitls.StateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ChiFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etSoTienChi, etNgayChi, etGhiChuChi;
    private ImageButton btnSoTienChi, btnChonNgayChi;
    private ImageView ivAnhChi;
    private Button btnLuuChi;

    private String BASE_URL = "http://10.0.2.2:8080/api/chi/";
    private String token;
    private String refreshToken;
    private int nhomId;
    private CreateChiViewModel createChiViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_chi, container, false);

        getControl(view);

        getTokenAndRefreshToken();

        getNhomID();

        btnSoTienChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSoTienChi.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etSoTienChi, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        btnChonNgayChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        ivAnhChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        btnLuuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                saveData();
                createChi();
            }
        });

        return view;
    }

    private void getControl(View view) {
        etSoTienChi = view.findViewById(R.id.et_so_tien_chi);
        etNgayChi = view.findViewById(R.id.et_ngay_chi);
        etGhiChuChi = view.findViewById(R.id.et_ghi_chu_chi);
        btnSoTienChi = view.findViewById(R.id.btn_so_tien_chi);
        btnChonNgayChi = view.findViewById(R.id.btn_chon_ngay_chi);
        ivAnhChi = view.findViewById(R.id.iv_anh_chi);
        btnLuuChi = view.findViewById(R.id.btn_luu_chi);
    }

    private void getNhomID() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("dataNhom", MODE_PRIVATE);
        nhomId = sharedPreferences.getInt("nhomID", -1); // Default to 1 if not found
        Log.d("QuyId thu", String.valueOf(nhomId));
    }

    private void getTokenAndRefreshToken() {
        token = SharedReferenceUtils.getAccessToken(requireContext());
        refreshToken = SharedReferenceUtils.getRefreshToken(requireContext());
    }

    private void createChi() {
        try {
            createChiViewModel = new ViewModelProvider(requireActivity()).get(CreateChiViewModel.class);
            createChiViewModel.createChi(createChiModel(), refreshToken, token, requireContext());
            createChiViewModel.getChiViewStateMutableLiveData().observe(getViewLifecycleOwner(), thuModel -> {
                if(thuModel.getState() == StateUtil.SUCCESS) {
                    showDialog("Thành công", "Tạo chi thành công");
                } else if (thuModel.getState() == StateUtil.ERROR){
                    showDialog("Thất bại", "Tạo chi thất bại");
                } else {
                    Toast.makeText(requireContext(), "Đang xử lý...", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (ParseException e) {
            showDialog("Lỗi", e.getMessage());
            Log.d("Error parse ngayChi", e.getMessage());
            e.printStackTrace();
        }
    }

    private ChiModel createChiModel() throws ParseException {
        if(checkInput()) {
            String soTien = etSoTienChi.getText().toString();
            String ngayChi = etNgayChi.getText().toString();
            String ghiChu = etGhiChuChi.getText().toString();
            return new ChiModel(nhomId, Integer.parseInt(soTien), ngayChi, ghiChu);
        }
        return null;
    }

    private boolean checkInput() {
        String soTien = etSoTienChi.getText().toString();
        String ngayChi = etNgayChi.getText().toString();
        String ghiChu = etGhiChuChi.getText().toString();

        return !soTien.isEmpty() && !ngayChi.isEmpty() && !ghiChu.isEmpty();
    }

    private void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                        etNgayChi.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                ivAnhChi.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveData() {
        String soTienChi = etSoTienChi.getText().toString();
        String ngayChi = etNgayChi.getText().toString();
        String ghiChuChi = etGhiChuChi.getText().toString();

        if (soTienChi.isEmpty() || ngayChi.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("soTien", soTienChi);
            requestBody.put("ngayChi", ngayChi);
            requestBody.put("moTa", ghiChuChi);
            requestBody.put("nhomId", nhomId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String urlLuuChi = BASE_URL + "them";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, urlLuuChi, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getActivity(), "Lưu thông tin thành công!", Toast.LENGTH_SHORT).show();
                        // Thực hiện các xử lý khác sau khi lưu thành công nếu cần
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("Error", error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsonObjectRequest);
    }
}
