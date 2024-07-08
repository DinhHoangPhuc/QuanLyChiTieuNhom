package com.quanlychitieunhom.Quy.Data.Repository;

import com.quanlychitieunhom.Quy.UI.State.QuyModel;

public interface QuyCallback {
    void onApiResponse(QuyModel quyModel, String message);
}
