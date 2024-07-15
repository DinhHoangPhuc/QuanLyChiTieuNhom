package com.quanlychitieunhom.CreateGroup.Data.Repository;

import com.quanlychitieunhom.CreateGroup.UI.State.CreateGroupModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CreateGroupApiCall {
    @POST("nhom/taoNhom")
    Call<CreateGroupResponse> createGroup(@Body CreateGroupModel createGroupModel);
}
