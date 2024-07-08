package com.quanlychitieunhom.RefreshToken;

import com.quanlychitieunhom.Login.Data.Repository.LoginResonpse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RefreshTokenApiCall {
    @POST("refreshToken")
    Call<LoginResonpse> refreshToken(@Body RefreshTokenModel refreshTokenModel);
}
