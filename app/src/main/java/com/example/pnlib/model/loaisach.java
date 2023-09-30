package com.example.pnlib.model;

public class loaisach {
    private int matl;
    private String tentl;

    public loaisach(String tentl) {
        this.tentl = tentl;
    }

    public loaisach() {
    }

    public loaisach(int matl, String tentl) {
        this.matl = matl;
        this.tentl = tentl;
    }

    public int getMatl() {
        return matl;
    }

    public void setMatl(int matl) {
        this.matl = matl;
    }

    public String getTentl() {
        return tentl;
    }

    public void setTentl(String tentl) {
        this.tentl = tentl;
    }
}
