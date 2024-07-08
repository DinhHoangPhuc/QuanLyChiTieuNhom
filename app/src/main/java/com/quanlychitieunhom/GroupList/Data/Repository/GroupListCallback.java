package com.quanlychitieunhom.GroupList.Data.Repository;

import com.quanlychitieunhom.GroupList.UI.State.NhomModel;

import java.util.ArrayList;
import java.util.List;

public interface GroupListCallback {
    void onApiResponse(int statusCode, List<NhomModel> nhomModelList);
}
