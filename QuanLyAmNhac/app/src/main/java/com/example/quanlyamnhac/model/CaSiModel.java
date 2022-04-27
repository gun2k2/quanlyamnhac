package com.example.quanlyamnhac.model;

public class CaSiModel {
    private String maCaSi;
    private String tenCaSi;

    public String getMaCaSi() {
        return maCaSi;
    }

    public void setMaCaSi(String maCaSi) {
        this.maCaSi = maCaSi;
    }

    public String getTenCaSi() {
        return tenCaSi;
    }

    public void setTenCaSi(String tenCaSi) {
        this.tenCaSi = tenCaSi;
    }

    public CaSiModel(String maCaSi, String tenCaSi) {
        this.maCaSi = maCaSi;
        this.tenCaSi = tenCaSi;
    }

    public CaSiModel() {
    }
}
