package com.quanlychitieunhom.Home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.quanlychitieunhom.GroupList.UI.Display.DanhSachNhom;
import com.quanlychitieunhom.R;

public class TrangChuTruongNhom extends AppCompatActivity {
    NavigationView navigationView;
    Button btnOpenDy1, btnOpenDy2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_dy_frg);
        addControls();
        addEvents();
    }
    private void addControls(){
        navigationView = (NavigationView) findViewById(R.id.nav_drawer);
        btnOpenDy1 =(Button)findViewById(R.id.btnOpenDy1) ;
        btnOpenDy2 =(Button)findViewById(R.id.btnOpenDy2) ;
    }
    private void addEvents(){
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                // chèn code gọi Fragment tương ứng cho mỗi item khi được click vào trên Navigation
//                return false;
//            }
//        });
        btnOpenDy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new TaoNhom());
            }
        });
        btnOpenDy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new DanhSachNhom());
            }
        });
    }

    public void loadFragment (Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.frameDyFrag,fragment);
        ft.commit();
    }
}
