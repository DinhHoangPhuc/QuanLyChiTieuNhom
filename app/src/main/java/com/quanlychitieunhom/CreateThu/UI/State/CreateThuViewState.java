package com.quanlychitieunhom.CreateThu.UI.State;

import com.quanlychitieunhom.Uitls.StateUtil;

public class CreateThuViewState {
    private ThuModel thuModel;
    private StateUtil state;

    public CreateThuViewState(ThuModel thuModel, StateUtil state) {
        this.thuModel = thuModel;
        this.state = state;
    }

    public ThuModel getThuModel() {
        return thuModel;
    }

    public StateUtil getState() {
        return state;
    }

}
