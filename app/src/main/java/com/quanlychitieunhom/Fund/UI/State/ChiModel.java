package com.quanlychitieunhom.Fund.UI.State;

import java.util.Date;

public class ChiModel {
    private int id;
    private int soTien;
    private Date ngayChi;
    private String moTa;

    public ChiModel(int id,
                    int soTien,
                    Date ngayChi,
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

    public Date getNgayChi() {
        return ngayChi;
    }

    public String getMoTa() {
        return moTa;
    }
}
