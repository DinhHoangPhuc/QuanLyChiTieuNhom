package com.quanlychitieunhom.RefreshToken;

public interface RefreshTokenRepo {
    public void refreshToken(RefreshTokenModel model, RefreshTokenCallback callback);
}
