package com.quanlychitieunhom.Thai;


import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.quanlychitieunhom.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


public class TaoNhom extends Fragment {

    Button btnDy1;
    EditText edtIp;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private static final int MY_REQUEST_CODE = 10;

    private ImageView imgAvatar;
    EditText edtTenNhom, edtMoTa;
    Button btnTaiAnh;

    ImageButton btnTaoNhom;
    Uri mUri;
    String urlTaoNhom = "http://192.168.1.10:8080/api/nhom/taoNhom";
    String token;
    String username;
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
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            imgAvatar.setImageBitmap(bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
    );
    public TaoNhom() {
        // Required empty public constructor
    }

    public static TaoNhom newInstance(String param1, String param2) {
        TaoNhom fragment = new TaoNhom();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("dataLogin", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        username = sharedPreferences.getString("username", "");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.layout_sheet_taonhom, container, false);
        btnTaiAnh = view.findViewById(R.id.btnChonHinhNen);
        imgAvatar = view.findViewById(R.id.imgAnhNhom);
        btnTaoNhom = view.findViewById(R.id.btnTaoNhom);
        edtTenNhom = view.findViewById(R.id.edtTenNhom);
        edtMoTa = view.findViewById(R.id.edtMoTa);
        btnTaiAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
        btnTaoNhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUri == null) {
                    Toast.makeText(getActivity(), "Vui lòng chọn ảnh", Toast.LENGTH_SHORT).show();
                    return;
                }
                submitThemNhom();
            }
        });


        return view;
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

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, MY_REQUEST_CODE);
            } else {
                openGallery();
            }
        } else {
            openGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != MY_REQUEST_CODE) {
            openGallery();
        }

    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityLauncher.launch(Intent.createChooser(intent, "Chọn ảnh"));
    }
}


