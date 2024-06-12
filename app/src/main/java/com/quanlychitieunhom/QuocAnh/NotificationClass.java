package com.quanlychitieunhom.QuocAnh;

import java.util.Date;

public class NotificationClass {
   private int id;
   private String tieude;
   private String noiDung;
   private Date ngaydang;

   public NotificationClass(int id, String tieude, String noiDung, Date ngaytao) {
       this.id = id;
       this.tieude = tieude;
       this.noiDung = noiDung;
       this.ngaydang = ngaytao;

   }
    public int getId() {
         return id;
    }

    public void setId(int id) {
         this.id = id;
    }

    public String getTieude() {
         return tieude;
    }

    public void setTieude(String tieude) {
         this.tieude = tieude;
    }

    public String getNoidung() {
         return noiDung;
    }

    public void setNoidung(String noidung) {
         this.noiDung = noiDung;
    }

    public Date getNgaytao() {
         return ngaydang    ;
    }

    public void setNgaytao(Date ngaytao) {
         this.ngaydang = ngaytao;
    }


}
