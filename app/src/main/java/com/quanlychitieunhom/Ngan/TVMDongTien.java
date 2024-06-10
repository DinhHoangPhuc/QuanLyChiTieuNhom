package com.quanlychitieunhom.Ngan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.quanlychitieunhom.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVMDongTien extends AppCompatActivity {
    private ApiService apiService;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tvmdong_tien);
// Khởi tạo Retrofit Service Interface
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Khởi tạo Retrofit Service Interface
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Gọi API khi activity được tạo
        callApi();
        callApi2();
    }

    private void callApi() {
        Call<ResponseBody> call = apiService.soTienHT();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Xử lý dữ liệu trả về từ API
                    // Hiển thị dữ liệu lên TextView
                    TextView tvTienHT = findViewById(R.id.tv_tienHientai);
                    try {
                        tvTienHT.setText(response.body().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Xử lý khi API trả về lỗi
                    Toast.makeText(TVMDongTien.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Xử lý khi gọi API thất bại
                Toast.makeText(TVMDongTien.this, "Không thể kết nối đến máy chủ!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void callApi2() {
        Call<ResponseBody> call = apiService.chiaTien();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Xử lý dữ liệu trả về từ API
                    // Hiển thị dữ liệu lên TextView
                    TextView resultTextView = findViewById(R.id.tv_chiadeu);
                    try {
                        resultTextView.setText(response.body().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Xử lý khi API trả về lỗi
                    Toast.makeText(TVMDongTien.this, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Xử lý khi gọi API thất bại
                Toast.makeText(TVMDongTien.this, "Không thể kết nối đến máy chủ!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}