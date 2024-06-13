package com.quanlychitieunhom.Phuc;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.JsonObject;
import com.quanlychitieunhom.R;
import com.quanlychitieunhom.Thai.DanhSachNhom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView tenNhom, soTienBanDau, soTienHienTai;
    private ListView lvThu;
    private Button btnThu, btnChi;

    private ArrayList<Thu> thuArrayList = new ArrayList<>();
    private int nhomID;

    public QuyFragment(int nhomId) {
        this.nhomID = nhomId;
    }

    public QuyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuyFragment newInstance(String param1, String param2) {
        QuyFragment fragment = new QuyFragment();
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
        // Inflate the layout for this fragment
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.fragment_quy, container, false);

        tenNhom = view.findViewById(R.id.tenNhom);
        soTienBanDau = view.findViewById(R.id.soTienBanDau);
        soTienHienTai = view.findViewById(R.id.soTienHienTai);
        lvThu = view.findViewById(R.id.lvThu);
        btnThu = view.findViewById(R.id.btnThu);
        btnChi = view.findViewById(R.id.btnChi);

        btnThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApi(1);
            }
        });

        btnChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApi(0);
            }
        });

        callApi(1);
        return view;
    }

    private void callApi(int thuChi) {
        String url = "http://10.0.2.2:8080/api/quy/getQuy?nhomId=" + nhomID;

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if(!token.isEmpty()){
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            String error = response.optString("error");

                            if(!error.isEmpty()){
                                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                                return;
                            }

                            int soTienBD = response.optInt("soTienBD");
                            int soTienHT = response.optInt("soTienHT");
                            soTienBanDau.setText(String.valueOf(soTienBD));
                            soTienHienTai.setText(String.valueOf(soTienHT));

                            JSONObject quy = response.optJSONObject("nhom");
                            tenNhom.setText(quy.optString("tenNhom"));

                            if(thuChi == 1){
                                JSONArray thus = quy.optJSONArray("thu");

                                thuArrayList.clear();

                                for(int i=0; i<thus.length(); i++){
                                    JSONObject thuJson = thus.optJSONObject(i);
                                    Thu thu = new Thu();
                                    thu.setId(thuJson.optInt("id"));
                                    thu.setMoTa(thuJson.optString("moTa"));
                                    thu.setSoTienThu(thuJson.optInt("soTien"));
                                    thu.setNgayThu(thuJson.optString("ngayThu"));

                                    thuArrayList.add(thu);
                                }
                            } else {
                                JSONArray chis = response.optJSONArray("chis");

                                thuArrayList.clear();

                                for(int i=0; i<chis.length(); i++){
                                    JSONObject chiJson = chis.optJSONObject(i);
                                    Thu thu = new Thu();
                                    thu.setId(chiJson.optInt("id"));
                                    thu.setMoTa(chiJson.optString("moTa"));
                                    thu.setSoTienThu(chiJson.optInt("soTien"));
                                    thu.setNgayThu(chiJson.optString("ngayChi"));

                                    thuArrayList.add(thu);
                                }
                            }

                            ThuListViewAdapter thuListViewAdapter = new ThuListViewAdapter(getActivity(), R.layout.thu_row, thuArrayList);
                            lvThu.setAdapter(thuListViewAdapter);
                            thuListViewAdapter.notifyDataSetChanged();

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(requireContext(), "Quỹ không tồn tại", Toast.LENGTH_SHORT).show();
                            
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Bearer " + token);
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsonObjectRequest);
        }
    }

}