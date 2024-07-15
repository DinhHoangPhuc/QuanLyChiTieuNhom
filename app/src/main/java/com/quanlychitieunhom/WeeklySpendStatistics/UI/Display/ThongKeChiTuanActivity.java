package com.quanlychitieunhom.WeeklySpendStatistics.UI.Display;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.quanlychitieunhom.R;
import com.quanlychitieunhom.Uitls.SharedReferenceUtils;
import com.quanlychitieunhom.Uitls.StateUtil;
import com.quanlychitieunhom.WeeklySpendStatistics.UI.State.ThongKeChiTuanModel;
import com.quanlychitieunhom.WeeklySpendStatistics.UI.State.ThongKeChiTuanViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThongKeChiTuanActivity extends AppCompatActivity {

    private BarChart barChart;
    private ArrayList<BarEntry> entries = new ArrayList<>();
    private ArrayList<ThongKeChiTuanModel> thongKeChiTuans = new ArrayList<>();

    private String token, refreshToken;
    private int quyId;
    private ThongKeChiTuanViewModel thongKeChiTuanViewModel;

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

        getTokenAndRefreshToken();

        getNhomID();

        getThongKeChiTuan();

        //goi api
//        try {
//            callApi();
//        } catch (InterruptedException e) {
//            Toast.makeText(ThongKeChiTuanActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
    }

    private void getNhomID() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("dataNhom", MODE_PRIVATE);
        quyId = sharedPreferences.getInt("nhomID", -1); // Default to 1 if not found
        Log.d("QuyId thu", String.valueOf(quyId));
    }

    private void getTokenAndRefreshToken() {
        token = SharedReferenceUtils.getAccessToken(this);
        refreshToken = SharedReferenceUtils.getRefreshToken(this);
    }

    private void getThongKeChiTuan() {
        thongKeChiTuanViewModel = new ViewModelProvider(this).get(ThongKeChiTuanViewModel.class);
        thongKeChiTuanViewModel.getThongKeChiTuan(quyId, token, refreshToken, this);
        thongKeChiTuanViewModel.getThongKeChiTuanLiveData().observe(this, thongKeChiTuanModels -> {
            if (thongKeChiTuanModels.getStateUtil() == StateUtil.SUCCESS) {
                thongKeChiTuans.addAll(thongKeChiTuanModels.getData());
                for (int i = 0; i < thongKeChiTuans.size(); i++) {
                    entries.add(new BarEntry((float) i, (float) thongKeChiTuans.get(i).getSoTien()));
                }

                BarDataSet dataSet = new BarDataSet(entries, "Thống kê chi tiêu tuần");
                BarData barData = new BarData(dataSet);
                barChart.setData(barData);

                ValueFormatter formatter = new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis) {
                        return thongKeChiTuans.get((int) value).getNgayChi();
                    }
                };

                XAxis xAxis = barChart.getXAxis();
                xAxis.setGranularity(1f);
                xAxis.setGranularityEnabled(true);
                xAxis.setValueFormatter(formatter);

                barChart.invalidate();
            } else if (thongKeChiTuanModels.getStateUtil() == StateUtil.ERROR){
                showDialog("Lỗi", "Không thể lấy dữ liệu");
            }
        });
    }

    private void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    void callApi() throws InterruptedException {
        String url = "http://10.0.2.2:8080/api/chi/thongKeChiTuan?nhomId=4";

//        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
//        String token = sharedPreferences.getString("token", "");

        if(!token.isEmpty()){
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            //lặp qua từng phần tử của mảng json
                            for(int i=0; i<response.length(); i++){
                                try {
                                    //lấy từng phần tử của mảng json
                                    JSONObject spending = response.getJSONObject(i);

                                    //lấy ra các giá trị của từng phần tử
                                    String ngayChi = spending.getString("ngayChi");
                                    int soTien = spending.getInt("soTien");

                                    //tạo một đối tượng thongKeChiTuan và thêm vào mảng thongKeChiTuans
                                    ThongKeChiTuanModel thongKeChiTuan = new ThongKeChiTuanModel(ngayChi, soTien);
                                    thongKeChiTuans.add(thongKeChiTuan);

                                    //thêm dữ liệu vào biểu đồ
                                    entries.add(new BarEntry((float) i, (float) soTien));
//                                    Toast.makeText(ThongKe.this, thongKeChiTuan.getNgayChi() + " " + thongKeChiTuan.getTongTien(), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                            //set dữ liệu cho biểu đồ
                            BarDataSet dataSet = new BarDataSet(entries, "Thống kê chi tiêu tuần");
                            BarData barData = new BarData(dataSet);
                            barChart.setData(barData);

                            //set tên cho các cột
                            ValueFormatter formatter = new ValueFormatter() {
                                @Override
                                public String getAxisLabel(float value, AxisBase axis) {
                                    return thongKeChiTuans.get((int) value).getNgayChi();
                                }
                            };

                            //set dữ liệu cho trục x
                            XAxis xAxis = barChart.getXAxis();

                            //ngăn trùng lặp label trên trục x
                            xAxis.setGranularity(1f);
                            xAxis.setGranularityEnabled(true);
                            xAxis.setValueFormatter(formatter);

                            //làm mới biểu đồ
                            barChart.invalidate();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {

                //gửi token lên server để xác thực api
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonArrayRequest);
        }
    }

    void getControl(){
        barChart = findViewById(R.id.barChart);
    }
}