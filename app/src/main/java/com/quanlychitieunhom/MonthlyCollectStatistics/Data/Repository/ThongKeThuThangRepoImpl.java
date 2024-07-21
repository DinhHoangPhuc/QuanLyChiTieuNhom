package com.quanlychitieunhom.MonthlyCollectStatistics.Data.Repository;

import android.content.Context;

import com.google.gson.Gson;
import com.quanlychitieunhom.MonthlyCollectStatistics.UI.State.ThongKeThuThangModel;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.RetrofitSingleton.RetrofitClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class ThongKeThuThangRepoImpl implements ThongKeThuThangRepo {

    @Override
    public void getThongKeThuThang(int quyId,
                                   ThongKeThuThangCallback callback,
                                   RefreshTokenCallback refreshTokenCallback,
                                   String token,
                                   String refreshToken,
                                   Context context) {
        callApi(quyId, callback, refreshTokenCallback, token, refreshToken, context);
    }

    private void callApi(int quyId,
                         ThongKeThuThangCallback callback,
                         RefreshTokenCallback refreshTokenCallback,
                         String token,
                         String refreshToken,
                         Context context) {
        ThongKeThuThangApiCall thongKeThuThangApiCall = RetrofitClient.getThongKeThuThangApiCall(refreshTokenCallback, refreshToken, token, context);
        Call<List<ThongKeThuThangModel>> call = thongKeThuThangApiCall.getThongKeThuThang(quyId);
        call.enqueue(new retrofit2.Callback<List<ThongKeThuThangModel>>() {
            @Override
            public void onResponse(Call<List<ThongKeThuThangModel>> call, retrofit2.Response<List<ThongKeThuThangModel>> response) {
                if (response.isSuccessful()) {
                    callback.onGetThongKeThuThangSuccess(response.body());
                } else {
                    try(ResponseBody errorBody = response.errorBody()) {
                        assert errorBody != null;
                        String error = errorBody.string();
                        Map<String, String> errorMap = new Gson().fromJson(error, Map.class);
                        callback.onGetThongKeThuThangFailed(errorMap.get("error"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ThongKeThuThangModel>> call, Throwable t) {
                callback.onGetThongKeThuThangFailed(t.getMessage());
            }
        });
    }
}
