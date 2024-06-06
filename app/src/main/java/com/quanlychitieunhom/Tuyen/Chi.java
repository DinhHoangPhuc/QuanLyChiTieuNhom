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

public class Chi extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText etSoTienChi, etNgayChi, etGhiChuChi;
    private ImageButton btnSoTienChi, btnChonNgayChi;
    private ImageView ivAnhChi;
    private Button btnLuuChi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi);

        etSoTienChi = findViewById(R.id.et_so_tien_chi);
        etNgayChi = findViewById(R.id.et_ngay_chi);
        etGhiChuChi = findViewById(R.id.et_ghi_chu_chi);
        btnSoTienChi = findViewById(R.id.btn_so_tien_chi);
        btnChonNgayChi = findViewById(R.id.btn_chon_ngay_chi);
        ivAnhChi = findViewById(R.id.iv_anh_chi);
        btnLuuChi = findViewById(R.id.btn_luu_chi);

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

        ivAnhChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
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

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ivAnhChi.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

        // Thực hiện lưu thông tin
        Toast.makeText(this, "Lưu thông tin thành công!", Toast.LENGTH_SHORT).show();
    }
}
