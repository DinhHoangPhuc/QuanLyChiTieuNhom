package com.quanlychitieunhom.MonthlyCollectStatistics.UI.Display;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.quanlychitieunhom.MonthlyCollectStatistics.UI.State.ThongKeThuThangModel;
import com.quanlychitieunhom.MonthlyCollectStatistics.UI.State.ThongKeThuThangViewModel;
import com.quanlychitieunhom.R;
import com.quanlychitieunhom.Uitls.SharedReferenceUtils;
import com.quanlychitieunhom.Uitls.StateUtil;

import java.util.ArrayList;

public class ThongKeThuThangFragment extends Fragment {
    private BarChart barChart;
    private ArrayList<BarEntry> entries = new ArrayList<>();
    private ArrayList<ThongKeThuThangModel> thongKeThuThang = new ArrayList<>();

    private String token, refreshToken;
    private int quyId;
    private ThongKeThuThangViewModel thongKeThuThangViewModel;

    public ThongKeThuThangFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.fragment_thong_ke_thu_thang, container, false);

        barChart = view.findViewById(R.id.barChart);

        getNhomID();
        getTokenAndRefreshToken();

        getThongKeThuThang();
        handleError();

        return view;
    }

    private void getNhomID() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("dataNhom", MODE_PRIVATE);
        quyId = sharedPreferences.getInt("nhomID", -1); // Default to 1 if not found
        Log.d("QuyId thu", String.valueOf(quyId));
    }

    private void getTokenAndRefreshToken() {
        token = SharedReferenceUtils.getAccessToken(requireContext());
        refreshToken = SharedReferenceUtils.getRefreshToken(requireContext());
    }

    private void getThongKeThuThang() {
        thongKeThuThangViewModel = new ViewModelProvider(this).get(ThongKeThuThangViewModel.class);
        thongKeThuThangViewModel.getThongKeThuThang(quyId, token, refreshToken, requireContext());
        thongKeThuThangViewModel.getThongKeThuThangLiveData().observe(getViewLifecycleOwner(), thongKeThuThangResponse -> {
            if (thongKeThuThangResponse.getStateUtil() == StateUtil.SUCCESS) {
                thongKeThuThang = (ArrayList<ThongKeThuThangModel>) thongKeThuThangResponse.getData();
                for(int i = 0; i < thongKeThuThang.size(); i++) {
                    entries.add(new BarEntry(i, thongKeThuThang.get(i).getTongThu()));
                }

                BarDataSet barDataSet = new BarDataSet(entries, "Thống kê thu tháng");
                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);

                ValueFormatter formatter = new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis) {
                        return thongKeThuThang.get((int) value).getTuan() + "";
                    }
                };

                XAxis xAxis = barChart.getXAxis();
                xAxis.setGranularity(1f);
                xAxis.setGranularityEnabled(true);
                xAxis.setValueFormatter(formatter);

                barChart.invalidate();
            }
        });
    }

    private void handleError() {
        thongKeThuThangViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            showDialog("Lỗi", error);
        });
    }

    private void showDialog(String title, String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}