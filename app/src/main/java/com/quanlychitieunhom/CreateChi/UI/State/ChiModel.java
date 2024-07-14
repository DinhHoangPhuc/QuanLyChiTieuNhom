package com.quanlychitieunhom.CreateChi.UI.State;

public class ChiModel {
    private int soTien;
    private String ngayChi;
    private String moTa;
    private int nhomId;

    public ChiModel(int id,
                    int soTien,
                    String ngayChi,
                    String moTa) {
        this.nhomId = id;
        this.soTien = soTien;
        this.ngayChi = ngayChi;
        this.moTa = moTa;
    }

    public int getNhomId() {
        return nhomId;
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
