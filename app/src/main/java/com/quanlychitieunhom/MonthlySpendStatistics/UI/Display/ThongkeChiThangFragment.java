package com.quanlychitieunhom.MonthlySpendStatistics.UI.Display;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.quanlychitieunhom.MonthlySpendStatistics.UI.State.ThongKeChiThangModel;
import com.quanlychitieunhom.MonthlySpendStatistics.UI.State.ThongKeChiThangViewModel;
import com.quanlychitieunhom.R;
import com.quanlychitieunhom.Uitls.SharedReferenceUtils;
import com.quanlychitieunhom.Uitls.StateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThongkeChiThangFragment extends Fragment {
    private BarChart barChart;
    private ArrayList<BarEntry> entries = new ArrayList<>();
    private ArrayList<ThongKeChiThangModel> thongKeChiThangs = new ArrayList<>();

    private String token, refreshToken;
    private int quyId;
    private ThongKeChiThangViewModel thongKeChiThangViewModel;

    public ThongkeChiThangFragment() {
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
        View view = layoutInflater.inflate(R.layout.fragment_thongke_chi_thang, container, false);

        barChart = view.findViewById(R.id.barChart);

        //lấy token và refresh token
        getTokenAndRefreshToken();

        //lấy nhóm id
        getNhomID();

        //lấy dữ liệu thống kê chi tiêu tháng
        getThongKeChiThang();

        //goi api
//        callApi();

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

    private void getThongKeChiThang() {
        thongKeChiThangViewModel = new ViewModelProvider(this).get(ThongKeChiThangViewModel.class);
        thongKeChiThangViewModel.getThongKeChiThang(quyId, token, refreshToken, requireContext());
        thongKeChiThangViewModel.getThongKeChiThangLiveData().observe(getViewLifecycleOwner(), thongKeChiThangModel -> {
            if(thongKeChiThangModel.getStateUtil() == StateUtil.SUCCESS) {
                thongKeChiThangs = (ArrayList<ThongKeChiThangModel>) thongKeChiThangModel.getData();
                for (int i = 0; i < thongKeChiThangs.size(); i++) {
                    entries.add(new BarEntry((float) i, (float) thongKeChiThangs.get(i).getTongChi()));
                }

                BarDataSet dataSet = new BarDataSet(entries, "Thống kê chi tiêu tháng");
                BarData barData = new BarData(dataSet);
                barChart.setData(barData);

                //set tên cho các cột
                ValueFormatter formatter = new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis) {
                        return thongKeChiThangs.get((int) value).getTuan() + "";
                    }
                };

                XAxis xAxis = barChart.getXAxis();
                xAxis.setGranularity(1f);
                xAxis.setGranularityEnabled(true);
                xAxis.setValueFormatter(formatter);

                barChart.invalidate();
            } else if(thongKeChiThangModel.getStateUtil() == StateUtil.ERROR) {
                showDialog("Lỗi", "Không thể lấy dữ liệu");
            }
        });
    }

    private void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
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

//    private void callApi() {
//        String url = "http://10.0.2.2:8080/api/chi/thongKeChi/chiTuanTrongThang?nhomId=4";
//
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
//        String token = sharedPreferences.getString("token", "");
//
//        if(!token.isEmpty()){
//            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
//                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//
//                        @Override
//                        public void onResponse(JSONArray response) {
//                            try {
//                                for (int i = 0; i < response.length(); i++) {
//                                    JSONObject jsonObject = response.getJSONObject(i);
//                                    ThongKeChiThangModel thongKeChiThang = new ThongKeChiThangModel();
//                                    thongKeChiThang.setTuan(jsonObject.getInt("tuan"));
//                                    thongKeChiThang.setTongChi(jsonObject.getInt("tongChi"));
//                                    thongKeChiThangs.add(thongKeChiThang);
//                                    entries.add(new BarEntry((float) i, (float) thongKeChiThang.getTongChi()));
//                                }
//
//                                //set dữ liệu cho biểu đồ
//                                BarDataSet dataSet = new BarDataSet(entries, "Thống kê chi tiêu tháng");
//                                BarData barData = new BarData(dataSet);
//                                barChart.setData(barData);
//
//                                //set tên cho các cột
//                                ValueFormatter formatter = new ValueFormatter() {
//                                    @Override
//                                    public String getAxisLabel(float value, AxisBase axis) {
//                                        return thongKeChiThangs.get((int) value).getTuan() + "";
//                                    }
//                                };
//
//                                //set dữ liệu cho trục x
//                                XAxis xAxis = barChart.getXAxis();
//
//                                //ngăn trùng lặp label trên trục x
//                                xAxis.setGranularity(1f);
//                                xAxis.setGranularityEnabled(true);
//                                xAxis.setValueFormatter(formatter);
//
//                                //làm mới biểu đồ
//                                barChart.invalidate();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }) {
//                @Override
//                public Map<String, String> getHeaders() {
//                    Map<String, String> headers = new HashMap<>();
//                    headers.put("Authorization", "Bearer " + token);
//                    return headers;
//                }
//            };
//
//            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//            requestQueue.add(jsonArrayRequest);
//        }
//    }
}