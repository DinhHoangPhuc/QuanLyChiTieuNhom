package com.quanlychitieunhom.RefreshToken;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

public class JwtInterceptor implements Interceptor {
    private String token;

    public JwtInterceptor(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder()
                .header("Authorization", "Bearer " + token);
        Log.d("JwtInterceptor", "intercept: " + token);
        return chain.proceed(requestBuilder.build());
    }
}
