package com.ptithcm.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.mobile.model.CaSi;
import com.ptithcm.mobile.service.DownloadImageTask;
import com.ptithcm.mobile.service.SendMailActivity;

public class ChiTietCaSi extends AppCompatActivity {

    TextView tvMaCS, tvTenCS;
    ImageView imgViewCaSi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ca_si);
        setControl();
    }

    private void setControl() {
        tvMaCS = findViewById(R.id.tvMaCS);
        tvTenCS = findViewById(R.id.tvTenCS);
        imgViewCaSi = findViewById(R.id.imgViewCaSi);
        //
        getData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_demo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.idHome:
                startActivity(new Intent(ChiTietCaSi.this, MainActivity.class));

                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();

                break;
            case R.id.idLogin:
                startActivity(new Intent(ChiTietCaSi.this, LoginActivity.class));
                Toast.makeText(this, "LOGIN", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idMail:

                startActivity(new Intent(ChiTietCaSi.this, SendMailActivity.class));
                Toast.makeText(this, "MAIL", Toast.LENGTH_SHORT).show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void getData() {
        CaSi caSi = getCaSi();

        System.out.println(caSi.toString());

        tvMaCS.setText(caSi.getMaCS());
        tvTenCS.setText(caSi.getTenCS());
        String url = caSi.getUrlImage();
        System.out.println(url);
        new DownloadImageTask((ImageView) findViewById(R.id.imgViewCaSi))
                .execute(url);
    }

    private CaSi getCaSi() {
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            return  (CaSi) b.get("caSi");
        }
        else{
            return  null;
        }
    }
}