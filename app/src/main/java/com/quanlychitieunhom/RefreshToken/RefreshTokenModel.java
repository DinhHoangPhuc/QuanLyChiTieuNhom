package com.quanlychitieunhom.RefreshToken;

public class RefreshTokenModel {
    private String refreshToken;

    public RefreshTokenModel(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
