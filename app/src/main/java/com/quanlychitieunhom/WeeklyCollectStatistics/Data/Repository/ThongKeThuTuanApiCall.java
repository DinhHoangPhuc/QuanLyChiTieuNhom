package com.quanlychitieunhom.WeeklyCollectStatistics.Data.Repository;

import com.quanlychitieunhom.WeeklyCollectStatistics.UI.State.ThongKeThuTuanModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ThongKeThuTuanApiCall {
    @GET("thu/thongKeThuTuan")
    Call<List<ThongKeThuTuanModel>> getThongKeThuTuan(@Query("nhomId") int quyId);
}
