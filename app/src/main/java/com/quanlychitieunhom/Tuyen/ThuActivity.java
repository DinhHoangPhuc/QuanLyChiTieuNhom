package com.quanlychitieunhom.Tuyen;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Calendar;

import com.quanlychitieunhom.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ThuActivity extends AppCompatActivity {


    private EditText etSoTien, etNgayThu, etGhiChu;
    private ImageButton btnSoTien, btnChonNgay;
    private Button btnLuu;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu);

        etSoTien = findViewById(R.id.et_so_tien);
        etNgayThu = findViewById(R.id.et_ngay_thu);
        etGhiChu = findViewById(R.id.et_ghi_chu);
        btnSoTien = findViewById(R.id.btn_so_tien);
        btnChonNgay = findViewById(R.id.btn_chon_ngay);
        btnLuu = findViewById(R.id.btn_luu);

        Retrofit retrofit = RetrofitClient.getClient("http://localhost:8080/");
        apiService = retrofit.create(ApiService.class);

        btnSoTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSoTien.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(etSoTien, InputMethodManager.SHOW_IMPLICIT);
            }
        });


        btnChonNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        //bấm vào nút lưu để lưu số tiền thu
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        etNgayThu.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }


    private void saveData() {
        String soTien = etSoTien.getText().toString();
        String ngayThu = etNgayThu.getText().toString();
        String ghiChu = etGhiChu.getText().toString();

        // Kiểm tra các trường nhập liệu
        if (soTien.isEmpty() || ngayThu.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        //Tao doi tuong thu
        Thu thu = new Thu();
        thu.setMo_ta(ghiChu);
        thu.setNgay_thu(ngayThu);
        thu.setSo_tien(Integer.parseInt(soTien));
        thu.setNhom_id(getNhomIdFromUserSession());

        apiService.createThu(thu).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ThuActivity.this, "Thêm thu thành công!", Toast.LENGTH_SHORT).show();
                    //   finish();
                } else {
                    Toast.makeText(ThuActivity.this, "Thêm thu thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ThuActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //lay nhom_id tu user session
    private int getNhomIdFromUserSession() {
        //example
        return 1;
    }
}