package com.quanlychitieunhom.Phuc;

public class ThongKeChiTuan {
    private String ngayChi;
    private int tongTien;

    public ThongKeChiTuan(String ngayChi, int tongTien) {
        this.ngayChi = ngayChi;
        this.tongTien = tongTien;
    }

    public String getNgayChi() {
        return ngayChi;
    }

    public void setNgayChi(String ngayChi) {
        this.ngayChi = ngayChi;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
