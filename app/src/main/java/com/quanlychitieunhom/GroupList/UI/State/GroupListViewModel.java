package com.quanlychitieunhom.GroupList.UI.State;

import android.content.Context;

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
                             String refreshToken,
                             Context context) {
        getGroupListCall(username, token, expireTime, refreshToken, context);
    }

    private void getGroupListCall(String username,
                                  String token,
                                  long expireTime,
                                  String refreshToken,
                                  Context context) {
        groupListViewState.postValue(new GroupListViewState(GroupListState.LOADING,
                                null));
        groupListRepo.getGroupList(username,
                                    token,
                                    new GroupListCallback() {
            @Override
            public void onApiResponse(int statusCode,
                                      List<NhomModel> nhomModelList) {
                if(statusCode == 200) {
                    groupListViewState.postValue(new GroupListViewState(GroupListState.SUCCESS,
                            new ListNhomModel(nhomModelList)));
                } else {
                    groupListViewState.postValue(new GroupListViewState(GroupListState.ERROR,
                            null));
                }
            }
            }, new RefreshTokenCallback() {
            @Override
            public void onApiResponse(LoginResonpse response) {
                if(response != null) {
                    refreshTokenViewState.postValue(response);
                }
            }
        }, refreshToken, context);
    }

    public MutableLiveData<GroupListViewState> getGroupListViewState() {
        return groupListViewState;
    }

    public MutableLiveData<LoginResonpse> getRefreshTokenViewState() {
        return refreshTokenViewState;
    }

}
