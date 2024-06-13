package com.quanlychitieunhom.Phuc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.quanlychitieunhom.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DangKy extends AppCompatActivity {

    EditText edtHoTen;
    EditText edtEmail;
    EditText edtSoDienThoai;
    EditText edtTenDangNhap;
    EditText edtMatKhau;
    EditText edtNhapLaiMatKhau;
    TextView txtDangNhap;
    Button btnDangKy;
    ImageView imgAvatar;

    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getControl();

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });

        txtDangNhap.setOnClickListener(v -> {
            Intent intent = new Intent(DangKy.this, DangNhap.class);
            startActivity(intent);
        });

        btnDangKy.setOnClickListener(v -> {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    callRegisterApi();
                }
            }).start();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            Uri imageUri = data.getData();
            imgAvatar.setImageURI(imageUri);
            imagePath = imageUri.getPath();
//            Toast.makeText(this, imageUri.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(this, imagePath, Toast.LENGTH_LONG).show();
        }
    }

    void callRegisterApi() {
        String hoTen = edtHoTen.getText().toString();
        String email = edtEmail.getText().toString();
        String soDienThoai = edtSoDienThoai.getText().toString();
        String tenDangNhap = edtTenDangNhap.getText().toString();
        String matKhau = edtMatKhau.getText().toString();
        String nhapLaiMatKhau = edtNhapLaiMatKhau.getText().toString();

        if(hoTen.isEmpty() || email.isEmpty() || soDienThoai.isEmpty() || tenDangNhap.isEmpty() || matKhau.isEmpty() || nhapLaiMatKhau.isEmpty()){
            Toast.makeText(this, "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        } else if(!matKhau.equals(nhapLaiMatKhau)){
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String url = "http://10.0.2.2:8080/api/auth/register";

            // Create the request body as a JSONObject
            JSONObject requestBody = new JSONObject();
            try {
                requestBody.put("hoTen", hoTen);
                requestBody.put("email", email);
                requestBody.put("sdt", soDienThoai);
                requestBody.put("username", tenDangNhap);
                requestBody.put("password", matKhau);
                requestBody.put("avatar", imagePath);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Create a JsonObjectRequest
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            String message = response.optString("message");
                            String error = response.optString("error");
                            if(!message.isEmpty()) {
                                    Toast.makeText(DangKy.this, message, Toast.LENGTH_LONG).show();

//                                Toast.makeText(DangKy.this, response.getString("message"), Toast.LENGTH_LONG).show();
                            } else if(!error.isEmpty()) {
                                    Toast.makeText(DangKy.this, error, Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DangKy.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("Error", error.getMessage());
                        }
                    });

            // Add the request to the RequestQueue
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObjectRequest);
        }
    }

    void getControl(){
        edtHoTen = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtSoDienThoai = findViewById(R.id.edtPhoneNumber);
        edtTenDangNhap = findViewById(R.id.edtUsername);
        edtMatKhau = findViewById(R.id.edtPassword);
        edtNhapLaiMatKhau = findViewById(R.id.edtConfirmPassword);
        imgAvatar = findViewById(R.id.imgAvatar);
        txtDangNhap = findViewById(R.id.tvSignIn);
        btnDangKy = findViewById(R.id.btnDangKy);
    }
}