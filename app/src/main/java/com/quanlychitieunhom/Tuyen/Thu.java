package com.quanlychitieunhom.Tuyen;

public class Thu {

    private String mo_ta;
    private String ngay_thu;
    private int so_tien;
    private int nhom_id;

    public Thu() {
    }

    public Thu( String mo_ta, String ngay_thu, int so_tien, int nhom_id) {

        this.mo_ta = mo_ta;
        this.ngay_thu = ngay_thu;
        this.so_tien = so_tien;
        this.nhom_id = nhom_id;
    }


    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public String getNgay_thu() {
        return ngay_thu;
    }

    public void setNgay_thu(String ngay_thu) {
        this.ngay_thu = ngay_thu;
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
