package com.ptithcm.mobile.baihat;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.mobile.R;

public class BaiHatSuccessActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bai_hat_success_activity);
        Intent intent = new Intent(this, BaiHatAdminMainActivity.class);
        startActivity(intent);
    }

}