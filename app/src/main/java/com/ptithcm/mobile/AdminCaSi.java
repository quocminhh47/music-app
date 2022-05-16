package com.ptithcm.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ptithcm.mobile.model.CaSi;
import com.ptithcm.mobile.service.SendMailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminCaSi extends AppCompatActivity{
    private RequestQueue requestQueue;
    ArrayList<CaSi> listCS = new ArrayList<>();
    ListView lvCaSi;
    Button btnXuat, btnThongKe, btnThem;
    ImageView imgThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ca_si);

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
                startActivity(new Intent(AdminCaSi.this, AdminMainActivity.class));
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idLogout:
                startActivity(new Intent(AdminCaSi.this, MainActivity.class));
                Toast.makeText(this, "LOGOUT", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEvent(){
        lvCaSi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AdminCaSi.this, AdminChiTietCaSi.class);
                CaSi caSi = listCS.get(i);
                intent.putExtra("caSi", caSi);
                startActivity(intent);
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCaSi.this, AddCaSi.class);
                startActivity(intent);

            }
        });

    }

    private void setControl(){

        lvCaSi = findViewById(R.id.lvCaSi);
        btnThongKe = findViewById(R.id.btnThongKe);
        btnXuat = findViewById(R.id.btnXuatPDF);
        imgThumbnail = findViewById(R.id.idImage);
        btnThem = findViewById(R.id.btnThem);

        getTTCaSi();

    }

    private void getTTCaSi() {
        listCS.clear();
        String url = "https://api-qlan-nhom-2.herokuapp.com/api/all/thong-tin-ca-si";
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        CaSi caSi = new CaSi();
                        caSi.setMaCS(j.getString("maCS"));
                        caSi.setTenCS(j.getString("tenCS"));
                        caSi.setUrlImage(j.getString("image"));

                        System.out.println(caSi.toString());
                        listCS.add(caSi);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(listCS.size());

                CustomListViewCaSiAdapter caSiAdapter = new CustomListViewCaSiAdapter(AdminCaSi.this, R.layout.listview_item_ca_si, listCS);
                lvCaSi.setAdapter(caSiAdapter);

                caSiAdapter.notifyDataSetChanged();
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