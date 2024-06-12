package com.quanlychitieunhom.Thai;

public class Quy {
    private int tienLapQuy;
    private int tienHienTai;

    public Quy(int tienLapQuy, int tienHienTai) {
        this.tienLapQuy = tienLapQuy;
        this.tienHienTai = tienHienTai;
    }

    public int getTienLapQuy() {
        return tienLapQuy;
    }

    public void setTienLapQuy(int tienLapQuy) {
        this.tienLapQuy = tienLapQuy;
    }

    public int getTienHienTai() {
        return tienHienTai;
    }

    public void setTienHienTai(int tienHienTai) {
        this.tienHienTai = tienHienTai;
    }
}
