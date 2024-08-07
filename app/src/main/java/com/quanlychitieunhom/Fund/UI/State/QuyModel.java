package com.quanlychitieunhom.Fund.UI.State;

import com.quanlychitieunhom.CreateChi.UI.State.ChiModel;
import com.quanlychitieunhom.CreateThu.UI.State.ThuModel;

import java.util.List;

public class QuyModel {
    private int id;
    private int soTienBD;
    private int soTienHT;
    private List<ChiModel> chis;
    private List<ThuModel> thus;

    public QuyModel(int nhomId,
                    int soTienBD,
                    int soTienHT,
                    List<ChiModel> chis,
                    List<ThuModel> thus) {
        this.id = nhomId;
        this.soTienBD = soTienBD;
        this.soTienHT = soTienHT;
        this.chis = chis;
        this.thus = thus;
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

    public List<ChiModel> getChis() {
        return chis;
    }

    public List<ThuModel> getThus() {
        return thus;
    }
}
