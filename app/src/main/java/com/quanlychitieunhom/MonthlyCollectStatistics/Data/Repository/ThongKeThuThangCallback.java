package com.quanlychitieunhom.MonthlyCollectStatistics.Data.Repository;

import com.quanlychitieunhom.MonthlyCollectStatistics.UI.State.ThongKeThuThangModel;

import java.util.List;

public interface ThongKeThuThangCallback {
    void onGetThongKeThuThangSuccess(List<ThongKeThuThangModel> thongKeThuThangModelList);
    void onGetThongKeThuThangFailed(String message);
}
