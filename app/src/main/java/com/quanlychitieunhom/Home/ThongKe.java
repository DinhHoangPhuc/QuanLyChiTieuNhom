package com.quanlychitieunhom.Home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThongKe extends AppCompatActivity {

    BarChart barChart;
    ArrayList<BarEntry> entries = new ArrayList<>();

    ArrayList<ThongKeChiTuan> thongKeChiTuans = new ArrayList<>();

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

        //goi api
        try {
            callApi();
        } catch (InterruptedException e) {
            Toast.makeText(ThongKe.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    void callApi() throws InterruptedException {
        String url = "http://10.0.2.2:8080/api/chi/thongKeChiTuan?nhomId=4";

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

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
                                    ThongKeChiTuan thongKeChiTuan = new ThongKeChiTuan(ngayChi, soTien);
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