package com.quanlychitieunhom.WeeklySpendStatistics.Data.Repository;

import android.content.Context;

import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.RetrofitSingleton.RetrofitClient;
import com.quanlychitieunhom.WeeklySpendStatistics.UI.State.ThongKeChiTuanModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongKeChiTuanRepoImpl implements ThongKeChiTuanRepo {

    @Override
    public void getThongKeChiTuan(int quyId,
                                  ThongKeChiTuanCallback callback,
                                  RefreshTokenCallback refreshTokenCallback,
                                  String token,
                                  String refreshToken,
                                  Context context) {
        callApi(quyId, callback, refreshTokenCallback, token, refreshToken, context);
    }

    private void callApi(int quyId,
                         ThongKeChiTuanCallback callback,
                         RefreshTokenCallback refreshTokenCallback,
                         String token,
                         String refreshToken,
                         Context context) {
        ThongKeChiTuanApiCall apiCall = RetrofitClient.getThongKeChiTuanApiCall(refreshTokenCallback, refreshToken, token, context);
        Call<List<ThongKeChiTuanModel>> call = apiCall.getThongKeChiTuan(quyId);
        call.enqueue(new Callback<List<ThongKeChiTuanModel>>() {
            @Override
            public void onResponse(Call<List<ThongKeChiTuanModel>> call, Response<List<ThongKeChiTuanModel>> response) {
                if(response.isSuccessful()) {
                    callback.onApiResponse(response.code(), response.body());
                } else {
                    callback.onApiResponse(400, null);
                }
            }

            @Override
            public void onFailure(Call<List<ThongKeChiTuanModel>> call, Throwable t) {
                callback.onApiResponse(400, null);
            }
        });
    }
}
