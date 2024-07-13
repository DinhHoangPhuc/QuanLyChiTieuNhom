package com.quanlychitieunhom.Fund.Data.Repository;

import com.quanlychitieunhom.Fund.UI.State.QuyModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuyApiCall {
    @GET("quy/getQuy")
    Call<QuyModel> getQuy(@Query("nhomId") int nhomId);
}
