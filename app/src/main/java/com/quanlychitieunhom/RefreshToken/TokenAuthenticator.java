package com.quanlychitieunhom.RefreshToken;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.quanlychitieunhom.Login.Data.Repository.LoginResonpse;
import com.quanlychitieunhom.Login.UI.Display.DangNhap;

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
    private Context context;

    public TokenAuthenticator(RefreshTokenCallback refreshTokenCallback,
                              String refreshToken,
                              Context context) {
        this.refreshTokenCallback = refreshTokenCallback;
        this.refreshTokenApiCall = createRefreshTokenRetrofit();
        this.refreshToken = refreshToken;
        this.context = context;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NonNull Response response) throws IOException {
        Log.d("TokenAuthenticator", "first point");
//        if (currentTime >= expireTime) {
            Call<LoginResonpse> call = refreshTokenApiCall.refreshToken(new RefreshTokenModel(refreshToken));
            retrofit2.Response<LoginResonpse> refreshTokenResponse = call.execute();

            if (refreshTokenResponse.isSuccessful()) {
                LoginResonpse loginResponse = refreshTokenResponse.body();
                if (loginResponse != null) {
                    refreshTokenCallback.onApiResponse(loginResponse);
                    saveToken(loginResponse.getToken(), loginResponse.getRefreshToken());
                    Log.d("TokenAuthenticator", loginResponse.getToken());
                    return response.request().newBuilder()
                            .header("Authorization", "Bearer " + loginResponse.getToken())
                            .build();
                }
            } else {
                deleteToken();
                startLoginActivity();
                return null;
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

    private void saveToken(String token, String refreshToken) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.putString("refreshToken", refreshToken);
        editor.commit();
    }

    private void deleteToken() {
        Log.d("deleteToken", "deleteToken: ");
        SharedPreferences sharedPreferences = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.remove("refreshToken");
        editor.commit();
    }

    private void startLoginActivity() {
        Log.d("startLoginActivity", "startLoginActivity: ");
        Intent intent = new Intent(context, DangNhap.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
