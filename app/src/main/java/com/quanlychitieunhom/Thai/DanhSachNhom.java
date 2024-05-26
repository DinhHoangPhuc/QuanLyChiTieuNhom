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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.quanlychitieunhom.R;

import java.util.ArrayList;

public class DanhSachNhom extends AppCompatActivity {
    ListView lvNhom;
    Button taonhom, taianh;
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
        taianh = findViewById(R.id.btnChonHinhNen);
    }
    private void addEvents(){
        lsNhom.add(new Nhom(R.drawable.ic_launcher_foreground,"Thái","Nhóm trưởng"));

        customAdapterDanhSachNhom = new CustomAdapterDanhSachNhom(DanhSachNhom.this,R.layout.layout_item_thanhvien, lsNhom);
        lvNhom.setAdapter(customAdapterDanhSachNhom);
        taonhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
            }
        });

        taianh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
