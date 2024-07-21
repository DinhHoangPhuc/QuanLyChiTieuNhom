package com.quanlychitieunhom.MonthlyCollectStatistics.UI.State;

public class ThongKeThuThangModel {
    private int tuan;
    private int tongThu;

    public ThongKeThuThangModel(int tuan, int tongThu) {
        this.tuan = tuan;
        this.tongThu = tongThu;
    }

    public int getTuan() {
        return tuan;
    }

    public void setTuan(int tuan) {
        this.tuan = tuan;
    }

    public int getTongThu() {
        return tongThu;
    }

    public void setTongThu(int tongThu) {
        this.tongThu = tongThu;
    }
}
