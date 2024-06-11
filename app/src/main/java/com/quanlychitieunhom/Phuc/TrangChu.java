package com.quanlychitieunhom.Phuc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.quanlychitieunhom.R;

public class TrangChu extends AppCompatActivity {

    Button btnThongKe;
    Button btnThongKeThang;
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;

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

        btnThongKe.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChu.this, ThongKe.class);
            startActivity(intent);
        });

        btnThongKeThang.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ThongkeChiThangFragment()).commit();
        });
    }

    private void getCOntrol() {
        btnThongKe = findViewById(R.id.btnThongKe);
        frameLayout = findViewById(R.id.fragment_container);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        btnThongKeThang = findViewById(R.id.btnThongKeThang);
    }
}