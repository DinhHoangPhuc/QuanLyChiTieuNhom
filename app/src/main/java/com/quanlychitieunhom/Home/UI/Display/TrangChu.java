package com.quanlychitieunhom.Home.UI.Display;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
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
import com.quanlychitieunhom.CreateChi.UI.Display.ChiFragment;
import com.quanlychitieunhom.Home.ChiaDeuFragment;
import com.quanlychitieunhom.GroupList.UI.Display.DanhSachNhom;
import com.quanlychitieunhom.Home.FragAdd_Notice;
import com.quanlychitieunhom.Home.FragInvite_QR;
import com.quanlychitieunhom.Home.FragNotice;
import com.quanlychitieunhom.Home.HoanTienFragment;
import com.quanlychitieunhom.Home.TVMDongFragment;
import com.quanlychitieunhom.Home.ThongKe;
import com.quanlychitieunhom.Home.ThongkeChiThangFragment;
import com.quanlychitieunhom.CreateThu.UI.Display.ThuFragment;
import com.quanlychitieunhom.Login.UI.Display.DangNhap;
import com.quanlychitieunhom.R;
import com.quanlychitieunhom.Uitls.ViewModel.NhomViewModel;

public class TrangChu extends AppCompatActivity {
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

        getControl();

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
//            if (integer != 0) { // Check if a group is selected
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QuyFragment(integer)).commit();
//                nhomId = integer;
//            } else {
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DanhSachNhom()).commit();
//            }
        });

        String token = getToken();
        String refreshToken = getRefreshToken();

        long expireTime = getExpireTime();
//        Toast.makeText(this, "expireTime: " + String.valueOf(expireTime), Toast.LENGTH_LONG).show();

        checkTokenIsEmpty(token);
        checkRefreshTokenIsEmpty(refreshToken);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DanhSachNhom()).commit();
    }

    private void getControl() {
        frameLayout = findViewById(R.id.fragment_container);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navigationView = findViewById(R.id.nav_drawer);
        drawerLayout = findViewById(R.id.main);
        toolbar = findViewById(R.id.toolbar);
    }

    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    private String getRefreshToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        return sharedPreferences.getString("refreshToken", "");
    }

    private long getExpireTime() {
        SharedPreferences sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        return sharedPreferences.getLong("expireTime", 0);
    }

    private void checkTokenIsEmpty(String token) {
        if(token.isEmpty()) {
            Intent intent = new Intent(TrangChu.this, DangNhap.class);
            startActivity(intent);
        } else {
//            Toast.makeText(this, token, Toast.LENGTH_LONG).show();
        }
    }

    private void checkRefreshTokenIsEmpty(String refreshToken) {
        if(refreshToken.isEmpty()) {
            Intent intent = new Intent(TrangChu.this, DangNhap.class);
            startActivity(intent);
        } else {
//            Toast.makeText(this, refreshToken, Toast.LENGTH_LONG).show();
        }
    }
}