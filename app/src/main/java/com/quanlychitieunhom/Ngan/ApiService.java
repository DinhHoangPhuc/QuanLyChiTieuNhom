package com.quanlychitieunhom.Ngan;
// ApiService.java
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/quy/chia-tien")
    Call<ResponseBody> chiaTien();
    @GET("api/quy/so-tien-hien-tai")
    Call<ResponseBody> soTienHT();
}


