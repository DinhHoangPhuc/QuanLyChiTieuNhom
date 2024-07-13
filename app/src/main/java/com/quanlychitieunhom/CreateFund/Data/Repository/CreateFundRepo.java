package com.quanlychitieunhom.CreateFund.Data.Repository;

import android.content.Context;

import com.quanlychitieunhom.CreateFund.UI.State.FundModel;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;

public interface CreateFundRepo {
    void createFund(FundModel fundModel,
                    CreateFundCallback callback,
                    RefreshTokenCallback refreshTokenCallback,
                    String refreshToken,
                    String token,
                    Context context);
}
