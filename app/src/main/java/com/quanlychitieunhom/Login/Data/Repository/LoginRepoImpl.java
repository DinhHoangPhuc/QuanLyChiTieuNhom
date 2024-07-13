package com.quanlychitieunhom.Login.Data.Repository;

import android.util.Log;

import com.google.gson.Gson;
import com.quanlychitieunhom.Login.UI.State.LoginModel;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginRepoImpl implements LoginRepo{
    @Override
    public void login(LoginModel loginModel, LoginCallback loginCallback) {
        makeApiCall(loginModel, loginCallback);
    }

    private void makeApiCall(LoginModel loginModel, LoginCallback loginCallback) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        LoginApiCall loginApiCall = retrofit.create(LoginApiCall.class);
        Call<LoginResonpse> call = loginApiCall.login(loginModel);
        call.enqueue(new Callback<LoginResonpse>() {
            @Override
            public void onResponse(Call<LoginResonpse> call, Response<LoginResonpse> response) {
                if (response.isSuccessful()) {
                    LoginResonpse responseMap = response.body();
                    assert responseMap != null;
                    loginCallback.onApiResponse("Success", responseMap.getUsername(), responseMap.getToken(), responseMap.getRefreshToken());
                    Log.d("Get token success when login", responseMap.getToken() + ", " + responseMap.getToken());
                }else {
                    ResponseBody responseBody = response.errorBody();
                    try {
                        assert responseBody != null;
                        String error = responseBody.string();
                        Map<String, String> errorResponse = new Gson().fromJson(error, Map.class);
                        loginCallback.onApiResponse(errorResponse.get("error"), "", "", "");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResonpse> call, Throwable t) {
                loginCallback.onApiResponse(t.getMessage(), "", "", "");
            }
        });
    }
}
