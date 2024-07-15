package com.quanlychitieunhom.CreateGroup.Data.Repository;

public class CreateGroupResponse {
    private int id;
    private String tenNhom;
    private String moTap;
    private String hinhNhom;

    public CreateGroupResponse(int id, String tenNhom, String moTap, String hinhNhom) {
        this.id = id;
        this.tenNhom = tenNhom;
        this.moTap = moTap;
        this.hinhNhom = hinhNhom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    public String getMoTap() {
        return moTap;
    }

    public void setMoTap(String moTap) {
        this.moTap = moTap;
    }

    public String getHinhNhom() {
        return hinhNhom;
    }

    public void setHinhNhom(String hinhNhom) {
        this.hinhNhom = hinhNhom;
    }
}
