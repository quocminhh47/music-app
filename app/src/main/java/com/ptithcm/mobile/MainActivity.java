package com.ptithcm.mobile;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ptithcm.mobile.baihat.BaiHatUserMainActivity;
import com.ptithcm.mobile.nhacsi.NhacSiUserMainActivity;
import com.ptithcm.mobile.service.SendMailActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btnCS, btnNS, btnBH, btnBD, btnSendMail, btnLogin;
    //Select Image
//    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        setControl();
        setEvent();

    }

    private void setEvent() {
        btnBH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BaiHatUserMainActivity.class);
                startActivity(intent);
            }
        });

        // chi tiet buoi dien
        btnBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChiTietBuoiDienActivity.class);
                startActivity(intent);
            }
        });
        //send Mail
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SendMailActivity.class);
                startActivity(intent);
            }
        });
        //login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnCS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserCaSi.class);
                startActivity(intent);
            }
        });
        btnNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NhacSiUserMainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setControl() {
        btnCS = findViewById(R.id.btnHomeCaSi);
        btnNS = findViewById(R.id.btnHomeNhacSi);
        btnBH = findViewById(R.id.btnHomeBaiHat);
        btnBD = findViewById(R.id.btnHomeBuoiDien);
        btnSendMail = findViewById(R.id.btnHomeMail);
        btnLogin = findViewById(R.id.btnHomeLogin);
    }
//
//    void imageChooser() {
//        Intent i = new Intent();
//        i.setType("image/*");
//        i.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//
//            if (requestCode == SELECT_PICTURE) {
//                Uri selectedImageUri = data.getData();
//                if (null != selectedImageUri) {
//                    try {
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
//                        Drawable dr = new BitmapDrawable(bitmap);
//                        LinearLayout ln = findViewById(R.id.mainLayout);
//                        ln.setBackground(dr);
//                    }
//                    catch(IOException e){
//
//                    }
//                }
//            }
//        }
//    }
}