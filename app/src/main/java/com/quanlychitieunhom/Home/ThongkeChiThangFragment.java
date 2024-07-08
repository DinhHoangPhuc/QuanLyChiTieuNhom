package com.quanlychitieunhom.Home;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.quanlychitieunhom.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongkeChiThangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongkeChiThangFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private BarChart barChart;
    private ArrayList<BarEntry> entries = new ArrayList<>();
    private ArrayList<ThongKeChiThang> thongKeChiThangs = new ArrayList<>();

    public ThongkeChiThangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongkeChiThangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThongkeChiThangFragment newInstance(String param1, String param2) {
        ThongkeChiThangFragment fragment = new ThongkeChiThangFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.fragment_thongke_chi_thang, container, false);

        barChart = view.findViewById(R.id.barChart);



        //goi api
        callApi();

        return view;
    }

    private void callApi() {
        String url = "http://10.0.2.2:8080/api/chi/thongKeChi/chiTuanTrongThang?nhomId=4";

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if(!token.isEmpty()){
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    ThongKeChiThang thongKeChiThang = new ThongKeChiThang();
                                    thongKeChiThang.setTuan(jsonObject.getInt("tuan"));
                                    thongKeChiThang.setTongChi(jsonObject.getInt("tongChi"));
                                    thongKeChiThangs.add(thongKeChiThang);
                                    entries.add(new BarEntry((float) i, (float) thongKeChiThang.getTongChi()));
                                }

                                //set dữ liệu cho biểu đồ
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

                                //set dữ liệu cho trục x
                                XAxis xAxis = barChart.getXAxis();

                                //ngăn trùng lặp label trên trục x
                                xAxis.setGranularity(1f);
                                xAxis.setGranularityEnabled(true);
                                xAxis.setValueFormatter(formatter);

                                //làm mới biểu đồ
                                barChart.invalidate();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsonArrayRequest);
        }
    }
}