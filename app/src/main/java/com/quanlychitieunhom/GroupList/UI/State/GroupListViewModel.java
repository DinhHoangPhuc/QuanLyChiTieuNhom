package com.quanlychitieunhom.GroupList.UI.State;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quanlychitieunhom.GroupList.Data.Repository.GroupListCallback;
import com.quanlychitieunhom.GroupList.Data.Repository.GroupListRepo;
import com.quanlychitieunhom.GroupList.Data.Repository.GroupListRepoImpl;
import com.quanlychitieunhom.Login.Data.Repository.LoginResonpse;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;

import java.util.ArrayList;
import java.util.List;

public class GroupListViewModel extends ViewModel {
    private final MutableLiveData<GroupListViewState> groupListViewState = new MutableLiveData<>();;
    private final MutableLiveData<LoginResonpse> refreshTokenViewState = new MutableLiveData<>();
    private final GroupListRepo groupListRepo = new GroupListRepoImpl();

    public void getGroupList(String username,
                             String token,
                             long expireTime,
                             String refreshToken) {
        getGroupListCall(username, token, expireTime, refreshToken);
    }

    private void getGroupListCall(String username,
                                  String token,
                                  long expireTime,
                                  String refreshToken) {
        groupListViewState.postValue(new GroupListViewState(GroupListState.LOADING, null));
        groupListRepo.getGroupList(username, token, new GroupListCallback() {
            @Override
            public void onApiResponse(String message, List<NhomModel> nhomModelList) {
                if(message.equals("Success")) {
                    groupListViewState.postValue(new GroupListViewState(GroupListState.SUCCESS, new ListNhomModel(nhomModelList)));
                } else {
                    groupListViewState.postValue(new GroupListViewState(GroupListState.ERROR, null));
                }
            }
        }, new RefreshTokenCallback() {
            @Override
            public void onApiResponse(LoginResonpse response) {
                if(response != null) {
                    refreshTokenViewState.postValue(response);
                }
            }
        }, refreshToken);
    }

    public MutableLiveData<GroupListViewState> getGroupListViewState() {
        return groupListViewState;
    }

    public MutableLiveData<LoginResonpse> getRefreshTokenViewState() {
        return refreshTokenViewState;
    }

}
