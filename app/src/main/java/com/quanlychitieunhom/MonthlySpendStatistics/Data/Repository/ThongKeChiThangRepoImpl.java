package com.quanlychitieunhom.MonthlySpendStatistics.Data.Repository;

import android.content.Context;

import com.quanlychitieunhom.MonthlySpendStatistics.UI.State.ThongKeChiThangModel;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.RetrofitSingleton.RetrofitClient;

import java.util.List;

import retrofit2.Call;

public class ThongKeChiThangRepoImpl implements ThongKeChiThangRepo {

    @Override
    public void getThongKeChiThang(int quyId, ThongKeChiThangCallback callback, RefreshTokenCallback refreshTokenCallback, String token, String refreshToken, Context context) {
        callApi(quyId, callback, refreshTokenCallback, token, refreshToken, context);
    }

    private void callApi(int quyId,
                         ThongKeChiThangCallback callback,
                         RefreshTokenCallback refreshTokenCallback,
                         String token,
                         String refreshToken,
                         Context context) {
        ThongKeChiThangApiCall apiCall = RetrofitClient.getThongKeChiThangApiCall(refreshTokenCallback, refreshToken, token, context);
        Call<List<ThongKeChiThangModel>> call = apiCall.getThongKeChiThang(quyId);
        call.enqueue(new retrofit2.Callback<List<ThongKeChiThangModel>>() {
            @Override
            public void onResponse(Call<List<ThongKeChiThangModel>> call, retrofit2.Response<List<ThongKeChiThangModel>> response) {
                if(response.isSuccessful()) {
                    callback.onApiResponse(response.code(), response.body());
                } else {
                    callback.onApiResponse(400, null);
                }
            }

            @Override
            public void onFailure(Call<List<ThongKeChiThangModel>> call, Throwable t) {
                callback.onApiResponse(400, null);
            }
        });
    }
}
