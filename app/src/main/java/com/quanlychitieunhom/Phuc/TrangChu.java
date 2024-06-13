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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.quanlychitieunhom.R;

public class TrangChu extends AppCompatActivity {

    Button btnThongKe;
    Button btnThongKeThang;
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

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
                Intent intent = new Intent(TrangChu.this, ThongKe.class);
                startActivity(intent);
            }
            return false;
        });

        btnThongKe.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChu.this, ThongKe.class);
            startActivity(intent);
        });

        btnThongKeThang.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ThongkeChiThangFragment()).commit();
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QuyFragment()).commit();
    }

    private void getCOntrol() {
        btnThongKe = findViewById(R.id.btnThongKe);
        frameLayout = findViewById(R.id.fragment_container);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        btnThongKeThang = findViewById(R.id.btnThongKeThang);
        navigationView = findViewById(R.id.nav_drawer);
        drawerLayout = findViewById(R.id.main);
        toolbar = findViewById(R.id.toolbar);
    }
}