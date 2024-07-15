package com.quanlychitieunhom.MonthlySpendStatistics.Data.Repository;

import android.content.Context;

import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;

public interface ThongKeChiThangRepo {
    void getThongKeChiThang(int quyId,
                            ThongKeChiThangCallback callback,
                            RefreshTokenCallback refreshTokenCallback,
                            String token,
                            String refreshToken,
                            Context context);
}
