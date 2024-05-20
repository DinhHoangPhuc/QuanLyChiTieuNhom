package com.quanlychitieunhom;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class ThongKe extends AppCompatActivity {

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_ke);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getControl();

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 500));
        entries.add(new BarEntry(1, 1000));
        entries.add(new BarEntry(2, 1500));
        // Add more entries as needed

        BarDataSet dataSet = new BarDataSet(entries, "Weekly Spendings");
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.invalidate(); // refresh
    }

    void getControl(){
        barChart = findViewById(R.id.barChart);
    }
}