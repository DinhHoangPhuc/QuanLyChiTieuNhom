package com.quanlychitieunhom.Fund.UI.State;

import java.util.Date;

public class ChiModel {
    private int id;
    private int soTien;
    private String ngayChi;
    private String moTa;

    public ChiModel(int id,
                    int soTien,
                    String ngayChi,
                    String moTa) {
        this.id = id;
        this.soTien = soTien;
        this.ngayChi = ngayChi;
        this.moTa = moTa;
    }

    public int getId() {
        return id;
    }

    public int getSoTien() {
        return soTien;
    }

    public String getNgayChi() {
        return ngayChi;
    }

    public String getMoTa() {
        return moTa;
    }
}
