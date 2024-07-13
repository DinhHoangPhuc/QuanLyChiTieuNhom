package com.quanlychitieunhom.RetrofitSingleton;

import android.content.Context;

import com.quanlychitieunhom.CreateFund.Data.Repository.CreateFundApiCall;
import com.quanlychitieunhom.GroupList.Data.Repository.GroupListApiCall;
import com.quanlychitieunhom.Fund.Data.Repository.QuyApiCall;
import com.quanlychitieunhom.RefreshToken.JwtInterceptor;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.RefreshToken.TokenAuthenticator;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://10.0.2.2:8080/api/";

    private RetrofitClient() {}

    public static GroupListApiCall getGroupListApiCall(RefreshTokenCallback refreshTokenCallback,
                                                       String refreshToken,
                                                       String token,
                                                       Context context) {
        return getInstance(refreshTokenCallback, refreshToken, token, context).create(GroupListApiCall.class);
    }

    public static QuyApiCall getQuyApiCall(RefreshTokenCallback refreshTokenCallback,
                                           String refreshToken,
                                           String token,
                                           Context context) {
        return getInstance(refreshTokenCallback, refreshToken, token, context).create(QuyApiCall.class);
    }

    public static CreateFundApiCall getCreateFundApiCall(RefreshTokenCallback refreshTokenCallback,
                                                         String refreshToken,
                                                         String token,
                                                         Context context) {
        return getInstance(refreshTokenCallback, refreshToken, token, context).create(CreateFundApiCall.class);
    }

    public static Retrofit getInstance(RefreshTokenCallback refreshTokenCallback,
                                       String refreshToken,
                                       String token,
                                       Context context) {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            OkHttpClient.Builder client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(new JwtInterceptor(token))
                    .authenticator(new TokenAuthenticator(refreshTokenCallback, refreshToken, context));
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build();
        }
        return retrofit;
    }
}