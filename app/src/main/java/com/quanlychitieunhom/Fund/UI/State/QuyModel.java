package com.quanlychitieunhom.Fund.UI.State;

import java.util.List;

public class QuyModel {
    private int nhomId;
    private int soTienBD;
    private int soTienHT;
    private List<ChiModel> chis;
    private List<ThuModel> thus;

    public QuyModel(int nhomId,
                    int soTienBD,
                    int soTienHT,
                    List<ChiModel> chis,
                    List<ThuModel> thus) {
        this.nhomId = nhomId;
        this.soTienBD = soTienBD;
        this.soTienHT = soTienHT;
        this.chis = chis;
        this.thus = thus;
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

    public List<ChiModel> getChis() {
        return chis;
    }

    public List<ThuModel> getThus() {
        return thus;
    }
}
