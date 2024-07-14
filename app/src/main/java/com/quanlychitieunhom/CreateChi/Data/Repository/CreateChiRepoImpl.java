package com.quanlychitieunhom.CreateChi.Data.Repository;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.quanlychitieunhom.CreateChi.UI.State.ChiModel;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.RetrofitSingleton.RetrofitClient;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;

public class CreateChiRepoImpl implements CreateChiRepo {

    @Override
    public void createChi(ChiModel chiModel,
                          CreateChiCallback callback,
                          RefreshTokenCallback refreshTokenCallback,
                          String refreshToken,
                          String token,
                          Context context) {
        CreateChiApiCall createChiApiCall = RetrofitClient.getCreateChiApiCall(refreshTokenCallback, refreshToken, token, context);
        createChiApiCall.createChi(chiModel).enqueue(new retrofit2.Callback<ChiModel>() {
            @Override
            public void onResponse(retrofit2.Call<ChiModel> call, retrofit2.Response<ChiModel> response) {
                if (response.isSuccessful()) {
                    callback.onApiResponse(response.code());
                } else {
                    try(ResponseBody errorBody = response.errorBody()) {
                        assert errorBody != null;
                        String errorString = errorBody.string();
                        Map<String, String> errorResponse = new Gson().fromJson(errorString, Map.class);
                        Log.d("Error create chi", Objects.requireNonNull(errorResponse.get("error")));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    callback.onApiResponse(400);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ChiModel> call, Throwable t) {
                callback.onApiResponse(500);
            }
        });
    }
}
