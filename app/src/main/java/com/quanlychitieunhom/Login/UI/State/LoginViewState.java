package com.quanlychitieunhom.Login.UI.State;

public class LoginViewState {
    private final LoginModel loginModel;
    private final LoginState loginState;
    private final String errorMessage;

    private final String token;
    private final String username;
    private final String refreshToken;

    public LoginViewState(LoginModel loginModel, LoginState loginState, String errorMessage, String token, String username, String refreshToken) {
        this.loginModel = loginModel;
        this.loginState = loginState;
        this.errorMessage = errorMessage;
        this.token = token;
        this.username = username;
        this.refreshToken = refreshToken;
    }

    public LoginModel getLoginModel() {
        return loginModel;
    }

    public LoginState getLoginState() {
        return loginState;
    }

    public String getErrorMessage() {
        return errorMessage;
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
