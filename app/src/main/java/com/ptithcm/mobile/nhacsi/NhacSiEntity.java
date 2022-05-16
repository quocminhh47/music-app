package com.ptithcm.mobile.nhacsi;

import java.io.Serializable;

public class NhacSiEntity implements Serializable {
    Integer maNS;
    String tenNS, gioiThieu, hinh;

    public Integer getMaNS() {
        return maNS;
    }

    public void setMaNS(Integer maNS) {
        this.maNS = maNS;
    }

    public String getTenNS() {
        return tenNS;
    }

    public void setTenNS(String tenNS) {
        this.tenNS = tenNS;
    }

    public String getGioiThieu() {
        return gioiThieu;
    }

    public void setGioiThieu(String gioiThieu) {
        this.gioiThieu = gioiThieu;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}
