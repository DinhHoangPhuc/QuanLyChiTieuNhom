package com.quanlychitieunhom.Register.Data.Repository;

import com.quanlychitieunhom.Register.UI.State.RegisterModel;

public interface RegisterRepo {
    void register(RegisterModel registrationModel, RegisterCallback registerCallback);
}
