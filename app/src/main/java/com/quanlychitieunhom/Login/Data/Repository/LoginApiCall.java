package com.quanlychitieunhom.Login.Data.Repository;

import com.quanlychitieunhom.Login.UI.State.LoginModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApiCall {
    @POST("auth/login")
    Call<LoginResonpse> login(@Body LoginModel loginModel);
}
