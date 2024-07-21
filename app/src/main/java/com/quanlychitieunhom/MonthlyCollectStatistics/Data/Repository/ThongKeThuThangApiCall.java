package com.quanlychitieunhom.MonthlyCollectStatistics.Data.Repository;

import com.quanlychitieunhom.MonthlyCollectStatistics.UI.State.ThongKeThuThangModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ThongKeThuThangApiCall {
    @GET("thu/thongKeThu/ThuTuanTrongThang")
    Call<List<ThongKeThuThangModel>> getThongKeThuThang(@Query("nhomId") int quyId);
}
