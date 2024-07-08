package com.quanlychitieunhom.Login.Data.Repository;

import com.quanlychitieunhom.Login.UI.State.LoginModel;

public interface LoginRepo {
    void login(LoginModel loginModel, LoginCallback loginCallback);
}
