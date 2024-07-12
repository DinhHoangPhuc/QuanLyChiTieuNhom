package com.quanlychitieunhom.Quy.Data.Repository;

import android.content.Context;

import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;

public interface QuyRepo {
    void getQuy(int nhomId,
                QuyCallback callback,
                RefreshTokenCallback refreshTokenCallback,
                String refreshToken,
                String token,
                Context context);
}
