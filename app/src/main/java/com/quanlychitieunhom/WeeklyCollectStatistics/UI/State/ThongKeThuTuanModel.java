package com.quanlychitieunhom.WeeklyCollectStatistics.UI.State;

public class ThongKeThuTuanModel {
    private String ngayThu;
    private int soTien;

    public ThongKeThuTuanModel(String ngayThu, int soTien) {
        this.ngayThu = ngayThu;
        this.soTien = soTien;
    }

    public String getNgayThu() {
        return ngayThu;
    }

    public void setNgayThu(String ngayThu) {
        this.ngayThu = ngayThu;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }
}
