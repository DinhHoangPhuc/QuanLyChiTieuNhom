package com.quanlychitieunhom.Register.Data.Repository;

import com.quanlychitieunhom.Register.UI.State.RegisterModel;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegistrationApiCall {
//    @POST("auth/register")
//    Call<RegisterModel> register(@Body RegisterModel registrationModel);

    @POST("auth/register")
    Call<RegisterResponse> register(@Body RegisterModel registrationModel);
}
