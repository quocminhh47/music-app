package com.ptithcm.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ptithcm.mobile.baihat.BaiHatAdminMainActivity;
import com.ptithcm.mobile.nhacsi.NhacSiSuccessActivity2;

public class BuoiDienSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buoi_dien_success);
        Intent intent = new Intent(this, BuoiDienSuccessActivity2.class);
        startActivity(intent);
    }
}