package com.quanlychitieunhom.Tuyen;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/chi")
    Call<Void> createChi(@Body Chi chi);

    @POST("/api/thu")
    Call<Void> createThu(@Body Thu thu);
}
