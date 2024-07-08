package com.quanlychitieunhom.Quy.UI.State;

public class QuyViewState {
    private QuyState quyState;
    private QuyModel quyModel;

    public QuyViewState(QuyState quyState, QuyModel quyModel) {
        this.quyState = quyState;
        this.quyModel = quyModel;
    }

    public QuyState getQuyState() {
        return quyState;
    }

    public QuyModel getQuyModel() {
        return quyModel;
    }
}
