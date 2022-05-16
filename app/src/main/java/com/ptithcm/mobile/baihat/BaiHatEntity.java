package com.ptithcm.mobile.baihat;

import com.ptithcm.mobile.nhacsi.NhacSiEntity;

import java.io.Serializable;

public class BaiHatEntity implements Serializable {
    Integer maBH;
    String tenBH, url, namST;
    Integer maNS;
    NhacSiEntity nhacSi;

    public NhacSiEntity getNhacSi() {
        return nhacSi;
    }

    public void setNhacSi(NhacSiEntity nhacSi) {
        this.nhacSi = nhacSi;
    }

    public Integer getMaBH() {
        return maBH;
    }

    public void setMaBH(Integer maBH) {
        this.maBH = maBH;
    }

    public String getTenBH() {
        return tenBH;
    }

    public void setTenBH(String tenBH) {
        this.tenBH = tenBH;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNamST() {
        return namST;
    }

    public void setNamST(String namST) {
        this.namST = namST;
    }

    public Integer getMaNS() {
        return maNS;
    }

    public void setMaNS(Integer maNS) {
        this.maNS = maNS;
    }
}
