package com.quanlychitieunhom.GroupList.UI.State;

public class NhomModel {
    private int id;
    private String tenNhom;
    private String moTa;
    private String hinhNhom;

    public NhomModel(int id, String tenNhom, String moTa, String hinhNhom) {
        this.id = id;
        this.tenNhom = tenNhom;
        this.moTa = moTa;
        this.hinhNhom = hinhNhom;
    }

    public NhomModel(){
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public void setTenNhom(String tenNhom) {
        this.tenNhom = tenNhom;
    }

    public String getHinhNhom() {
        return hinhNhom;
    }

    public void setHinhNhom(String hinhNhom) {
        this.hinhNhom = hinhNhom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
