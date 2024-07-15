package com.quanlychitieunhom.CreateGroup.UI.State;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quanlychitieunhom.CreateGroup.Data.Repository.CreateGroupCallback;
import com.quanlychitieunhom.CreateGroup.Data.Repository.CreateGroupRepo;
import com.quanlychitieunhom.CreateGroup.Data.Repository.CreateGroupRepoImpl;
import com.quanlychitieunhom.CreateGroup.Data.Repository.CreateGroupResponse;
import com.quanlychitieunhom.Login.Data.Repository.LoginResonpse;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.Uitls.BaseViewState;
import com.quanlychitieunhom.Uitls.StateUtil;

public class CreateGroupViewModel extends ViewModel {
    private MutableLiveData<BaseViewState<CreateGroupModel>> createGroupState = new MutableLiveData<BaseViewState<CreateGroupModel>>();
    private CreateGroupRepo createGroupRepo = new CreateGroupRepoImpl();

    public MutableLiveData<BaseViewState<CreateGroupModel>> getCreateGroupState() {
        return createGroupState;
    }

    public void createGroup(CreateGroupModel createGroupModel,
                            String refreshToken,
                            String token,
                            Context context) {
        createGroupRepo.createGroup(createGroupModel, new CreateGroupCallback() {
            @Override
            public void onCreateGroupSuccess(CreateGroupResponse createGroupResponse) {
                createGroupState.postValue(new BaseViewState<>(createGroupModel, StateUtil.SUCCESS));
            }

            @Override
            public void onCreateGroupFailed(String message) {
                createGroupState.postValue(new BaseViewState<>(null, StateUtil.ERROR));
            }
        }, new RefreshTokenCallback() {
            @Override
            public void onApiResponse(LoginResonpse response) {

            }
        }, refreshToken, token, context);
    }
}
