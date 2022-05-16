package com.ptithcm.mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ptithcm.mobile.model.BuoiBieuDien;
import com.ptithcm.mobile.model.ThongKeBuoiDien;
import com.ptithcm.mobile.service.SendMailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThongKeBuoiDienActivity extends AppCompatActivity {

    ListView lvThongKe;
    private RequestQueue requestQueue;

    ArrayList<ThongKeBuoiDien> listTKBD = new ArrayList<>();
    ArrayList<BuoiBieuDien> listBD = new ArrayList<>();
    //ArrayList<BuoiBieuDien> listBD2 = new ArrayList<>();
    Map<String, Integer> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_buoi_dien);

        setControl();

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
                startActivity(new Intent(ThongKeBuoiDienActivity.this, MainActivity.class));

                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();

                break;
            case R.id.idLogin:
                startActivity(new Intent(ThongKeBuoiDienActivity.this, LoginActivity.class));
                Toast.makeText(this, "LOGIN", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idMail:

                startActivity(new Intent(ThongKeBuoiDienActivity.this, SendMailActivity.class));
                Toast.makeText(this, "MAIL", Toast.LENGTH_SHORT).show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        lvThongKe = findViewById(R.id.lvThongKeBuoiDien);
        getTTBuoiDien();


//        for(int i = 0; i < listBD2.size(); i++){
//            String key = listBD2.get(i).getDIADIEM().replaceAll(" ","");
//            if(map.containsKey(key)){
//                map.put(key, map.get(key)+1);
//            }
//            else map.put(key, 1);
//        }
//
//
//        System.out.println("map: "+ map);
//        for(String key : map.keySet()){
//            ThongKeBuoiDien thongKeBuoiDien = new ThongKeBuoiDien();
//            thongKeBuoiDien.setDiaDiem(key);
//            thongKeBuoiDien.setCount(map.get(key));
//        }
//        System.out.println("list: "+ listTKBD.size());

    }

    private void getTTBuoiDien() {
        listBD.clear();
        String url = "https://api-qlan-nhom-2.herokuapp.com/api/all/thong-tin-buoi-dien";
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        BuoiBieuDien bd = new BuoiBieuDien();
                        bd.setMABD(j.getString("maBD"));
                        bd.setNGAYBD(j.getString("ngayBD"));
                        bd.setDIADIEM(j.getString("diaDiem"));
                        bd.setMABH(j.getString("maBH"));
                        bd.setMACS(j.getString("maCS"));
                        bd.setUrl(j.getString("url"));
                        System.out.println(bd.toString());
                        listBD.add(bd);
                    }
                    // put place and frequency to map
                    for(int i = 0; i < listBD.size(); i++){
                        String key = listBD.get(i).getDIADIEM().replaceAll(" ","");
                        if(map.containsKey(key)){
                            map.put(key, map.get(key)+1);
                        }
                        else map.put(key, 1);
                    }
                    System.out.println("map: "+ map);
                    // add data from map to list
                    for(String key : map.keySet()){
                        ThongKeBuoiDien thongKeBuoiDien = new ThongKeBuoiDien();
                        thongKeBuoiDien.setDiaDiem(key);
                        thongKeBuoiDien.setCount(map.get(key));
                        listTKBD.add(thongKeBuoiDien);
                    }
                    System.out.println("list: "+ listTKBD.size());
                   CustomAdapterThongKeBD adapterThongKeBD = new CustomAdapterThongKeBD(ThongKeBuoiDienActivity.this, R.layout.listview_item_thong_ke_buoi_dien, listTKBD);
                    lvThongKe.setAdapter(adapterThongKeBD);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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