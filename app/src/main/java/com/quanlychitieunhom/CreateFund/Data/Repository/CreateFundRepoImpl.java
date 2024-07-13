package com.quanlychitieunhom.CreateFund.Data.Repository;

import android.content.Context;
import android.util.Log;

import com.quanlychitieunhom.CreateFund.UI.State.FundModel;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.RetrofitSingleton.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFundRepoImpl implements CreateFundRepo {
    @Override
    public void createFund(FundModel fundModel,
                           CreateFundCallback callback,
                           RefreshTokenCallback refreshTokenCallback,
                           String refreshToken,
                           String token,
                           Context context) {
        createFundCallApi(fundModel, callback, refreshTokenCallback, refreshToken, token, context);
    }

    private void createFundCallApi(FundModel fundModel,
                            CreateFundCallback callback,
                            RefreshTokenCallback refreshTokenCallback,
                            String refreshToken,
                            String token,
                            Context context) {
        CreateFundApiCall createFundApiCall = RetrofitClient.getCreateFundApiCall(refreshTokenCallback,
                                                                    refreshToken,token, context);
        Call<CreateFundResponse> call = createFundApiCall.createFund(fundModel);
        call.enqueue(new Callback<CreateFundResponse>() {
            @Override
            public void onResponse(Call<CreateFundResponse> call, Response<CreateFundResponse> response) {
                if(response.isSuccessful()) {
                    callback.onApiResponse(response.code(), response.body());
                } else {
                    try {
                        Log.d("Error tao quy: ", response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    callback.onApiResponse(response.code(), null);
                }
            }

            @Override
            public void onFailure(Call<CreateFundResponse> call, Throwable t) {
                callback.onApiResponse(500, null);
            }
        });
    }
}
