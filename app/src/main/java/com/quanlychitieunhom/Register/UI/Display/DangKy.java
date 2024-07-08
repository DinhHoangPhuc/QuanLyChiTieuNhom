package com.quanlychitieunhom.Register.UI.Display;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import com.quanlychitieunhom.Login.UI.Display.DangNhap;
import com.quanlychitieunhom.R;
import com.quanlychitieunhom.Register.UI.State.RegisterIntent;
import com.quanlychitieunhom.Register.UI.State.RegisterModel;
import com.quanlychitieunhom.Register.UI.State.RegisterState;
import com.quanlychitieunhom.Register.UI.State.RegisterViewModel;

import java.util.Map;

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
    ProgressBar progressBar;

    String imagePath;

    RegisterViewModel registrationViewModel;

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

        registrationViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        registrationViewModel.getRegistrationModel().observe(this, viewState -> {
            if(viewState.getRegisterState() == RegisterState.SENDING) {
//                Toast.makeText(this, "Sending", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.VISIBLE);
            } else if(viewState.getRegisterState() == RegisterState.SUCCESS) {
//                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                showSuccessDialog();
                Intent intent = new Intent(DangKy.this, DangNhap.class);
                startActivity(intent);
            } else if(viewState.getRegisterState() == RegisterState.ERROR) {
                progressBar.setVisibility(View.GONE);
                showErrorMessageDialog("Lỗi server");
//                Toast.makeText(this, viewState.getRepsonseObject(), Toast.LENGTH_LONG).show();
            } else if(viewState.getRegisterState() == RegisterState.ERROR_VALIDATION) {
                progressBar.setVisibility(View.GONE);
//                Toast.makeText(this, "Validation Error", Toast.LENGTH_SHORT).show();
//                showValidationErrorDialog(viewState.getRepsonseObject());
                setErrorOnEditText(viewState.getRepsonseObject());
            }
        });

        handleRegister();
    }

    private void handleRegister() {
        btnDangKy.setOnClickListener(v -> {
            RegisterModel registerModel = getDataFromUI();
            if(checkData(registerModel)) {
                registrationViewModel.proccessIntent(RegisterIntent.CLICK_REGISTER_BUTTON, registerModel);
            }
        });
    }

    private RegisterModel getDataFromUI() {
        String hoTen = edtHoTen.getText().toString();
        String email = edtEmail.getText().toString();
        String soDienThoai = edtSoDienThoai.getText().toString();
        String tenDangNhap = edtTenDangNhap.getText().toString();
        String matKhau = edtMatKhau.getText().toString();
        return new RegisterModel(hoTen, email, soDienThoai, tenDangNhap, matKhau, imagePath);
    }

    private boolean checkData(RegisterModel registrationModel) {
        if (registrationModel.getHoTen().isEmpty() || registrationModel.getEmail().isEmpty() || registrationModel.getSdt().isEmpty() || registrationModel.getUsername().isEmpty() || registrationModel.getPassword().isEmpty() || registrationModel.getAvatar().isEmpty()) {
            Toast.makeText(this, "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!registrationModel.getPassword().equals(edtNhapLaiMatKhau.getText().toString())) {
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setErrorOnEditText(Map<String, String> errors) {
        edtSoDienThoai.setError(errors.getOrDefault("sdt", null));
        edtEmail.setError(errors.getOrDefault("email", null));
        edtTenDangNhap.setError(errors.getOrDefault("username", null));
    }

    private void showValidationErrorDialog(Map<String, String> errors) {
        StringBuilder errorMessage = new StringBuilder();
        for (Map.Entry<String, String> entry : errors.entrySet()) {
            errorMessage.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        new AlertDialog.Builder(this)
                .setTitle("Validation Error")
                .setMessage(errorMessage.toString())
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void showErrorMessageDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Lỗi")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Thành công")
                .setMessage("Đăng ký thành công")
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            Uri imageUri = data.getData();
            imgAvatar.setImageURI(imageUri);
            assert imageUri != null;
            imagePath = imageUri.getPath();
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
        progressBar = findViewById(R.id.progressBar);
    }
}