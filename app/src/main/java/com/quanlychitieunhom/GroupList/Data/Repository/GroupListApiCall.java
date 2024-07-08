package com.quanlychitieunhom.GroupList.Data.Repository;

import com.quanlychitieunhom.GroupList.UI.State.NhomModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GroupListApiCall {
    @GET("nhom/getAllNhom")
    Call<List<NhomModel>> getGroupList(@Query("username") String username);
}
