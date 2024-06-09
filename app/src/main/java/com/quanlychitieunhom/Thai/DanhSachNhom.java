package com.quanlychitieunhom.Thai;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.quanlychitieunhom.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachNhom extends AppCompatActivity {
    ListView lvNhom;
    Button taonhom,test;
    ArrayList<Nhom> lsNhom = new ArrayList<>();
    CustomAdapterDanhSachNhom customAdapterDanhSachNhom;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_nhom);
        addControls();
        addEvents();
    }
    private void addControls(){
        taonhom = findViewById(R.id.btn_TaoNhom);
        lvNhom = findViewById(R.id.lvDanhSachNhom);
        test = findViewById(R.id.btn_ThamGiaNhomQR);
    }
    private void addEvents(){
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallApiNhom apiService = RetrofitClient.getClient("https://localhost:8080/").create(CallApiNhom.class);
                Call<Nhom> call = apiService.getNhom();
                call.enqueue(new Callback<Nhom>() {
                    @Override
                    public void onResponse(Call<Nhom> call, Response<Nhom> response) {
                        Toast.makeText(DanhSachNhom.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                        lsNhom.add(new Nhom(R.drawable.ic_launcher_foreground,"Thái"));
                        customAdapterDanhSachNhom = new CustomAdapterDanhSachNhom(DanhSachNhom.this,R.layout.layout_item_thanhvien, lsNhom);
                        lvNhom.setAdapter(customAdapterDanhSachNhom);
                    }
                    @Override
                    public void onFailure(Call<Nhom> call, Throwable t) {
                        Toast.makeText(DanhSachNhom.this, "That bai", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        taonhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachNhom.this, ThemNhom.class);
                startActivity(intent);
            }
        });
    }
    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_sheet_taonhom);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}
