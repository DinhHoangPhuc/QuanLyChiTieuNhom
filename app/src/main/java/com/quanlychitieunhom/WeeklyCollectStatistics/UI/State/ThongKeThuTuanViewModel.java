package com.quanlychitieunhom.WeeklyCollectStatistics.UI.State;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quanlychitieunhom.Login.Data.Repository.LoginResonpse;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.Uitls.BaseViewState;
import com.quanlychitieunhom.Uitls.StateUtil;
import com.quanlychitieunhom.WeeklyCollectStatistics.Data.Repository.ThongKeThuTuanCallback;
import com.quanlychitieunhom.WeeklyCollectStatistics.Data.Repository.ThongKeThuTuanRepo;
import com.quanlychitieunhom.WeeklyCollectStatistics.Data.Repository.ThongKeThuTuanRepoImpl;

import java.util.List;

public class ThongKeThuTuanViewModel extends ViewModel {
    private MutableLiveData<BaseViewState<List<ThongKeThuTuanModel>>> thongKeThuTuanLiveData =  new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private ThongKeThuTuanRepo thongKeThuTuanRepo = new ThongKeThuTuanRepoImpl();

    public MutableLiveData<BaseViewState<List<ThongKeThuTuanModel>>> getThongKeThuTuanLiveData() {
        return thongKeThuTuanLiveData;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void getThongKeThuTuan(int quyId,
                                  String token,
                                  String refreshToken,
                                  Context context) {
        thongKeThuTuanRepo.getThongKeThuTuan(quyId, new ThongKeThuTuanCallback() {
            @Override
            public void onGetThongKeThuTuanSuccess(List<ThongKeThuTuanModel> thongKeThuTuanResponse) {
                thongKeThuTuanLiveData.postValue(new BaseViewState<>(thongKeThuTuanResponse, StateUtil.SUCCESS));
            }

            @Override
            public void onGetThongKeThuTuanFailed(String message) {
                error.postValue(message);
            }
        }, new RefreshTokenCallback() {
            @Override
            public void onApiResponse(LoginResonpse response) {

            }
        }, token, refreshToken, context);
    }

}
