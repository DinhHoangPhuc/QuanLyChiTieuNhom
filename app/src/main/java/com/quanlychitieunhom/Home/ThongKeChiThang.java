package com.quanlychitieunhom.Home;

public class ThongKeChiThang {
    private int tuan;
    private int tongChi;

    public ThongKeChiThang() {
    }

    public ThongKeChiThang(int tuan, int tongChi) {
            this.tuan = tuan;
            this.tongChi = tongChi;
    }

    public int getTuan() {
            return tuan;
    }

    public void setTuan(int tuan) {
            this.tuan = tuan;
    }

    public int getTongChi() {
            return tongChi;
    }

    public void setTongChi(int tongChi) {
            this.tongChi = tongChi;
    }
}
