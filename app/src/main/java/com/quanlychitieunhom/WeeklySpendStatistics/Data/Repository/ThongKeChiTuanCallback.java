package com.quanlychitieunhom.WeeklySpendStatistics.Data.Repository;

import com.quanlychitieunhom.WeeklySpendStatistics.UI.State.ThongKeChiTuanModel;

import java.util.List;

public interface ThongKeChiTuanCallback {
    void onApiResponse(int statusCode, List<ThongKeChiTuanModel> thongKeChiTuanModels);
}
