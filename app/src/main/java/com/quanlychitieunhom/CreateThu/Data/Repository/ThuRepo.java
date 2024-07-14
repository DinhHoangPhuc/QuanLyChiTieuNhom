package com.quanlychitieunhom.CreateThu.Data.Repository;

import android.content.Context;

import com.quanlychitieunhom.CreateThu.UI.State.ThuModel;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;

public interface ThuRepo {
    void createThu(ThuModel thuModel,
                   CreateThuCallback callback,
                   RefreshTokenCallback refreshTokenCallback,
                   String refreshToken,
                   String token,
                   Context context);
}
