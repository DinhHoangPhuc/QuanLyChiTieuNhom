package com.quanlychitieunhom.MonthlySpendStatistics.Data.Repository;

import com.quanlychitieunhom.MonthlySpendStatistics.UI.State.ThongKeChiThangModel;

import java.util.List;

public interface ThongKeChiThangCallback {
    void onApiResponse(int statusCode,
                       List<ThongKeChiThangModel> thongKeChiThangModelList);
}
