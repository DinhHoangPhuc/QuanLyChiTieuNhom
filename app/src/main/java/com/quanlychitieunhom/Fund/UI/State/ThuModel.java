package com.quanlychitieunhom.Fund.UI.State;

import java.util.Date;

public class ThuModel {
    private int id;
    private int soTien;
    private String moTa;
    private Date ngayThu;

    public ThuModel(int id,
                    int soTien,
                    String moTa,
                    Date ngayThu) {
        this.id = id;
        this.soTien = soTien;
        this.moTa = moTa;
        this.ngayThu = ngayThu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getNgayThu() {
        return ngayThu;
    }

    public void setNgayThu(Date ngayThu) {
        this.ngayThu = ngayThu;
    }
}


