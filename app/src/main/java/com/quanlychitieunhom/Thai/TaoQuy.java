package com.quanlychitieunhom.Thai;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.quanlychitieunhom.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class TaoQuy extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private static final int MY_REQUEST_CODE = 10;

    EditText edtTienLapQuy;
    ImageButton btnTaoQuy;
    String urlTaoQuy = "http://192.168.1.10:8080/api/quy/taoQuy";

    public TaoQuy() {
        // Required empty public constructor
    }

    public static TaoQuy newInstance(String param1, String param2) {
        TaoQuy fragment = new TaoQuy();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_sheet_taoquy, container, false);
        btnTaoQuy = view.findViewById(R.id.btnTaoQuy);
        edtTienLapQuy = view.findViewById(R.id.edtTienLapQuy);
        btnTaoQuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitThemQuy();
            }
        });


        return view;
    }

    private void submitThemQuy() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            int selectedItemId = arguments.getInt("selectedItemId");
            String tienLapQuy = edtTienLapQuy.getText().toString();
            if (tienLapQuy.isEmpty()) {
                Toast.makeText(getActivity(), "Vui lòng nhập số tiền lập quỷ", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("nhomId", selectedItemId);
                jsonObject.put("tienLapQuy", tienLapQuy);
                Toast.makeText(getActivity(), "Tạo quỹ thành công", Toast.LENGTH_SHORT).show();
                ThemQuy(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "Lỗi lấy id nhóm!", Toast.LENGTH_SHORT).show();
        }
    }

    private void ThemQuy(JSONObject jsonObject) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlTaoQuy, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0X3VzZXIxIiwiaWF0IjoxNzE4MjIwMTQ5LCJleHAiOjE3MTgzMDY1NDl9.D-mlxzg8hTyBcn3JvNjX7qeYFWz9eUfIj3woBqcNR2c926rWJkIbKrNzWiYsvLvi");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}


