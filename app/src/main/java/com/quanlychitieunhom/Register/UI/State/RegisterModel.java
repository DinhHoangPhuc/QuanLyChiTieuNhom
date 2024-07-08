package com.quanlychitieunhom.Register.UI.State;

public class RegisterModel {
    private String hoTen;
    private String email;
    private String sdt;
    private String username;
    private String password;
    private String avatar;

    public RegisterModel(String hoTen, String email, String sdt, String username, String password, String avatar) {
        this.hoTen = hoTen;
        this.email = email;
        this.sdt = sdt;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String toString() {
        return "RegisterModel{" +
                "hoTen='" + hoTen + '\'' +
                ", email='" + email + '\'' +
                ", sdt='" + sdt + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
