package com.quanlychitieunhom.Phuc;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

public class DangNhap extends AppCompatActivity {

    TextView txtQuenMatKhau;
    TextView txtDangKy;
    Button btnDangNhap;
    EditText edtTaiKhoan;
    EditText edtMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getControl();

        txtQuenMatKhau.setOnClickListener(v -> {
        });

        txtDangKy.setOnClickListener(v -> {
            Intent intent = new Intent(DangNhap.this, DangKy.class);
            startActivity(intent);
        });

        btnDangNhap.setOnClickListener(v -> {
            if(edtTaiKhoan.getText().toString().isEmpty() || edtMatKhau.getText().toString().isEmpty()){
                Toast.makeText(DangNhap.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            } else {
                callLoginApi(edtTaiKhoan.getText().toString(), edtMatKhau.getText().toString());
                Intent intent = new Intent(DangNhap.this, TrangChu.class);
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        if(!token.isEmpty()) {
            Intent intent = new Intent(DangNhap.this, TrangChu.class);
            startActivity(intent);
        }
    }

    void callLoginApi(String username, String password){
        String url = "http://10.0.2.2:8080/api/auth/login";

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", username);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestBody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String token = response.optString("token");
                        String username = response.optString("username");
                        String error = response.optString("error");
                        if(!token.isEmpty()) {
                            Toast.makeText(DangNhap.this, token, Toast.LENGTH_LONG).show();
//                            Context context = getActivity()
                            SharedPreferences sharedPreferences;
                            SharedPreferences.Editor editor;
                            sharedPreferences = getApplicationContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("token", token);
                            editor.putString("username", username);
                            editor.apply();
                            editor.commit();
                            Toast.makeText(DangNhap.this, username, Toast.LENGTH_LONG).show();
//                                Toast.makeText(DangKy.this, response.getString("message"), Toast.LENGTH_LONG).show();
                        } else if(!error.isEmpty()) {
                            Toast.makeText(DangNhap.this, error, Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DangNhap.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("Error", error.getMessage());
                    }
                });

        // Add the request to the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    void getControl(){
        txtQuenMatKhau = findViewById(R.id.tvForgotPassword);
        txtDangKy = findViewById(R.id.tvSignUp);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        edtTaiKhoan = findViewById(R.id.edtUsername);
        edtMatKhau = findViewById(R.id.edtPassword);
    }
}