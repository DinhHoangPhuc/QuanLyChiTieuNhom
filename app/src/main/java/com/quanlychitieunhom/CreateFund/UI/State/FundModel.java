package com.quanlychitieunhom.CreateFund.UI.State;

public class FundModel {
    private int nhomId;
    private int soTienBD;
    private int soTienHT;

    public FundModel(int nhomId, int soTienBD, int soTienHT) {
        this.nhomId = nhomId;
        this.soTienBD = soTienBD;
        this.soTienHT = soTienHT;
    }

    public int getNhomId() {
        return nhomId;
    }

    public int getSoTienBD() {
        return soTienBD;
    }

    public int getSoTienHT() {
        return soTienHT;
    }
}
