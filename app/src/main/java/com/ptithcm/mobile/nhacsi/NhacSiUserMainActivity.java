package com.ptithcm.mobile.nhacsi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.ptithcm.mobile.LoginActivity;
import com.ptithcm.mobile.MainActivity;
import com.ptithcm.mobile.R;
import com.ptithcm.mobile.UserCaSi;
import com.ptithcm.mobile.VolleySingleton;
import com.ptithcm.mobile.service.SendMailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NhacSiUserMainActivity extends AppCompatActivity {

    ListView lvDanhSach;
    ArrayList<NhacSiEntity> dsNhacSi = new ArrayList<>();
    CustomAdapterNhacSiForListView customAdapterNhacSi;
    SearchView searchNhacSi;
    private RequestQueue requestQueue;

    FloatingActionButton btnBackToHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhac_si_user_main_activity);
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
                startActivity(new Intent(NhacSiUserMainActivity.this, MainActivity.class));

                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();

                break;
            case R.id.idLogin:
                startActivity(new Intent(NhacSiUserMainActivity.this, LoginActivity.class));
                Toast.makeText(this, "LOGIN", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idMail:

                startActivity(new Intent(NhacSiUserMainActivity.this, SendMailActivity.class));
                Toast.makeText(this, "MAIL", Toast.LENGTH_SHORT).show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        searchNhacSi = findViewById(R.id.searchNhacSi);
        lvDanhSach = findViewById(R.id.lvDsNhacSi);

    }

    private void setEvent() {
        getDanhSachNS();
        // Bổ sung
        lvDanhSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NhacSiUserMainActivity.this, NhacSiInfo.class);
                NhacSiEntity nhacSi = dsNhacSi.get(position);
                intent.putExtra("nhacSi", nhacSi);
                startActivity(intent);
            }
        });
        searchNhacSi.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapterNhacSi.searchNhacSi(newText);
                return false;
            }
        });
    }

    private void getDanhSachNS(){
        String url = "https://api-qlan-nhom-2.herokuapp.com/musicians";
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        NhacSiEntity nhacSi = new NhacSiEntity();
                        nhacSi.setMaNS(j.getInt("maNS"));
                        nhacSi.setTenNS(j.getString("tenNS"));
                        nhacSi.setGioiThieu(j.getString("gioiThieu"));
                        nhacSi.setHinh(j.getString("hinh"));
                        dsNhacSi.add(nhacSi);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                customAdapterNhacSi = new CustomAdapterNhacSiForListView(NhacSiUserMainActivity.this, R.layout.listview_item_nhacsi, dsNhacSi);
                lvDanhSach.setAdapter((customAdapterNhacSi));
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