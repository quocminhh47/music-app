package com.ptithcm.mobile.nhacsi;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.mobile.R;

public class NhacSiSuccessActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhac_si_success_activity);
        Intent intent = new Intent(this, MainNhacSiActivity.class);
        startActivity(intent);
    }
}