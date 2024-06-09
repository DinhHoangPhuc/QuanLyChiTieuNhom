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

public class ChiActivity extends AppCompatActivity {


    private EditText etSoTienChi, etNgayChi, etGhiChuChi;
    private ImageButton btnSoTienChi, btnChonNgayChi;
    private Button btnLuuChi;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi);

        etSoTienChi = findViewById(R.id.et_so_tien_chi);
        etNgayChi = findViewById(R.id.et_ngay_chi);
        etGhiChuChi = findViewById(R.id.et_ghi_chu_chi);
        btnSoTienChi = findViewById(R.id.btn_so_tien_chi);
        btnChonNgayChi = findViewById(R.id.btn_chon_ngay_chi);
        btnLuuChi = findViewById(R.id.btn_luu_chi);

        Retrofit retrofit = RetrofitClient.getClient("http://localhost:8080/");
        apiService = retrofit.create(ApiService.class);

        //bấm nút btn chọn sẽ hien bang chọn so tien
        btnSoTienChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSoTienChi.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(etSoTienChi, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        btnChonNgayChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        btnLuuChi.setOnClickListener(new View.OnClickListener() {
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
                        etNgayChi.setText(selectedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }


    private void saveData() {
        String soTienChi = etSoTienChi.getText().toString();
        String ngayChi = etNgayChi.getText().toString();
        String ghiChuChi = etGhiChuChi.getText().toString();

        // Kiểm tra các trường nhập liệu
        if (soTienChi.isEmpty() || ngayChi.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng Chi
        Chi chi = new Chi();
        chi.setMo_ta(ghiChuChi);
        chi.setNgay_chi(ngayChi);
        chi.setSo_tien(Integer.parseInt(soTienChi));
        chi.setNhom_id(getNhomIdFromUserSession());

        apiService.createChi(chi).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ChiActivity.this, "Lưu thông tin thành công!", Toast.LENGTH_SHORT).show();
                    //finish();
                } else {
                    Toast.makeText(ChiActivity.this, "Lưu thông tin thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ChiActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //lay nhomid tu session
    private int getNhomIdFromUserSession()
    {
        //example
        return 1;
    }
}
