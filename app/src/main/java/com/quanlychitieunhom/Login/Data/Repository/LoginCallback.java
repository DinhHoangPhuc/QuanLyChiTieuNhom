package com.quanlychitieunhom.Login.Data.Repository;

public interface LoginCallback {
    void onApiResponse(String message, String username, String token, String refreshToken);
}
