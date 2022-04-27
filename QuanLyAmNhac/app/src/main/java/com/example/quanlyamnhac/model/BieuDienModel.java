package com.example.quanlyamnhac.model;

public class BieuDienModel {
    private int MaBieuDien;
    private String maCaSi;
    private String maBaiHat;
    private String ngayBieuDien;
    private String diaDiem;

    public BieuDienModel() {
    }

    public BieuDienModel(int MaBieuDien, String maCaSi, String maBaiHat, String ngayBieuDien, String diaDiem) {
        this.maCaSi = maCaSi;
        this.maBaiHat = maBaiHat;
        this.ngayBieuDien = ngayBieuDien;
        this.diaDiem = diaDiem;
        this.MaBieuDien =MaBieuDien;
    }


    public int getMaBieuDien() {
        return MaBieuDien;
    }

    public void setMaBieuDien(int maBieuDien) {
        MaBieuDien = maBieuDien;
    }

    public String getMaCaSi() {
        return maCaSi;
    }

    public void setMaCaSi(String maCaSi) {
        this.maCaSi = maCaSi;
    }


    public String getMaBaiHat() {
        return maBaiHat;
    }

    public void setMaBaiHat(String maBaiHat) {
        this.maBaiHat = maBaiHat;
    }

    public String getNgayBieuDien() {
        return ngayBieuDien;
    }

    public void setNgayBieuDien(String ngayBieuDien) {
        this.ngayBieuDien = ngayBieuDien;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }
}
