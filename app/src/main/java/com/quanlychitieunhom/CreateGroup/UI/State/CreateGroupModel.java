package com.quanlychitieunhom.CreateGroup.UI.State;

public class CreateGroupModel {
    private String username;
    private String tenNhom;
    private String moTa;
    private String hinhNhom;

    public CreateGroupModel(String username, String tenNhom, String moTa, String hinhNhom) {
        this.username = username;
        this.tenNhom = tenNhom;
        this.moTa = moTa;
        this.hinhNhom = hinhNhom;
    }

    public String getUsername() {
        return username;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getHinhNhom() {
        return hinhNhom;
    }
}
