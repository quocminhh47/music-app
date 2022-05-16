package com.ptithcm.mobile;

import static com.itextpdf.io.codec.brotli.dec.Dictionary.getData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ptithcm.mobile.model.BuoiBieuDien;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminBuoiDien extends AppCompatActivity {
    private RequestQueue requestQueue;
    CustomListViewBuoiBDAdapter adapterBuoiBD;

    ListView lvBuoiDien;
    ArrayList<BuoiBieuDien> listBD = new ArrayList<>();
    Button btnNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_buoi_dien);

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
        switch (item.getItemId()) {
            case R.id.idHome:
                startActivity(new Intent(AdminBuoiDien.this, AdminMainActivity.class));
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idLogout:
                startActivity(new Intent(AdminBuoiDien.this, MainActivity.class));
                Toast.makeText(this, "LOGOUT", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEvent() {
        lvBuoiDien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AdminBuoiDien.this, AdminEditBuoiDien.class);
                BuoiBieuDien buoiDien = listBD.get(i);
                intent.putExtra("buoiDien", buoiDien);

                startActivity(intent);
            }
        });

        //insert new
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminBuoiDien.this, InsertBuoiDienActivity.class);
                startActivity(intent);

            }
        });
    }

    private void setControl() {
        lvBuoiDien = findViewById(R.id.lvAdminBuoiDien);
        btnNew = findViewById(R.id.btnNew);
        getTTBuoiDien();

    }

    private void getTTBuoiDien() {
        listBD.clear();
        String url = "https://api-qlan-nhom-2.herokuapp.com/api/all/thong-tin-buoi-dien";
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;
                    for (int i = 0; i < jsonArray.length(); i++) {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(listBD.size());

                if (adapterBuoiBD != null) {
                    adapterBuoiBD.clear();
                }

                adapterBuoiBD = new CustomListViewBuoiBDAdapter(AdminBuoiDien.this, R.layout.listview_items_buoi_dien, listBD);
                adapterBuoiBD.notifyDataSetChanged();
                lvBuoiDien.setAdapter(adapterBuoiBD);


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