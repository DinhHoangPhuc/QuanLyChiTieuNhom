package com.quanlychitieunhom.GroupList.Data.Repository;

import android.util.Log;

import com.google.gson.Gson;
import com.quanlychitieunhom.GroupList.UI.State.NhomModel;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.RetrofitSingleton.RetrofitClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class GroupListRepoImpl implements GroupListRepo{
    @Override
    public void getGroupList(String username,
                             String token,
                             GroupListCallback callback,
                             RefreshTokenCallback refreshTokenCallback,
                             String refreshToken) {
        getGroupListFromApi(username, callback, refreshTokenCallback, refreshToken, token);
    }

    private void getGroupListFromApi(String username,
                                     GroupListCallback callback,
                                     RefreshTokenCallback refreshTokenCallback,
                                     String refreshToken,
                                     String token) {
        GroupListApiCall groupListApiCall = RetrofitClient.getGroupListApiCall(refreshTokenCallback, refreshToken, token);
        Call<List<NhomModel>> call = groupListApiCall.getGroupList(username);
        call.enqueue(new retrofit2.Callback<List<NhomModel>>() {
            @Override
            public void onResponse(Call<List<NhomModel>> call, Response<List<NhomModel>> response) {
                if (response.isSuccessful()) {
                    List<NhomModel> responseMap = response.body();
                    if (!responseMap.isEmpty()) {
                        callback.onApiResponse(response.code(), responseMap);
                        Log.d("Call api group success", responseMap.get(0).getTenNhom());
                    } else {
                        callback.onApiResponse(response.code(), responseMap);
                        Log.d("Call api group success", "The list is empty");
                    }
                } else {
                    try (ResponseBody error = response.errorBody()) {
                        assert error != null;
                        String errorMessage = error.string();
                        if (isJsonValid(errorMessage)) {
                            Map<String, String> errorResponse = new Gson().fromJson(errorMessage, Map.class);
                            Log.d("Error json valid", errorResponse.get("error"));
                            callback.onApiResponse(response.code(), null);
                        } else {
                            // Handle non-JSON error message
                            callback.onApiResponse(response.code(), null);
                            Log.d("Error json not valid", errorMessage);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<NhomModel>> call, Throwable t) {
                if (t instanceof IOException) {
                    Log.d("Error onFailure", "This is a network error");
                } else {
                    Log.d("Error onFailure", "This error is not due to a network issue");
                }
                callback.onApiResponse(500, null);
                Log.d("Error onFailure", t.getMessage());
            }
        });
    }

    private boolean isJsonValid(String test) {
        try {
            new Gson().fromJson(test, Object.class);
            return true;
        } catch(com.google.gson.JsonSyntaxException ex) {
            return false;
        }
    }
}
