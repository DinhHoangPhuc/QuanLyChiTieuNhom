package com.quanlychitieunhom.RefreshToken;

import com.quanlychitieunhom.Login.Data.Repository.LoginResonpse;

public interface RefreshTokenCallback {
    void onApiResponse(LoginResonpse response);
}
