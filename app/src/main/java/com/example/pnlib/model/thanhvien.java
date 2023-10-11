package com.example.pnlib.model;

public class thanhvien {
    private int matv;
    private String tentv;
    private int namsinh;

    public thanhvien() {
    }

    public thanhvien(String tentv, int namsinh) {
        this.tentv = tentv;
        this.namsinh = namsinh;
    }

    public thanhvien(int matv, String tentv, int namsinh) {
        this.matv = matv;
        this.tentv = tentv;
        this.namsinh = namsinh;
    }

    public int getMatv() {
        return matv;
    }

    public void setMatv(int matv) {
        this.matv = matv;
    }

    public String getTentv() {
        return tentv;
    }

    public void setTentv(String tentv) {
        this.tentv = tentv;
    }

    public int getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(int namsinh) {
        this.namsinh = namsinh;
    }
}
