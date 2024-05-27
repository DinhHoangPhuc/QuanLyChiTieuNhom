package com.quanlychitieunhom.Thai;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;
import com.quanlychitieunhom.R;

public class TrangChuTruongNhom extends AppCompatActivity {
    NavigationView navigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu_truongnhom);
        addControls();
        addEvents();
    }
    private void addControls(){
        navigationView = (NavigationView) findViewById(R.id.nav_drawer);
    }
    private void addEvents(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // chèn code gọi Fragment tương ứng cho mỗi item khi được click vào trên Navigation
                return false;
            }
        });
    }
}
