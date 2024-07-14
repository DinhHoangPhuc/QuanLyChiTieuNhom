package com.quanlychitieunhom.CreateThu.Data.Repository;

import com.quanlychitieunhom.CreateThu.UI.State.ThuModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CreateThuApiCall {
    @POST("thu/taoThu")
    Call<ThuModel> createThu(@Body ThuModel thuModel);
}
