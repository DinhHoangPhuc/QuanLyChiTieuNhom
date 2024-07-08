package com.quanlychitieunhom.RefreshToken;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.quanlychitieunhom.Login.Data.Repository.LoginResonpse;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenAuthenticator implements Authenticator {

    private RefreshTokenCallback refreshTokenCallback;
    private RefreshTokenApiCall refreshTokenApiCall;
    private String refreshToken;

    public TokenAuthenticator(RefreshTokenCallback refreshTokenCallback, String refreshToken) {
        this.refreshTokenCallback = refreshTokenCallback;
        this.refreshTokenApiCall = createRefreshTokenRetrofit();
        this.refreshToken = refreshToken;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NonNull Response response) throws IOException {
        long currentTime = System.currentTimeMillis();
        Log.d("TokenAuthenticator", "first point");
//        if (currentTime >= expireTime) {
            Call<LoginResonpse> call = refreshTokenApiCall.refreshToken(new RefreshTokenModel(refreshToken));
            retrofit2.Response<LoginResonpse> refreshTokenResponse = call.execute();

            if (refreshTokenResponse.isSuccessful()) {
                LoginResonpse loginResponse = refreshTokenResponse.body();
                if (loginResponse != null) {
                    refreshTokenCallback.onApiResponse(loginResponse);
                    Log.d("TokenAuthenticator", loginResponse.getToken());
                    return response.request().newBuilder()
                            .header("Authorization", "Bearer " + loginResponse.getToken())
                            .build();
                }
//            } else {
//                return null;
            }
//        }
        return null;
    }

    private RefreshTokenApiCall createRefreshTokenRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit.create(RefreshTokenApiCall.class);
    }
}
