package com.quanlychitieunhom.Fund.Data.Repository;

import android.content.Context;

import com.google.gson.Gson;
import com.quanlychitieunhom.Fund.UI.State.QuyModel;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.RetrofitSingleton.RetrofitClient;

import java.io.IOException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuyRepoImpl implements QuyRepo {

    @Override
    public void getQuy(int nhomId,
                       QuyCallback callback,
                       RefreshTokenCallback refreshTokenCallback,
                       String refreshToken,
                       String token,
                       Context context) {
        callApi(nhomId, callback, refreshTokenCallback, refreshToken, token, context);
    }

    private void callApi(int nhomId,
                         QuyCallback callback,
                         RefreshTokenCallback refreshTokenCallback,
                         String refreshToken,
                         String token,
                         Context context) {
        QuyApiCall apiCall = RetrofitClient.getQuyApiCall(refreshTokenCallback, refreshToken, token, context);
        Call<QuyModel> call = apiCall.getQuy(nhomId);
        call.enqueue(new Callback<QuyModel>() {
            @Override
            public void onResponse(Call<QuyModel> call, Response<QuyModel> response) {
                if(response.isSuccessful()) {
                    callback.onApiResponse(response.body(), "Success");
                } else {
                    try(ResponseBody errorBody = response.errorBody()) {
                        assert errorBody != null;
                        String errorString = errorBody.string();
                        Map<String, String> errorResponse = new Gson().fromJson(errorString, Map.class);
                        callback.onApiResponse(null, errorResponse.get("error"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<QuyModel> call, Throwable t) {
                callback.onApiResponse(null, t.getMessage());
            }
        });
    }
}
