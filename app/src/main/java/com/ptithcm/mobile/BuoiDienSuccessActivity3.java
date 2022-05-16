package com.ptithcm.mobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BuoiDienSuccessActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buoi_dien_success);
        Intent intent = new Intent(this, AdminBuoiDien.class);
        startActivity(intent);
    }
}