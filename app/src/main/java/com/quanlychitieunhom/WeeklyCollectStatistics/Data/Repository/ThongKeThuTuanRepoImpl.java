package com.quanlychitieunhom.WeeklyCollectStatistics.Data.Repository;

import android.content.Context;

import com.google.gson.Gson;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.RetrofitSingleton.RetrofitClient;
import com.quanlychitieunhom.WeeklyCollectStatistics.UI.State.ThongKeThuTuanModel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongKeThuTuanRepoImpl implements ThongKeThuTuanRepo {

    @Override
    public void getThongKeThuTuan(int quyId,
                                  ThongKeThuTuanCallback callback,
                                  RefreshTokenCallback refreshTokenCallback,
                                  String token,
                                  String refreshToken,
                                  Context context) {
        callApi(quyId, callback, refreshTokenCallback, token, refreshToken, context);
    }

    private void callApi(int quyId,
                         ThongKeThuTuanCallback callback,
                         RefreshTokenCallback refreshTokenCallback,
                         String token,
                         String refreshToken,
                         Context context) {
        ThongKeThuTuanApiCall apiCall = RetrofitClient.getThongKeThuTuanApiCall(refreshTokenCallback, refreshToken, token, context);
        Call<List<ThongKeThuTuanModel>> call = apiCall.getThongKeThuTuan(quyId);
        call.enqueue(new Callback<List<ThongKeThuTuanModel>>() {
            @Override
            public void onResponse(Call<List<ThongKeThuTuanModel>> call, Response<List<ThongKeThuTuanModel>> response) {
                if(response.isSuccessful()) {
                    callback.onGetThongKeThuTuanSuccess(response.body());
                } else {
                    try(ResponseBody errorBody = response.errorBody()) {
                        assert errorBody != null;
                        String error = errorBody.string();
                        Map<String, String> errorMap = new Gson().fromJson(error, Map.class);
                        callback.onGetThongKeThuTuanFailed(errorMap.get("error"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ThongKeThuTuanModel>> call, Throwable t) {
                callback.onGetThongKeThuTuanFailed(t.getMessage());
            }
        });
    }
}
