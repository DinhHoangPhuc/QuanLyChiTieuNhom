package com.quanlychitieunhom.CreateThu.UI.State;

import java.util.Date;

public class ThuModel {
    private int nhomId;
    private int soTien;
    private String moTa;
    private String ngayThu;

    public ThuModel(int id,
                    int soTien,
                    String moTa,
                    String ngayThu) {
        this.nhomId = id;
        this.soTien = soTien;
        this.moTa = moTa;
        this.ngayThu = ngayThu;
    }

    public int getNhomId() {
        return nhomId;
    }

    public void setNhomId(int nhomId) {
        this.nhomId = nhomId;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getNgayThu() {
        return ngayThu;
    }

    public void setNgayThu(String ngayThu) {
        this.ngayThu = ngayThu;
    }
}


