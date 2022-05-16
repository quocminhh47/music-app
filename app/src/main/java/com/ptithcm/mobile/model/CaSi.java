package com.ptithcm.mobile.model;

import java.io.Serializable;

public class CaSi implements Serializable {
    private String maCS;
    private String tenCS;
    private String urlImage;

    public String getMaCS() {
        return maCS;
    }

    public void setMaCS(String maCS) {
        this.maCS = maCS;
    }

    public String getTenCS() {
        return tenCS;
    }

    public void setTenCS(String tenCS) {
        this.tenCS = tenCS;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return "CaSi{" +
                "MACS='" + maCS + '\'' +
                ",TENCS='" + tenCS + '\'' +
                ",IMAGE='" + urlImage + '\'' +
                '}';
    }
}
