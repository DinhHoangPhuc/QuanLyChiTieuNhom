package com.quanlychitieunhom.Thai;

public class Nhom {
    private String TenNhom;
    private int HinhNhom;
    private String ChucVu;

    public Nhom(int hinhNhom, String tenNhom, String chucVu) {
        TenNhom = tenNhom;
        HinhNhom = hinhNhom;
        ChucVu = chucVu;
    }

    public Nhom(){
    }

    public String getChucVu() {
        return ChucVu;
    }

    public void setChucVu(String chucVu) {
        ChucVu = chucVu;
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
