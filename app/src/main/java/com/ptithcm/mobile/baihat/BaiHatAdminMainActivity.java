package com.ptithcm.mobile.baihat;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ptithcm.mobile.AdminMainActivity;
import com.ptithcm.mobile.MainActivity;
import com.ptithcm.mobile.R;
import com.ptithcm.mobile.VolleySingleton;
import com.ptithcm.mobile.nhacsi.MainNhacSiActivity;
import com.ptithcm.mobile.nhacsi.NhacSiEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class BaiHatAdminMainActivity extends AppCompatActivity {
    Button btnThem, btnTK;

    FloatingActionButton btnBackToHome;

    ListView listViewDanhSachBaiHat;

    SearchView searchView;

    ArrayList<BaiHatEntity> danhSachBaiHat = new ArrayList<>();
    BaiHatCustomAdapterForListView customAdapterForListViewDanhSachBaiHatBaiHat;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bai_hat_admin_main_activity);
        setControl();
        setEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.idHome:
                startActivity(new Intent(BaiHatAdminMainActivity.this, AdminMainActivity.class));
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idLogout:
                startActivity(new Intent(BaiHatAdminMainActivity.this, MainActivity.class));
                Toast.makeText(this, "LOGOUT", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void troVe() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void setControl() {
        btnThem = findViewById(R.id.btnThem);
//        btnTK= findViewById(R.id.btnTK);

        listViewDanhSachBaiHat = findViewById(R.id.listViewDanhSachBaiHat);
        searchView = findViewById(R.id.searchView);
    }
    private void setEvent() {
        danhSachBaiHat.clear();
        getDanhSachBaiHat();

        listViewDanhSachBaiHat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BaiHatAdminMainActivity.this, BaiHatEditDeleteActivity.class);
                BaiHatEntity baiHat =danhSachBaiHat.get(i);
                intent.putExtra("baiHat", baiHat);
                startActivity(intent);
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaiHatAdminMainActivity.this, BaiHatAddActivity.class);
                startActivity(intent);
            }
        });
//        btnTK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(BaiHatAdminMainActivity.this, BaiHatChartActivity.class);
//                startActivity(intent);
//            }
//        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapterForListViewDanhSachBaiHatBaiHat.filter(newText);
                return false;
            }
        });

    }
    private void getDanhSachBaiHat() {
        String url = "https://api-qlan-nhom-2.herokuapp.com/musics";
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
                customAdapterForListViewDanhSachBaiHatBaiHat = new BaiHatCustomAdapterForListView(BaiHatAdminMainActivity.this, R.layout.bai_hat_layout_item, danhSachBaiHat);
                listViewDanhSachBaiHat.setAdapter(customAdapterForListViewDanhSachBaiHatBaiHat);
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
