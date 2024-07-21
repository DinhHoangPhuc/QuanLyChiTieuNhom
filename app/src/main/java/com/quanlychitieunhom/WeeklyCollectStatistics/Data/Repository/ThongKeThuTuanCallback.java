package com.quanlychitieunhom.WeeklyCollectStatistics.Data.Repository;

import com.quanlychitieunhom.WeeklyCollectStatistics.UI.State.ThongKeThuTuanModel;

import java.util.List;

public interface ThongKeThuTuanCallback {
    void onGetThongKeThuTuanSuccess(List<ThongKeThuTuanModel> thongKeThuTuanResponse);
    void onGetThongKeThuTuanFailed(String message);
}
