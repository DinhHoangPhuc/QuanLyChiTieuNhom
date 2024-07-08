package com.quanlychitieunhom.Register.Data.Repository;

import android.util.Log;

import com.google.gson.Gson;
import com.quanlychitieunhom.Register.UI.State.RegisterModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterRepoImpl implements RegisterRepo{
    @Override
    public void register(RegisterModel registrationModel, RegisterCallback registerCallback) {
        makeApiCall(registrationModel, registerCallback);
    }

    private void makeApiCall(RegisterModel registrationModel, RegisterCallback registerCallback) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        RegistrationApiCall registrationApiCall = retrofit.create(RegistrationApiCall.class);
        Call<RegisterResponse> call = registrationApiCall.register(registrationModel);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    int statusCode = response.code();
                    Log.d("StatusCode success", String.valueOf(statusCode));
                    Map<String, String> data = new HashMap<>();
                    assert response.body() != null;
                    data.put("message", response.body().getMessage());
                    Log.d("Response success", Objects.requireNonNull(data.get("message")));
                    registerCallback.onApiResponse(data, statusCode);
//                    Map<String, String> data = new Gson().fromJson(message, Map.class);

//                    registerCallback.onApiResponse(data, statusCode);

//                    registerCallback.onApiResponse("Success");
                } else {
                    try (ResponseBody responseBody = response.errorBody()) {
                        assert responseBody != null;
                        String error = responseBody.string();
                        Gson gson = new Gson();
                        Map<String, String> errorResponse = gson.fromJson(error, Map.class);
                        Log.d("Response error", errorResponse.toString());

                        registerCallback.onApiResponse(errorResponse, response.code());

//                        registerCallback.onApiResponse(errorResponse.get("error"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                registerCallback.onApiResponse(t.getMessage());
            }
        });
    }
}
