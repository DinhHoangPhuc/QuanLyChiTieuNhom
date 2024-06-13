package com.quanlychitieunhom.Thai;

public class Nhom {
    private int id;
    private String TenNhom;
    private String MoTa;
    private String HinhNhom;

    public Nhom(int id, String tenNhom, String hinhNhom) {
        this.id = id;
        this.TenNhom = tenNhom;
        this.HinhNhom = hinhNhom;
    }

    public Nhom(){
    }

    public String getTenNhom() {
        return TenNhom;
    }

    public void setTenNhom(String tenNhom) {
        TenNhom = tenNhom;
    }

    public String getHinhNhom() {
        return HinhNhom;
    }

    public void setHinhNhom(String hinhNhom) {
        HinhNhom = hinhNhom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
