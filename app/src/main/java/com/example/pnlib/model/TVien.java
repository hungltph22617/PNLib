package com.example.pnlib.model;

public class TVien {
    private int matv;
    private String tentv;
    private int namsinh;

    public TVien() {
    }

    public TVien(String tentv, int namsinh) {
        this.tentv = tentv;
        this.namsinh = namsinh;
    }

    public TVien(int matv, String tentv, int namsinh) {
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
