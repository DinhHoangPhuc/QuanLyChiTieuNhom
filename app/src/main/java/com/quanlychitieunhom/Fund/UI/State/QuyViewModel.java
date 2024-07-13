package com.quanlychitieunhom.Fund.UI.State;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quanlychitieunhom.Login.Data.Repository.LoginResonpse;
import com.quanlychitieunhom.Fund.Data.Repository.QuyCallback;
import com.quanlychitieunhom.Fund.Data.Repository.QuyRepo;
import com.quanlychitieunhom.Fund.Data.Repository.QuyRepoImpl;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;

public class QuyViewModel extends ViewModel {
    private final MutableLiveData<QuyViewState> quyViewStateLiveData = new MutableLiveData<>();
    private final MutableLiveData<LoginResonpse> refreshTokenViewState = new MutableLiveData<>();
    private final QuyRepo quyRepo = new QuyRepoImpl();

    public MutableLiveData<QuyViewState> getQuyViewStateLiveData() {
        return quyViewStateLiveData;
    }
    public MutableLiveData<LoginResonpse> getRefreshTokenViewState() {
        return refreshTokenViewState;
    }

    public void getQuy(int nhomId,
                       String token,
                       String refreshToken,
                       Context context) {
        getQuyCall(nhomId, token, refreshToken, context);
    }

    private void getQuyCall(int nhomId,
                            String token,
                            String refreshToken,
                            Context context) {
        quyViewStateLiveData.postValue(new QuyViewState(QuyState.LOADING, null));
        quyRepo.getQuy(nhomId, new QuyCallback() {
            @Override
            public void onApiResponse(QuyModel quyModel, String message) {
                if(message.equals("Success")) {
                    quyViewStateLiveData.postValue(new QuyViewState(QuyState.SUCCESS, quyModel));
                } else {
                    quyViewStateLiveData.postValue(new QuyViewState(QuyState.ERROR, null));
                }
            }
        }, new RefreshTokenCallback() {
            @Override
            public void onApiResponse(LoginResonpse response) {
                if(response != null) {
                    refreshTokenViewState.postValue(response);
                }
            }
        }, refreshToken, token, context);
    }
}
