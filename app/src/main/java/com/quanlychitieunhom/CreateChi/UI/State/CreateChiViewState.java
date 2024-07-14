package com.quanlychitieunhom.CreateChi.UI.State;

import com.quanlychitieunhom.Uitls.StateUtil;

public class CreateChiViewState {
    private ChiModel chiModel;
    private StateUtil state;

    public CreateChiViewState(ChiModel chiModel, StateUtil state) {
        this.chiModel = chiModel;
        this.state = state;
    }

    public ChiModel getChiModel() {
        return chiModel;
    }

    public StateUtil getState() {
        return state;
    }
}
