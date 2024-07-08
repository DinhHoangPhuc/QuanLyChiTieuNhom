package com.quanlychitieunhom.RefreshToken;

import com.quanlychitieunhom.Login.Data.Repository.LoginResonpse;
import com.quanlychitieunhom.RetrofitSingleton.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefreshTokenRepoImpl implements RefreshTokenRepo{
    @Override
    public void refreshToken(RefreshTokenModel model, RefreshTokenCallback callback) {
//         Call<LoginResonpse> call = RetrofitClient.getRefreshTokenApiCall().refreshToken(model);
//         call.enqueue(new Callback<LoginResonpse>() {
//             @Override
//             public void onResponse(Call<LoginResonpse> call, Response<LoginResonpse> response) {
//                 if(response.isSuccessful()){
//                     callback.onApiResponse(response.body());
//                 }
//             }
//
//             @Override
//             public void onFailure(Call<LoginResonpse> call, Throwable t) {
//                 callback.onApiResponse(null);
//             }
//         });
    }
}
