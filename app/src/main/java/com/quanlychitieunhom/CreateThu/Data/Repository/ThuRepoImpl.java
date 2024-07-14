package com.quanlychitieunhom.CreateThu.Data.Repository;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.quanlychitieunhom.CreateThu.UI.State.ThuModel;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.RetrofitSingleton.RetrofitClient;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThuRepoImpl implements ThuRepo {

    @Override
    public void createThu(ThuModel thuModel,
                          CreateThuCallback callback,
                          RefreshTokenCallback refreshTokenCallback,
                          String refreshToken,
                          String token,
                          Context context) {
        callApiCreateThu(thuModel, callback, refreshTokenCallback, refreshToken, token, context);
    }

    private void callApiCreateThu(ThuModel thuModel,
                                  CreateThuCallback callback,
                                  RefreshTokenCallback refreshTokenCallback,
                                  String refreshToken,
                                  String token,
                                  Context context) {
        CreateThuApiCall createThuApiCall = RetrofitClient.getCreateThuApiCall(refreshTokenCallback, refreshToken, token, context);
        createThuApiCall.createThu(thuModel).enqueue(new Callback<ThuModel>() {
            @Override
            public void onResponse(Call<ThuModel> call, Response<ThuModel> response) {
                if (response.isSuccessful()) {
                    callback.onApiResponse(response.code());
                } else {
                    try(ResponseBody errorBody = response.errorBody()) {
                        assert errorBody != null;
                        String errorString = errorBody.string();
                        Map<String, String> errorResponse = new Gson().fromJson(errorString, Map.class);
                        Log.d("Error create thu", Objects.requireNonNull(errorResponse.get("error")));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    callback.onApiResponse(400);
                }
            }

            @Override
            public void onFailure(Call<ThuModel> call, Throwable t) {
                callback.onApiResponse(400);
            }
        });
    }
}
