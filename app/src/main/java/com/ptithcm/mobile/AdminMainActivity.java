package com.ptithcm.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ptithcm.mobile.baihat.BaiHatAddActivity;
import com.ptithcm.mobile.baihat.BaiHatAdminMainActivity;
import com.ptithcm.mobile.nhacsi.MainNhacSiActivity;
import com.ptithcm.mobile.service.SendMailActivity;

public class AdminMainActivity extends AppCompatActivity {

    Button btnCS, btnNS, btnBH, btnBD, btnLogout;
    //Select Image
//    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        setControl();
        setEvent();

    }

    private void setEvent() {
        btnBH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, BaiHatAdminMainActivity.class);
                startActivity(intent);
            }
        });

        // chi tiet buoi dien
        btnBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, AdminBuoiDien.class);
                startActivity(intent);
            }
        });


        //login
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminMainActivity.this, "Logout success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminMainActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        btnCS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, AdminCaSi.class);
                startActivity(intent);
            }
        });
        btnNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, MainNhacSiActivity.class);
                startActivity(intent);

            }
        });

    }

    private void setControl() {
        btnCS = findViewById(R.id.btnAdminHomeCaSi);
        btnNS = findViewById(R.id.btnAdminHomeNhacSi);
        btnBH = findViewById(R.id.btnAdminHomeBaiHat);
        btnBD = findViewById(R.id.btnAdminHomeBuoiDien);

        btnLogout = findViewById(R.id.btnAdminHomeLogout);
    }
}