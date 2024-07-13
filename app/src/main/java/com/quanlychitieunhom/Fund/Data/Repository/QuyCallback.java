package com.quanlychitieunhom.Fund.Data.Repository;

import com.quanlychitieunhom.Fund.UI.State.QuyModel;

public interface QuyCallback {
    void onApiResponse(QuyModel quyModel,
                       String message);
}
