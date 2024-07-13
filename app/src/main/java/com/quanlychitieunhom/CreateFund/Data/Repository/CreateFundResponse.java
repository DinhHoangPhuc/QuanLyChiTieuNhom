package com.quanlychitieunhom.CreateFund.Data.Repository;

public class CreateFundResponse {
    private int id;
    private int soTienBD;
    private int soTienHT;

    public CreateFundResponse(int id, int soTienBD, int soTienHT) {
        this.id = id;
        this.soTienBD = soTienBD;
        this.soTienHT = soTienHT;
    }

    public int getId() {
        return id;
    }

    public int getSoTienBD() {
        return soTienBD;
    }

    public int getSoTienHT() {
        return soTienHT;
    }
}
