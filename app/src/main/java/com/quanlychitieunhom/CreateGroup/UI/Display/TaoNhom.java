package com.quanlychitieunhom.CreateGroup.UI.Display;


import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.content.CursorLoader;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.quanlychitieunhom.CreateGroup.UI.State.CreateGroupModel;
import com.quanlychitieunhom.CreateGroup.UI.State.CreateGroupViewModel;
import com.quanlychitieunhom.R;
import com.quanlychitieunhom.Uitls.SharedReferenceUtils;
import com.quanlychitieunhom.Uitls.StateUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class TaoNhom extends Fragment {
    private static final int MY_REQUEST_CODE = 10;

    private ImageView imgAvatar;
    private EditText edtTenNhom, edtMoTa;
    private Button btnTaiAnh;
    private ImageButton btnTaoNhom;
    private Uri mUri;

    private String urlTaoNhom = "http://192.168.1.10:8080/api/nhom/taoNhom";
    private  String token;
    private String refreshToken;
    private String username;
    private String imgaePath;
    private CreateGroupViewModel createGroupViewModel;

    private ActivityResultLauncher<Intent> mActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    Log.e(TAG, "onActivityResult");
                    if (o.getResultCode() == Activity.RESULT_OK) {
                        Intent data = o.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        mUri = uri;

                        imgaePath = getRealPathFromURI(uri);

                        setAvatar(imgaePath);
                    }

                }
            }
    );

    public TaoNhom() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.layout_sheet_taonhom, container, false);

        getControl(view);

        getTokenAndRefreshTokenAndUsername();

        createGroupViewModel = new ViewModelProvider(this).get(CreateGroupViewModel.class);

        handleTaiAnh();

        handleTaoNhom();

        return view;
    }

    private void handleTaoNhom() {
        btnTaoNhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGroup();
//                if (mUri == null) {
//                    showDialog("Thông báo", "Vui lòng chọn ảnh");
//                    return;
//                }
//                submitThemNhom();
            }
        });
    }

    private void handleTaiAnh() {
        btnTaiAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void createGroup() {
        if(getCreateGroupModel() != null) {
            createGroupViewModel.createGroup(getCreateGroupModel(), refreshToken, token, requireContext());
            createGroupViewModel.getCreateGroupState().observe(getViewLifecycleOwner(), createGroupModel -> {
                if(createGroupModel.getStateUtil() == StateUtil.SUCCESS) {
                    showDialog("Thông báo", "Tạo nhóm thành công");
                } else {
                    showDialog("Thông báo", "Tạo nhóm thất bại");
                }
            });
        } else {
            showDialog("Thông báo", "Vui lòng nhập đầy đủ thông tin");
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(requireContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        assert cursor != null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void setAvatar(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .into(imgAvatar);
    }

    private CreateGroupModel getCreateGroupModel() {
        if (!validateInput()) {
            return null;
        }
        String tenNhom = edtTenNhom.getText().toString();
        String moTa = edtMoTa.getText().toString();
        return new CreateGroupModel(username, tenNhom, moTa, imgaePath);
    }

    private boolean validateInput() {
        checkInput();
        if(edtTenNhom.getError() != null || edtMoTa.getError() != null || mUri == null) {
            return false;
        }
        return true;
    }

    private void checkInput() {
        if (edtTenNhom.getText().toString().isEmpty()) {
            edtTenNhom.setError("Vui lòng nhập tên nhóm");
        }
        if (edtMoTa.getText().toString().isEmpty()) {
            edtMoTa.setError("Vui lòng nhập mô tả");
        }
        if (mUri == null) {
            showDialog("Thông báo", "Vui lòng chọn ảnh");
        }
    }

    private void getControl(View view) {
        btnTaiAnh = view.findViewById(R.id.btnChonHinhNen);
        imgAvatar = view.findViewById(R.id.imgAnhNhom);
        btnTaoNhom = view.findViewById(R.id.btnTaoNhom);
        edtTenNhom = view.findViewById(R.id.edtTenNhom);
        edtMoTa = view.findViewById(R.id.edtMoTa);
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

    private void getTokenAndRefreshTokenAndUsername() {
        token = SharedReferenceUtils.getAccessToken(requireContext());
        refreshToken = SharedReferenceUtils.getRefreshToken(requireContext());
        username = SharedReferenceUtils.getUserName(requireContext());
    }

    private void submitThemNhom() {
//        String username = "test_user1";
        String tenNhom = edtTenNhom.getText().toString();
        String moTa = edtMoTa.getText().toString();
        String hinhNhom = imgAvatar.toString();
        if (tenNhom.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập tên nhóm", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mUri);
            saveImageToInternalStorage(bitmap);
            jsonObject.put("username", username);
            jsonObject.put("tenNhom", tenNhom);
            jsonObject.put("moTa", moTa);
            jsonObject.put("hinhNhom", saveImageToInternalStorage(bitmap).toString());
            edtMoTa.setText(saveImageToInternalStorage(bitmap).toString());
            ThemNhom(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Uri saveImageToInternalStorage(Bitmap bitmap) {
        // Get the context wrapper
        ContextWrapper wrapper = new ContextWrapper(getActivity().getApplicationContext());

        // Initialize a new file instance to save bitmap object
        File file = wrapper.getDir("Images",MODE_PRIVATE);

        // Create a unique file name based on the current time
        String fileName = "Image_" + System.currentTimeMillis() + ".jpg";
        file = new File(file, fileName);

        try{
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Return the saved image Uri
        return Uri.parse(file.getAbsolutePath());
    }

    private void ThemNhom(JSONObject jsonObject) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlTaoNhom, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), imgAvatar.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityLauncher.launch(Intent.createChooser(intent, "Chọn ảnh"));
    }
}


