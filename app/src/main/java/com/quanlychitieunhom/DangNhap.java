package com.quanlychitieunhom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DangNhap extends AppCompatActivity {

    TextView txtQuenMatKhau;
    TextView txtDangKy;
    Button btnDangNhap;

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
    }

    void getControl(){
        txtQuenMatKhau = findViewById(R.id.tvForgotPassword);
        txtDangKy = findViewById(R.id.tvSignUp);
        btnDangNhap = findViewById(R.id.btnDangNhap);
    }
}