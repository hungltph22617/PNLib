package com.example.pnlib.model;

public class acccount {
    private String mand, tennd, mknd;

    public acccount(String mand, String tennd, String mknd) {
        this.mand = mand;
        this.tennd = tennd;
        this.mknd = mknd;
    }

    public acccount() {
    }

    public String getMand() {
        return mand;
    }

    public void setMand(String mand) {
        this.mand = mand;
    }

    public String getTennd() {
        return tennd;
    }

    public void setTennd(String tennd) {
        this.tennd = tennd;
    }

    public String getMknd() {
        return mknd;
    }

    public void setMknd(String mknd) {
        this.mknd = mknd;
    }
}
