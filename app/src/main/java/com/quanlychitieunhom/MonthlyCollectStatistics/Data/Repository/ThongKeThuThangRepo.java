package com.quanlychitieunhom.MonthlyCollectStatistics.Data.Repository;

import android.content.Context;

import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;

public interface ThongKeThuThangRepo {
    void getThongKeThuThang(int quyId,
                            ThongKeThuThangCallback callback,
                            RefreshTokenCallback refreshTokenCallback,
                            String token,
                            String refreshToken,
                            Context context);
}
