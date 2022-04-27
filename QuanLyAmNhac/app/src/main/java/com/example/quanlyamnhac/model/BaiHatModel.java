package com.example.quanlyamnhac.model;

public class BaiHatModel {
    private String maBaiHat;
    private String tenBaiHat;
    private String namSangTac;
    private String manhacsi;

    public BaiHatModel() {
    }

    public String getMaBaiHat() {
        return maBaiHat;
    }

    public void setMaBaiHat(String maBaiHat) {
        this.maBaiHat = maBaiHat;
    }

    public String getTenBaiHat() {
        return tenBaiHat;
    }

    public void setTenBaiHat(String tenBaiHat) {
        this.tenBaiHat = tenBaiHat;
    }

    public String getNamSangTac() {
        return namSangTac;
    }

    public void setNamSangTac(String namSangTac) {
        this.namSangTac = namSangTac;
    }

    public String getManhacsi() {
        return manhacsi;
    }

    public void setManhacsi(String manhacsi) {
        this.manhacsi = manhacsi;
    }

    public BaiHatModel(String maBaiHat, String tenBaiHat, String namSangTac, String manhacsi) {
        this.maBaiHat = maBaiHat;
        this.tenBaiHat = tenBaiHat;
        this.namSangTac = namSangTac;
        this.manhacsi = manhacsi;
    }
}
