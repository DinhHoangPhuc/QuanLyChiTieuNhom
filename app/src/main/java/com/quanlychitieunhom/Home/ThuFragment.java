package com.quanlychitieunhom.Home;

import android.app.DatePickerDialog;
import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.quanlychitieunhom.SharedViewModel.NhomViewModel;
import com.quanlychitieunhom.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ThuFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etSoTien, etNgayThu, etGhiChu;
    private ImageButton btnSoTien, btnChonNgay;
    private ImageView ivAnh;
    private Button btnLuu;

    private String BASE_URL = "http://10.0.2.2:8080/api/thu/";
    private String token;
    private int nhomId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_thu, container, false);

        etSoTien = view.findViewById(R.id.et_so_tien);
        etNgayThu = view.findViewById(R.id.et_ngay_thu);
        etGhiChu = view.findViewById(R.id.et_ghi_chu);
        btnSoTien = view.findViewById(R.id.btn_so_tien);
        btnChonNgay = view.findViewById(R.id.btn_chon_ngay);
        ivAnh = view.findViewById(R.id.iv_anh);
        btnLuu = view.findViewById(R.id.btn_luu);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
//        nhomId = sharedPreferences.getInt("nhomId", 1); // Default to 1 if not found

        NhomViewModel nhomViewModel = new ViewModelProvider(requireActivity()).get(NhomViewModel.class);
        nhomId = nhomViewModel.getNhomID().getValue();

        btnSoTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSoTien.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etSoTien, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        btnChonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        ivAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        return view;
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
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        etNgayThu.setText(selectedDate);
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
                ivAnh.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveData() {
        String soTien = etSoTien.getText().toString();
        String ngayThu = etNgayThu.getText().toString();
        String ghiChu = etGhiChu.getText().toString();
        // Kiểm tra các trường nhập liệu
        if (soTien.isEmpty() || ngayThu.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thực hiện lưu thông tin
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("soTien", soTien);
            requestBody.put("moTa", ghiChu);
            requestBody.put("ngayThu", ngayThu);
            requestBody.put("nhomId", nhomId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String urlLuuThu = BASE_URL + "taothu";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, urlLuuThu, requestBody, new Response.Listener<JSONObject>() {

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
