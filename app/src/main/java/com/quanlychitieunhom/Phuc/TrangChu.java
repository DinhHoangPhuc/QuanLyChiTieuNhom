package com.quanlychitieunhom.Phuc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.quanlychitieunhom.Ngan.ChiaDeuFragment;
import com.quanlychitieunhom.Ngan.HoanTienFragment;
import com.quanlychitieunhom.Ngan.TVMDongFragment;
import com.quanlychitieunhom.QuocAnh.FragAdd_Notice;
import com.quanlychitieunhom.QuocAnh.FragInvite_QR;
import com.quanlychitieunhom.QuocAnh.FragNotice;
import com.quanlychitieunhom.R;
import com.quanlychitieunhom.Thai.DanhSachNhom;
import com.quanlychitieunhom.Tuyen.ChiFragment;
import com.quanlychitieunhom.Tuyen.ThuFragment;

public class TrangChu extends AppCompatActivity {

//    Button btnThongKe;
//    Button btnThongKeThang;
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    int nhomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_chu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getCOntrol();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.NhapThu){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ThuFragment()).commit();
            } else if (item.getItemId() == R.id.Home) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DanhSachNhom()).commit();
            } else if (item.getItemId() == R.id.ThongKeChiTuan) {
                Intent intent = new Intent(TrangChu.this, ThongKe.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.ThongKeChiThang) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ThongkeChiThangFragment()).commit();
            } else if (item.getItemId() == R.id.NhapChi) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChiFragment()).commit();
            } else if (item.getItemId() == R.id.ChiaTien) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChiaDeuFragment()).commit();
            } else if (item.getItemId() == R.id.NopTienVaoNhom) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TVMDongFragment()).commit();
            } else if (item.getItemId() == R.id.ChiaTienGiaiTan) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HoanTienFragment()).commit();
            } else if (item.getItemId() == R.id.TaoLoiMoi) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragInvite_QR()).commit();
            } else if (item.getItemId() == R.id.TaoThongbao) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragAdd_Notice(nhomId)).commit();
            } else if (item.getItemId() == R.id.XemThongBao) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragNotice(nhomId)).commit();
            }
            return false;
        });

        NhomViewModel nhomViewModel = new ViewModelProvider(this).get(NhomViewModel.class);
        nhomViewModel.getNhomID().observe(this, integer -> {
            if (integer != 0) { // Check if a group is selected
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QuyFragment(integer)).commit();
                nhomId = integer;
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DanhSachNhom()).commit();
            }
        });

//        btnThongKe.setOnClickListener(v -> {
//            Intent intent = new Intent(TrangChu.this, ThongKe.class);
//            startActivity(intent);
//        });
//
//        btnThongKeThang.setOnClickListener(v -> {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ThongkeChiThangFragment()).commit();
//        });

//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QuyFragment()).commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DanhSachNhom()).commit();
    }


    private void getCOntrol() {
//        btnThongKe = findViewById(R.id.btnThongKe);
        frameLayout = findViewById(R.id.fragment_container);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
//        btnThongKeThang = findViewById(R.id.btnThongKeThang);
        navigationView = findViewById(R.id.nav_drawer);
        drawerLayout = findViewById(R.id.main);
        toolbar = findViewById(R.id.toolbar);
    }
}