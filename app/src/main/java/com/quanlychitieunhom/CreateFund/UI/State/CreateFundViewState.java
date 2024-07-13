package com.quanlychitieunhom.CreateFund.UI.State;

public class CreateFundViewState {
    private CreateFundState state;
    private FundModel fundModel;

    public CreateFundViewState(CreateFundState state, FundModel fundModel) {
        this.state = state;
        this.fundModel = fundModel;
    }

    public CreateFundState getState() {
        return state;
    }

    public FundModel getFundModel() {
        return fundModel;
    }
}
