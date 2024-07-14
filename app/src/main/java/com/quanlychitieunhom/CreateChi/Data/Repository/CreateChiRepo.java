package com.quanlychitieunhom.CreateChi.Data.Repository;

import android.content.Context;

import com.quanlychitieunhom.CreateChi.UI.State.ChiModel;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;

public interface CreateChiRepo {
    void createChi(ChiModel chiModel,
                     CreateChiCallback callback,
                     RefreshTokenCallback refreshTokenCallback,
                     String refreshToken,
                     String token,
                     Context context);
}
