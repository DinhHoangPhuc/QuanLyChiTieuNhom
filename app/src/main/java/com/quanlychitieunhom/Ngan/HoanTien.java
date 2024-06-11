package com.quanlychitieunhom.Ngan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.quanlychitieunhom.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HoanTien extends AppCompatActivity {

    private static final String BASE_URL = "http://10.0.2.2:8080/api/quy/";
    private static final String AUTH_HEADER = "Authorization";
    private String jwtToken;
    private int groupId;
    private TextView tvChiaDeu, tvTienHienTai;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hoan_tien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Lấy token JWT và groupId từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        jwtToken = sharedPreferences.getString("token", null);
        groupId = sharedPreferences.getInt("groupId", -1);

        // Ánh xạ TextView
        tvChiaDeu = findViewById(R.id.tv_chiadeu);
        tvTienHienTai = findViewById(R.id.tv_tienHientai);

        // Gọi phương thức chia tiền và lấy số tiền hiện tại khi hoạt động được tạo
        callApiChiaDeu();
        callApiTienHienTai();
    }

    private void callApiChiaDeu() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String urlChiaDeu = BASE_URL + "chia-deu/" + groupId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlChiaDeu, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            tvChiaDeu.setText(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                        Toast.makeText(HoanTien.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(AUTH_HEADER, "Bearer " + jwtToken);
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    private void callApiTienHienTai() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String urlTienHienTai = BASE_URL + "so-tien-hien-tai/" + groupId;

        StringRequest stringRequest = new StringRequest
                (Request.Method.GET, urlTienHienTai, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        tvTienHienTai.setText(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                        Toast.makeText(HoanTien.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put(AUTH_HEADER, "Bearer " + jwtToken);
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }
}
