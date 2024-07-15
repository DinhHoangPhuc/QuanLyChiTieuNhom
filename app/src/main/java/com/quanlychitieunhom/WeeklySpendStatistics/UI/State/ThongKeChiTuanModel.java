package com.quanlychitieunhom.WeeklySpendStatistics.UI.State;

public class ThongKeChiTuanModel {
    private String ngayChi;
    private int soTien;

    public ThongKeChiTuanModel(String ngayChi, int tongTien) {
        this.ngayChi = ngayChi;
        this.soTien = tongTien;
    }

    public String getNgayChi() {
        return ngayChi;
    }

    public void setNgayChi(String ngayChi) {
        this.ngayChi = ngayChi;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }
}
