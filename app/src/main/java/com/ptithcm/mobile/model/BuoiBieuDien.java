package com.ptithcm.mobile.model;

import java.io.Serializable;

public class BuoiBieuDien implements Serializable {
    private String MABD;
    private String MACS;
    private String MABH;
    private String NGAYBD;
    private String DIADIEM;
    private String url;



    public BuoiBieuDien(String MABD, String MACS, String MABH, String NGAYBD, String DIADIEM, String url) {
        this.MABD = MABD;
        this.MACS = MACS;
        this.MABH = MABH;
        this.NGAYBD = NGAYBD;
        this.DIADIEM = DIADIEM;
        this.url = url;
    }

    public String getMABD() {
        return MABD;
    }

    public void setMABD(String MABD) {
        this.MABD = MABD;
    }

    public String getMACS() {
        return MACS;
    }

    public void setMACS(String MACS) {
        this.MACS = MACS;
    }

    public String getMABH() {
        return MABH;
    }

    public void setMABH(String MABH) {
        this.MABH = MABH;
    }

    public String getNGAYBD() {
        return NGAYBD;
    }

    public void setNGAYBD(String NGAYBD) {
        this.NGAYBD = NGAYBD;
    }

    public String getDIADIEM() {
        return DIADIEM;
    }

    public void setDIADIEM(String DIADIEM) {
        this.DIADIEM = DIADIEM;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BuoiBieuDien() {
    }

    @Override
    public String toString() {
        return "BuoiBieuDien{" +
                "MABD='" + MABD + '\'' +
                ", MACS='" + MACS + '\'' +
                ", MABH='" + MABH + '\'' +
                ", NGAYBD='" + NGAYBD + '\'' +
                ", DIADIEM='" + DIADIEM + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
