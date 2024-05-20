package com.quanlychitieunhom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DangKy extends AppCompatActivity {

    EditText edtHoTen;
    EditText edtEmail;
    EditText edtSoDienThoai;
    EditText edtTenDangNhap;
    EditText edtMatKhau;
    EditText edtNhapLaiMatKhau;
    TextView txtDangNhap;
    Button btnDangKy;

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

        txtDangNhap.setOnClickListener(v -> {
            Intent intent = new Intent(DangKy.this, DangNhap.class);
            startActivity(intent);
        });
    }

    void getControl(){
        edtHoTen = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtSoDienThoai = findViewById(R.id.edtPhoneNumber);
        edtTenDangNhap = findViewById(R.id.edtUsername);
        edtMatKhau = findViewById(R.id.edtPassword);
        edtNhapLaiMatKhau = findViewById(R.id.edtConfirmPassword);
        txtDangNhap = findViewById(R.id.tvSignIn);
        btnDangKy = findViewById(R.id.btnDangKy);
    }
}