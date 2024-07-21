package com.quanlychitieunhom.MonthlyCollectStatistics.UI.State;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quanlychitieunhom.Login.Data.Repository.LoginResonpse;
import com.quanlychitieunhom.MonthlyCollectStatistics.Data.Repository.ThongKeThuThangCallback;
import com.quanlychitieunhom.MonthlyCollectStatistics.Data.Repository.ThongKeThuThangRepo;
import com.quanlychitieunhom.MonthlyCollectStatistics.Data.Repository.ThongKeThuThangRepoImpl;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.Uitls.BaseViewState;
import com.quanlychitieunhom.Uitls.StateUtil;

import java.util.List;

public class ThongKeThuThangViewModel extends ViewModel {
    private MutableLiveData<BaseViewState<List<ThongKeThuThangModel>>> thongKeThuThangLiveData = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private ThongKeThuThangRepo thongKeThuThangRepo = new ThongKeThuThangRepoImpl();

    public MutableLiveData<BaseViewState<List<ThongKeThuThangModel>>> getThongKeThuThangLiveData() {
        return thongKeThuThangLiveData;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void getThongKeThuThang(int quyId,
                                   String token,
                                   String refreshToken,
                                   Context context) {
        thongKeThuThangRepo.getThongKeThuThang(quyId, new ThongKeThuThangCallback() {
            @Override
            public void onGetThongKeThuThangSuccess(List<ThongKeThuThangModel> thongKeThuThangResponse) {
                thongKeThuThangLiveData.postValue(new BaseViewState<>(thongKeThuThangResponse, StateUtil.SUCCESS));
            }

            @Override
            public void onGetThongKeThuThangFailed(String message) {
                error.postValue(message);
            }
        }, new RefreshTokenCallback() {
            @Override
            public void onApiResponse(LoginResonpse response) {

            }
        }, token, refreshToken, context);
    }

}
