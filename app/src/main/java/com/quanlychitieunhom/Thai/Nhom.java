package com.quanlychitieunhom.Thai;

public class Nhom {
    private String TenNhom;
    private int HinhNhom;
    public Nhom(int hinhNhom, String tenNhom) {
        TenNhom = tenNhom;
        HinhNhom = hinhNhom;
    }

    public Nhom(){
    }
    public String getTenNhom() {
        return TenNhom;
    }

    public void setTenNhom(String tenNhom) {
        TenNhom = tenNhom;
    }

    public int getHinhNhom() {
        return HinhNhom;
    }

    public void setHinhNhom(int hinhNhom) {
        HinhNhom = hinhNhom;
    }
}
