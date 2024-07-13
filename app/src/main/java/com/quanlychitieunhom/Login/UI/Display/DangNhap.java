package com.quanlychitieunhom.Login.UI.Display;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.quanlychitieunhom.Home.UI.Display.TrangChu;
import com.quanlychitieunhom.Login.UI.State.LoginIntent;
import com.quanlychitieunhom.Login.UI.State.LoginModel;
import com.quanlychitieunhom.Login.UI.State.LoginState;
import com.quanlychitieunhom.Login.UI.State.LoginViewModel;
import com.quanlychitieunhom.R;
import com.quanlychitieunhom.Register.UI.Display.DangKy;

public class DangNhap extends AppCompatActivity {

    TextView txtQuenMatKhau;
    TextView txtDangKy;
    Button btnDangNhap;
    EditText edtTaiKhoan;
    EditText edtMatKhau;
    ProgressBar progressBar;

    LoginViewModel loginViewModel;

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

        checkLogin();

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getLoginViewState().observe(this, loginViewState -> {
            if(loginViewState.getLoginState() == LoginState.SUCCESS) {
                progressBar.setVisibility(View.GONE);
//                Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();

                setToken(loginViewState.getToken());
                setRefreshToken(loginViewState.getRefreshToken());
                setUsername(loginViewState.getUsername());
//                setExpireTime(decodeExpireTime(loginViewState.getToken()));

                Intent intent = new Intent(DangNhap.this, TrangChu.class);
                startActivity(intent);
            } else if(loginViewState.getLoginState() == LoginState.SENDING) {
                progressBar.setVisibility(View.VISIBLE);
//                Toast.makeText(DangNhap.this, "Đang xử lý", Toast.LENGTH_LONG).show();
            }else if(loginViewState.getLoginState() == LoginState.ERROR) {
                progressBar.setVisibility(View.GONE);
                showUnauthorizeDialog();
//                Toast.makeText(DangNhap.this, loginViewState.getErrorMessage(), Toast.LENGTH_LONG).show();
            }
        });

        handleLogin();

    }

    private void handleLogin() {
        btnDangNhap.setOnClickListener(v -> {
            LoginModel loginModel = getDataFromUI();
            if(checkData(loginModel)) {
                loginViewModel.processLogin(LoginIntent.CLICK_LOGIN_BUTTON, loginModel);
            }
        });
    }

    private LoginModel getDataFromUI() {
        LoginModel loginModel = new LoginModel();
        loginModel.setUsername(edtTaiKhoan.getText().toString());
        loginModel.setPassword(edtMatKhau.getText().toString());
        return loginModel;
    }

    private boolean checkData(LoginModel loginModel) {
        if(loginModel.getUsername().isEmpty() || loginModel.getPassword().isEmpty()){
            Toast.makeText(DangNhap.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setToken(String token) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = getApplicationContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.commit();
        Log.d("Set token successfully in login activity", token);
    }

    private void setRefreshToken(String refreshToken) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = getApplicationContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("refreshToken", refreshToken);
        editor.commit();
    }

    private long decodeExpireTime(String token) {
        String[] parts = token.split("\\.");
        String payload = parts[1];
        byte[] data = Base64.decode(payload, Base64.DEFAULT);
        String text = new String(data);
        String[] parts2 = text.split(",");
        String expireTime = parts2[2].split(":")[1];
        expireTime = expireTime.replaceAll("[^0-9]", ""); // Remove non-numeric characters
        return Long.parseLong(expireTime);
    }

//    private void setExpireTime(long expireTime) {
//        SharedPreferences sharedPreferences;
//        SharedPreferences.Editor editor;
//        sharedPreferences = getApplicationContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//        editor.putLong("expireTime", expireTime);
//        editor.apply();
//        editor.commit();
//    }

    private void setUsername(String username) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = getApplicationContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
        editor.commit();
    }

    private void checkLogin() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        if(!token.isEmpty()) {
            Log.d("Login: Token is not empty", token);

            Intent intent = new Intent(DangNhap.this, TrangChu.class);
            startActivity(intent);
        }
        else {
            Log.d("Login: Token is empty", token);
        }
    }

    private void showUnauthorizeDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Đăng nhập thất bại")
                .setMessage("Tài khoản hoặc mật khẩu không đúng")
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    void getControl(){
        txtQuenMatKhau = findViewById(R.id.tvForgotPassword);
        txtDangKy = findViewById(R.id.tvSignUp);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        edtTaiKhoan = findViewById(R.id.edtUsername);
        edtMatKhau = findViewById(R.id.edtPassword);
        progressBar = findViewById(R.id.progressBar);
    }
}