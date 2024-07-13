package com.quanlychitieunhom.CreateFund.UI.State;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quanlychitieunhom.CreateFund.Data.Repository.CreateFundCallback;
import com.quanlychitieunhom.CreateFund.Data.Repository.CreateFundRepo;
import com.quanlychitieunhom.CreateFund.Data.Repository.CreateFundRepoImpl;
import com.quanlychitieunhom.CreateFund.Data.Repository.CreateFundResponse;
import com.quanlychitieunhom.Login.Data.Repository.LoginResonpse;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;

public class CreateFundViewModel extends ViewModel {
    private MutableLiveData<CreateFundViewState> createFundViewStateLiveData = new MutableLiveData<>();
    private CreateFundRepo createFundRepo = new CreateFundRepoImpl();

    public MutableLiveData<CreateFundViewState> getCreateFundViewStateLiveData() {
        return createFundViewStateLiveData;
    }

    public void createFund(FundModel fundModel,
                           RefreshTokenCallback refreshTokenCallback,
                           String refreshToken,
                           String token,
                           Context context) {
        createFundViewStateLiveData.postValue(new CreateFundViewState(CreateFundState.LOADING, null));
        createFundRepo.createFund(fundModel, new CreateFundCallback() {
            @Override
            public void onApiResponse(int statusCode, CreateFundResponse createFundResponse) {
                if (statusCode == 201) {
                    createFundViewStateLiveData.postValue(new CreateFundViewState(CreateFundState.SUCCESS,
                            new FundModel(createFundResponse.getId(),
                                    createFundResponse.getSoTienBD(),
                                    createFundResponse.getSoTienHT())));
                } else {
                    createFundViewStateLiveData.postValue(new CreateFundViewState(CreateFundState.ERROR, null));
                }
            }
        }, new RefreshTokenCallback() {
            @Override
            public void onApiResponse(LoginResonpse response) {
                if (response != null) {
                    refreshTokenCallback.onApiResponse(response);
                }
            }
        }, refreshToken, token, context);
    }

}
