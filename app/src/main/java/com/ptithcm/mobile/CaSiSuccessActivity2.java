package com.ptithcm.mobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CaSiSuccessActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ca_si_success);
        Intent intent = new Intent(this, CaSiSuccessActivity2.class);
        startActivity(intent);
    }
}