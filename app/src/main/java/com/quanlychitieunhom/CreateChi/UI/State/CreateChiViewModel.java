package com.quanlychitieunhom.CreateChi.UI.State;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quanlychitieunhom.CreateChi.Data.Repository.CreateChiCallback;
import com.quanlychitieunhom.CreateChi.Data.Repository.CreateChiRepo;
import com.quanlychitieunhom.CreateChi.Data.Repository.CreateChiRepoImpl;
import com.quanlychitieunhom.CreateThu.Data.Repository.CreateThuCallback;
import com.quanlychitieunhom.CreateThu.UI.State.CreateThuViewState;
import com.quanlychitieunhom.CreateThu.UI.State.ThuModel;
import com.quanlychitieunhom.Login.Data.Repository.LoginResonpse;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.Uitls.StateUtil;

public class CreateChiViewModel extends ViewModel {
    private MutableLiveData<CreateChiViewState> chiViewStateMutableLiveData = new MutableLiveData<>();
    private CreateChiRepo chiRepo = new CreateChiRepoImpl();

    public MutableLiveData<CreateChiViewState> getChiViewStateMutableLiveData() {
        return chiViewStateMutableLiveData;
    }

    public void createChi(ChiModel chiModel,
                          String refreshToken,
                          String token,
                          Context context) {
        chiViewStateMutableLiveData.postValue(new CreateChiViewState(null, StateUtil.LOADING));
        chiRepo.createChi(chiModel, new CreateChiCallback() {
            @Override
            public void onApiResponse(int statusCode) {
                if (statusCode == 201) {
                    chiViewStateMutableLiveData.postValue(new CreateChiViewState(chiModel, StateUtil.SUCCESS));
                } else {
                    chiViewStateMutableLiveData.postValue(new CreateChiViewState(null, StateUtil.ERROR));
                }
            }
        }, new RefreshTokenCallback() {
            @Override
            public void onApiResponse(LoginResonpse response) {

            }
        }, refreshToken, token, context);
    }
}
