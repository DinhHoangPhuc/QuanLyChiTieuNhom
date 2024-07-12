package com.quanlychitieunhom.Quy.UI.Display;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.quanlychitieunhom.Home.Thu;
import com.quanlychitieunhom.Home.ThuListViewAdapter;
import com.quanlychitieunhom.Quy.UI.State.QuyModel;
import com.quanlychitieunhom.Quy.UI.State.QuyState;
import com.quanlychitieunhom.Quy.UI.State.QuyViewModel;
import com.quanlychitieunhom.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuyFragment extends Fragment {
    private TextView tenNhom, soTienBanDau, soTienHienTai;
    private ListView lvThu;
    private Button btnThu, btnChi;
    private ArrayList<Thu> thuArrayList = new ArrayList<>();
    private int nhomID;

    private QuyViewModel quyViewModel;
    private String token, refreshToken;

    public QuyFragment(int nhomId) {
        this.nhomID = nhomId;
    }

    public QuyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.fragment_quy, container, false);

        getControl(view);

        getSharedPref();


        handleChiEvent();

        handleThuEvent();

        return view;
    }

    private void getControl(View view) {
        tenNhom = view.findViewById(R.id.tenNhom);
        soTienBanDau = view.findViewById(R.id.soTienBanDau);
        soTienHienTai = view.findViewById(R.id.soTienHienTai);
        lvThu = view.findViewById(R.id.lvThu);
        btnThu = view.findViewById(R.id.btnThu);
        btnChi = view.findViewById(R.id.btnChi);
    }

    private void handleChiEvent() {
        btnChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void handleThuEvent() {
        btnThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getQuy(int nhomID) {
        quyViewModel = new ViewModelProvider(requireActivity()).get(QuyViewModel.class);
        quyViewModel.getQuy(nhomID, token, refreshToken, getContext());
    }

    private void loadQuy() {
        quyViewModel.getQuyViewStateLiveData().observe(getViewLifecycleOwner(), quyViewState -> {
            if(quyViewState.getQuyState() == QuyState.SUCCESS) {
                QuyModel quyModel = quyViewState.getQuyModel();
                soTienBanDau.setText(String.valueOf(quyModel.getSoTienBD()));
                soTienHienTai.setText(String.valueOf(quyModel.getSoTienHT()));
//                tenNhom.setText(quyModel.());
            } else {
                Toast.makeText(requireContext(), "Quỹ không tồn tại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSharedPref() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        refreshToken = sharedPreferences.getString("refreshToken", "");
    }

//    private void callApi(int thuChi) {
//        String url = "http://10.0.2.2:8080/api/quy/getQuy?nhomId=" + nhomID;
//
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
//        String token = sharedPreferences.getString("token", "");
//
//        if(!token.isEmpty()){
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            String error = response.optString("error");
//
//                            if(!error.isEmpty()){
//                                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//
//                            int soTienBD = response.optInt("soTienBD");
//                            int soTienHT = response.optInt("soTienHT");
//                            soTienBanDau.setText(String.valueOf(soTienBD));
//                            soTienHienTai.setText(String.valueOf(soTienHT));
//
//                            JSONObject quy = response.optJSONObject("nhom");
//                            tenNhom.setText(quy.optString("tenNhom"));
//
//                            if(thuChi == 1){
//                                JSONArray thus = quy.optJSONArray("thu");
//
//                                thuArrayList.clear();
//
//                                for(int i=0; i<thus.length(); i++){
//                                    JSONObject thuJson = thus.optJSONObject(i);
//                                    Thu thu = new Thu();
//                                    thu.setId(thuJson.optInt("id"));
//                                    thu.setMoTa(thuJson.optString("moTa"));
//                                    thu.setSoTienThu(thuJson.optInt("soTien"));
//                                    thu.setNgayThu(thuJson.optString("ngayThu"));
//
//                                    thuArrayList.add(thu);
//                                }
//                            } else {
//                                JSONArray chis = response.optJSONArray("chis");
//
//                                thuArrayList.clear();
//
//                                for(int i=0; i<chis.length(); i++){
//                                    JSONObject chiJson = chis.optJSONObject(i);
//                                    Thu thu = new Thu();
//                                    thu.setId(chiJson.optInt("id"));
//                                    thu.setMoTa(chiJson.optString("moTa"));
//                                    thu.setSoTienThu(chiJson.optInt("soTien"));
//                                    thu.setNgayThu(chiJson.optString("ngayChi"));
//
//                                    thuArrayList.add(thu);
//                                }
//                            }
//
//                            ThuListViewAdapter thuListViewAdapter = new ThuListViewAdapter(getActivity(), R.layout.thu_row, thuArrayList);
//                            lvThu.setAdapter(thuListViewAdapter);
//                            thuListViewAdapter.notifyDataSetChanged();
//
//                        }
//                    }, new Response.ErrorListener() {
//
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(requireContext(), "Quỹ không tồn tại", Toast.LENGTH_SHORT).show();
//
//                        }
//                    })
//            {
//                @Override
//                public Map<String, String> getHeaders() {
//                    Map<String, String> headers = new HashMap<>();
//                    headers.put("Authorization", "Bearer " + token);
//                    return headers;
//                }
//            };
//
//            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//            requestQueue.add(jsonObjectRequest);
//        }
//    }

}