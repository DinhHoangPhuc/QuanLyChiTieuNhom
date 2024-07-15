package com.quanlychitieunhom.CreateGroup.Data.Repository;

import android.content.Context;

import com.quanlychitieunhom.CreateGroup.UI.State.CreateGroupModel;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.RetrofitSingleton.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGroupRepoImpl implements CreateGroupRepo {

    @Override
    public void createGroup(CreateGroupModel createGroupModel,
                            CreateGroupCallback callback,
                            RefreshTokenCallback refreshTokenCallback,
                            String refreshToken,
                            String token,
                            Context context) {
        callCreateGroupApi(createGroupModel, callback, refreshTokenCallback, refreshToken, token, context);
    }

    private void callCreateGroupApi(CreateGroupModel createGroupModel,
                                    CreateGroupCallback callback,
                                    RefreshTokenCallback refreshTokenCallback,
                                    String refreshToken,
                                    String token,
                                    Context context) {
        CreateGroupApiCall createGroupApiCall = RetrofitClient.getCreateGroupApiCall(refreshTokenCallback, refreshToken, token, context);
        Call<CreateGroupResponse> call = createGroupApiCall.createGroup(createGroupModel);
        call.enqueue(new Callback<CreateGroupResponse>() {
            @Override
            public void onResponse(Call<CreateGroupResponse> call, Response<CreateGroupResponse> response) {
                if (response.isSuccessful()) {
                    callback.onCreateGroupSuccess(response.body());
                } else {
                    callback.onCreateGroupFailed("Tạo nhóm thất bại");
                }
            }

            @Override
            public void onFailure(Call<CreateGroupResponse> call, Throwable t) {
                callback.onCreateGroupFailed("Tạo nhóm thất bại");
            }
        });
    }
}
