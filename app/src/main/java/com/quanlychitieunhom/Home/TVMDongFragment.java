package com.quanlychitieunhom.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.quanlychitieunhom.R;
import com.quanlychitieunhom.Uitls.ViewModel.NhomViewModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TVMDongFragment extends Fragment {

    private TextView tvChiaDeu;
    private TextView tvTienHienTai;
    private String BASE_URL = "http://10.0.2.2:8080/api/quy/";
    private String token;
    private int nhomId;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_t_v_m_dong, container, false);

        tvChiaDeu = view.findViewById(R.id.tv_chiadeu);
        tvTienHienTai = view.findViewById(R.id.tv_tienHientai);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
//        nhomId = sharedPreferences.getInt("nhomId", 1); // Default to 1 if not found
        NhomViewModel nhomViewModel = new ViewModelProvider(requireActivity()).get(NhomViewModel.class);
        nhomId = nhomViewModel.getNhomID().getValue();

        callApiChiaDeu();
        callApiSoTienHienTai();

        return view;
    }

    private void callApiChiaDeu() {
        String urlChiaDeu = BASE_URL + "chia-deu/" + nhomId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlChiaDeu, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        tvChiaDeu.setText(String.valueOf(response.optDouble("tienThanhVien"))); // Hoặc logic khác để hiển thị kết quả chia đều
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("Error", error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jsonObjectRequest);
    }

    private void callApiSoTienHienTai() {
        String urlSoTienHienTai = BASE_URL + "so-tien-hien-tai/" + nhomId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlSoTienHienTai, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        try {
                        String soTienHienTai = String.valueOf(response.optDouble("soTienHienTai"));
                        tvTienHienTai.setText(soTienHienTai);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("Error", error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jsonObjectRequest);
    }
}
