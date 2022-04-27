package com.example.quanlyamnhac.model;

public class NhacSiModel {
    private String id;
    private String name;

    public NhacSiModel() {
    }

    public NhacSiModel(String id, String name) {
        this.id = id;
        this.name = name;

    }

    public NhacSiModel(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String maCaSi) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String tenCasi) {
        this.name = name;
    }



}
