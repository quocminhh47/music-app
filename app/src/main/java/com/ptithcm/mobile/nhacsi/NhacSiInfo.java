package com.ptithcm.mobile.nhacsi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ptithcm.mobile.LoginActivity;
import com.ptithcm.mobile.MainActivity;
import com.ptithcm.mobile.R;
import com.ptithcm.mobile.VolleySingleton;
import com.ptithcm.mobile.baihat.BaiHatCustomAdapterForListView;
import com.ptithcm.mobile.baihat.BaiHatEntity;
import com.ptithcm.mobile.baihat.BaiHatPlayMusicActivity;
import com.ptithcm.mobile.service.DownloadImageTask;
import com.ptithcm.mobile.service.SendMailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NhacSiInfo extends AppCompatActivity {
    TextView tvTenNs, tvGioiThieu;
    ImageView imgAvata;
    ListView dsTacPham;

    ArrayList<BaiHatEntity> danhSachBaiHat = new ArrayList<>();
    BaiHatCustomAdapterForListView customAdapterForListViewDanhSachBaiHatBaiHat;


    private RequestQueue requestQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhacsi_tacpham);
        setControl();
        setEvent();
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
                startActivity(new Intent(NhacSiInfo.this, MainActivity.class));

                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();

                break;
            case R.id.idLogin:
                startActivity(new Intent(NhacSiInfo.this, LoginActivity.class));
                Toast.makeText(this, "LOGIN", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idMail:

                startActivity(new Intent(NhacSiInfo.this, SendMailActivity.class));
                Toast.makeText(this, "MAIL", Toast.LENGTH_SHORT).show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    public void setControl(){
        tvTenNs = findViewById(R.id.tvTenNS);
        tvGioiThieu = findViewById(R.id.tvGioiThieu);
        imgAvata = findViewById(R.id.ivTitle);
        dsTacPham = findViewById(R.id.lvTacPham);
    }

    public void setEvent(){
        NhacSiEntity nhacSi = layDulieu();
        fillNhacSi(nhacSi);
        getDSTacPham(nhacSi.getMaNS());
    }

    private NhacSiEntity layDulieu() {
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            return  (NhacSiEntity) b.get("nhacSi");
        }
        else{
            return  null;
        }
    }

    private void fillNhacSi(NhacSiEntity nhacSi){
        String str = "NHẠC SĨ " + nhacSi.getTenNS().toUpperCase();
        tvTenNs.setText(str);
        tvGioiThieu.setText(nhacSi.getGioiThieu());
        String url = nhacSi.getHinh();
        new DownloadImageTask((ImageView) findViewById(R.id.ivTitle)).execute(url);
    }

    private void getDSTacPham(Integer nhacSiId){
        String url = "https://api-qlan-nhom-2.herokuapp.com/musics/musician/"+nhacSiId;
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        BaiHatEntity baiHatEntity = new BaiHatEntity();
                        baiHatEntity.setMaBH(j.getInt("maBH"));
                        baiHatEntity.setTenBH(j.getString("tenBH"));
                        baiHatEntity.setUrl(j.getString("url"));
                        baiHatEntity.setMaNS(j.getJSONObject("nhacSi").getInt("maNS"));
                        NhacSiEntity nhacSiEntity = new NhacSiEntity();
                        for(int k = 0; k < j.getJSONObject("nhacSi").length(); k++){
                            nhacSiEntity.setMaNS(j.getJSONObject("nhacSi").getInt("maNS"));
                            nhacSiEntity.setTenNS(j.getJSONObject("nhacSi").getString("tenNS"));
                            //Còn có các thông tin khác
                        }
                        baiHatEntity.setNhacSi(nhacSiEntity);
                        danhSachBaiHat.add(baiHatEntity);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                customAdapterForListViewDanhSachBaiHatBaiHat = new BaiHatCustomAdapterForListView(NhacSiInfo.this, R.layout.listview_tacpham, danhSachBaiHat);
                dsTacPham.setAdapter(customAdapterForListViewDanhSachBaiHatBaiHat);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
