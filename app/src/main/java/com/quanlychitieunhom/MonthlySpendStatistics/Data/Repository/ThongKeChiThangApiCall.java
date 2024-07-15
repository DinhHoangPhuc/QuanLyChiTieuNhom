package com.quanlychitieunhom.MonthlySpendStatistics.Data.Repository;

import com.quanlychitieunhom.MonthlySpendStatistics.UI.State.ThongKeChiThangModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ThongKeChiThangApiCall {
    @GET("chi/thongKeChi/chiTuanTrongThang")
    Call<List<ThongKeChiThangModel>> getThongKeChiThang(@Query("nhomId") int quyId);
}
