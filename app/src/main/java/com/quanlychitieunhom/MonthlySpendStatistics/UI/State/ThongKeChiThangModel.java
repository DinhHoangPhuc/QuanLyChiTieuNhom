package com.quanlychitieunhom.MonthlySpendStatistics.UI.State;

public class ThongKeChiThangModel {
    private int tuan;
    private int tongChi;

    public ThongKeChiThangModel() {
    }

    public ThongKeChiThangModel(int tuan, int tongChi) {
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
