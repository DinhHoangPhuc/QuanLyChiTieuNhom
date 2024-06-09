package com.quanlychitieunhom.Tuyen;

public class Chi {

    private String mo_ta;
    private String ngay_chi;
    private int so_tien;
    private int nhom_id;

    public Chi() {
    }

    public Chi(String mo_ta, String ngay_chi, int so_tien, int nhom_id) {
        this.mo_ta = mo_ta;
        this.ngay_chi = ngay_chi;
        this.so_tien = so_tien;
        this.nhom_id = nhom_id;
    }



    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public String getNgay_chi() {
        return ngay_chi;
    }

    public void setNgay_chi(String ngay_chi) {
        this.ngay_chi = ngay_chi;
    }

    public int getSo_tien() {
        return so_tien;
    }

    public void setSo_tien(int so_tien) {
        this.so_tien = so_tien;
    }

    public int getNhom_id() {
        return nhom_id;
    }

    public void setNhom_id(int nhom_id) {
        this.nhom_id = nhom_id;
    }
}
