package com.quanlychitieunhom.WeeklySpendStatistics.Data.Repository;

import android.content.Context;

import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;

public interface ThongKeChiTuanRepo {
    void getThongKeChiTuan(int quyId,
                           ThongKeChiTuanCallback callback,
                           RefreshTokenCallback refreshTokenCallback,
                           String token,
                           String refreshToken,
                           Context context);
}
