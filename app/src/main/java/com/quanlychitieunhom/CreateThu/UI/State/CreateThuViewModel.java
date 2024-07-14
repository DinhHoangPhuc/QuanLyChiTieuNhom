package com.quanlychitieunhom.CreateThu.UI.State;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quanlychitieunhom.CreateThu.Data.Repository.CreateThuCallback;
import com.quanlychitieunhom.CreateThu.Data.Repository.ThuRepo;
import com.quanlychitieunhom.CreateThu.Data.Repository.ThuRepoImpl;
import com.quanlychitieunhom.Login.Data.Repository.LoginResonpse;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.Uitls.StateUtil;

public class CreateThuViewModel extends ViewModel {
    private MutableLiveData<CreateThuViewState> thuViewStateMutableLiveData = new MutableLiveData<>();
    private ThuRepo thuRepo = new ThuRepoImpl();

    public MutableLiveData<CreateThuViewState> getThuViewStateMutableLiveData() {
        return thuViewStateMutableLiveData;
    }

    public void createThu(ThuModel thuModel,
                          String refreshToken,
                          String token,
                          Context context) {
        thuViewStateMutableLiveData.postValue(new CreateThuViewState(null, StateUtil.LOADING));
        thuRepo.createThu(thuModel, new CreateThuCallback() {
            @Override
            public void onApiResponse(int statusCode) {
                if (statusCode == 201) {
                    thuViewStateMutableLiveData.postValue(new CreateThuViewState(thuModel, StateUtil.SUCCESS));
                } else {
                    thuViewStateMutableLiveData.postValue(new CreateThuViewState(null, StateUtil.ERROR));
                }
            }
        }, new RefreshTokenCallback() {
            @Override
            public void onApiResponse(LoginResonpse response) {

            }
        }, refreshToken, token, context);
    }
}
