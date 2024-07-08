package com.quanlychitieunhom.Login.Data.Repository;

public class LoginResonpse {
    private final String token;
    private final String username;
    private final String refreshToken;

    public LoginResonpse(String token, String username, String refreshToken) {
        this.token = token;
        this.username = username;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
