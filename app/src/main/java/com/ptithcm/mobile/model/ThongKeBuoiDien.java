package com.ptithcm.mobile.model;

import java.io.Serializable;

public class ThongKeBuoiDien implements Serializable {
    private String diaDiem;
    private int count;

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ThongKeBuoiDien(String diaDiem, int count) {
        this.diaDiem = diaDiem;
        this.count = count;
    }

    public ThongKeBuoiDien() {
    }
}
