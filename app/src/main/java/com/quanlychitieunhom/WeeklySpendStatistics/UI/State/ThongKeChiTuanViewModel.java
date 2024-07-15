package com.quanlychitieunhom.WeeklySpendStatistics.UI.State;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quanlychitieunhom.Login.Data.Repository.LoginResonpse;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.Uitls.BaseViewState;
import com.quanlychitieunhom.Uitls.StateUtil;
import com.quanlychitieunhom.WeeklySpendStatistics.Data.Repository.ThongKeChiTuanCallback;
import com.quanlychitieunhom.WeeklySpendStatistics.Data.Repository.ThongKeChiTuanRepo;
import com.quanlychitieunhom.WeeklySpendStatistics.Data.Repository.ThongKeChiTuanRepoImpl;

import java.util.List;

public class ThongKeChiTuanViewModel extends ViewModel {
    private MutableLiveData<BaseViewState<List<ThongKeChiTuanModel>>> thongKeChiTuanLiveData = new MutableLiveData<>();
    private ThongKeChiTuanRepo thongKeChiTuanRepo = new ThongKeChiTuanRepoImpl();

    public MutableLiveData<BaseViewState<List<ThongKeChiTuanModel>>> getThongKeChiTuanLiveData() {
        return thongKeChiTuanLiveData;
    }

    public void getThongKeChiTuan(int quyId,
                                  String token,
                                  String refreshToken,
                                  Context context) {
//        thongKeChiTuanLiveData.postValue(new BaseViewState<>(null, StateUtil.LOADING));
        thongKeChiTuanRepo.getThongKeChiTuan(quyId, new ThongKeChiTuanCallback() {
            @Override
            public void onApiResponse(int statusCode, List<ThongKeChiTuanModel> thongKeChiTuanModels) {
                Log.d("status code tkct vm", statusCode + "");
                if (statusCode == 200) {
                    thongKeChiTuanLiveData.postValue(new BaseViewState<>(thongKeChiTuanModels, StateUtil.SUCCESS));
                } else {
                    thongKeChiTuanLiveData.postValue(new BaseViewState<>(null, StateUtil.ERROR));
                }
            }
        }, new RefreshTokenCallback() {
            @Override
            public void onApiResponse(LoginResonpse response) {

            }
        }, token, refreshToken, context);
    }
}
