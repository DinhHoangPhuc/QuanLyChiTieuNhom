package com.quanlychitieunhom.WeeklySpendStatistics.Data.Repository;

import com.quanlychitieunhom.WeeklySpendStatistics.UI.State.ThongKeChiTuanModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ThongKeChiTuanApiCall {
    @GET("chi/thongKeChiTuan")
    Call<List<ThongKeChiTuanModel>> getThongKeChiTuan(@Query("nhomId") int quyId);
}
