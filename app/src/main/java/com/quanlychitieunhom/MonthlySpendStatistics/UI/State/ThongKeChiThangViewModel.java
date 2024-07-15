package com.quanlychitieunhom.MonthlySpendStatistics.UI.State;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.quanlychitieunhom.Login.Data.Repository.LoginResonpse;
import com.quanlychitieunhom.MonthlySpendStatistics.Data.Repository.ThongKeChiThangCallback;
import com.quanlychitieunhom.MonthlySpendStatistics.Data.Repository.ThongKeChiThangRepo;
import com.quanlychitieunhom.MonthlySpendStatistics.Data.Repository.ThongKeChiThangRepoImpl;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.Uitls.BaseViewState;
import com.quanlychitieunhom.Uitls.StateUtil;

import java.util.List;

public class ThongKeChiThangViewModel extends ViewModel {
    private MutableLiveData<BaseViewState<List<ThongKeChiThangModel>>> thongKeChiThangLiveData = new MutableLiveData<>();
    private ThongKeChiThangRepo thongKeChiThangRepo = new ThongKeChiThangRepoImpl();

    public MutableLiveData<BaseViewState<List<ThongKeChiThangModel>>> getThongKeChiThangLiveData() {
        return thongKeChiThangLiveData;
    }

    public void getThongKeChiThang(int nhomId,
                                   String token,
                                   String refreshToken,
                                   Context context) {
        thongKeChiThangRepo.getThongKeChiThang(nhomId, new ThongKeChiThangCallback() {
            @Override
            public void onApiResponse(int statusCode, List<ThongKeChiThangModel> thongKeChiThangModelList) {
                if (statusCode == 200) {
                    thongKeChiThangLiveData.postValue(new BaseViewState<>(thongKeChiThangModelList, StateUtil.SUCCESS));
                } else {
                    thongKeChiThangLiveData.postValue(new BaseViewState<>(null, StateUtil.ERROR));
                }
            }
        }, new RefreshTokenCallback() {
            @Override
            public void onApiResponse(LoginResonpse response) {

            }
        }, token, refreshToken, context);
    }
}
