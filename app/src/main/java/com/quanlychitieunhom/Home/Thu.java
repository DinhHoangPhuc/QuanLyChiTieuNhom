package com.quanlychitieunhom.Home;

public class Thu {
    private int id;
    private String ngayThu;
    private int soTienThu;
    private String moTa;

    public Thu() {
    }

    public Thu(int id, String ngayThu, int soTienThu, String moTa) {
        this.id = id;
        this.ngayThu = ngayThu;
        this.soTienThu = soTienThu;
        this.moTa = moTa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNgayThu() {
        return ngayThu;
    }

    public void setNgayThu(String ngayThu) {
        this.ngayThu = ngayThu;
    }

    public int getSoTienThu() {
        return soTienThu;
    }

    public void setSoTienThu(int soTienThu) {
        this.soTienThu = soTienThu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
