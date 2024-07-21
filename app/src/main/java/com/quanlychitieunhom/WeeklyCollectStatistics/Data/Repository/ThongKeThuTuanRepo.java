package com.quanlychitieunhom.WeeklyCollectStatistics.Data.Repository;

import android.content.Context;

import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;

public interface ThongKeThuTuanRepo {
    void getThongKeThuTuan(int quyId,
                           ThongKeThuTuanCallback callback,
                           RefreshTokenCallback refreshTokenCallback,
                           String token,
                           String refreshToken,
                           Context context);
}
