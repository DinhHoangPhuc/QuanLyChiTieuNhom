package com.quanlychitieunhom.CreateFund.Data.Repository;

import com.quanlychitieunhom.CreateFund.UI.State.FundModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CreateFundApiCall {
    @POST("quy/taoQuy")
    Call<CreateFundResponse> createFund(@Body FundModel fundModel);
}
