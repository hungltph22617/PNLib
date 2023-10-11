package com.example.pnlib.model;

public class sach {
    private int mas;
    private String tens;
    private int gts;
    private String anh;
    private int mls;
    private String tls;
    private int soluongdamuon;

    public sach(int mas, String tens, int gts, String anh, int mls, String tls) {
        this.mas = mas;
        this.tens = tens;
        this.gts = gts;
        this.anh = anh;
        this.mls = mls;
        this.tls = tls;
    }

    public sach(int mas, String tens, int gts, String anh, int mls) {
        this.mas = mas;
        this.tens = tens;
        this.gts = gts;
        this.anh = anh;
        this.mls = mls;
    }

    public sach(int mas, String anh, String tens, int soluongdamuon) {
        this.mas = mas;
        this.anh = anh;
        this.tens = tens;
        this.soluongdamuon = soluongdamuon;
    }

    public int getSoluongdamuon() {
        return soluongdamuon;
    }

    public void setSoluongdamuon(int soluongdamuon) {
        this.soluongdamuon = soluongdamuon;
    }

    public sach() {
    }

    public int getMas() {
        return mas;
    }

    public void setMas(int mas) {
        this.mas = mas;
    }

    public String getTens() {
        return tens;
    }

    public void setTens(String tens) {
        this.tens = tens;
    }

    public int getGts() {
        return gts;
    }

    public void setGts(int gts) {
        this.gts = gts;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getMls() {
        return mls;
    }

    public void setMls(int mls) {
        this.mls = mls;
    }

    public String getTls() {
        return tls;
    }

    public void setTls(String tls) {
        this.tls = tls;
    }
}
