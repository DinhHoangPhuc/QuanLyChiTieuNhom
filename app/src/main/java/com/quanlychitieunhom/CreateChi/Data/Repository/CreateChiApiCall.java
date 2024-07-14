package com.quanlychitieunhom.CreateChi.Data.Repository;

import com.quanlychitieunhom.CreateChi.UI.State.ChiModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CreateChiApiCall {
    @POST("chi/them")
    Call<ChiModel> createChi(@Body ChiModel chiModel);
}
