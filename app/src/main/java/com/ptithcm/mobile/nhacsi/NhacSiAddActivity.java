package com.ptithcm.mobile.nhacsi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ptithcm.mobile.AdminMainActivity;
import com.ptithcm.mobile.MainActivity;
import com.ptithcm.mobile.R;
import com.ptithcm.mobile.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NhacSiAddActivity extends AppCompatActivity {
    ArrayList<NhacSiEntity> dsNhacSi = new ArrayList<>();
    private RequestQueue requestQueue;

    EditText txtTenNS, txtGioiThieu, txtHinh;
    Button btnThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_nhacsi);
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
                startActivity(new Intent(NhacSiAddActivity.this, AdminMainActivity.class));
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idLogout:
                startActivity(new Intent(NhacSiAddActivity.this, MainActivity.class));
                Toast.makeText(this, "LOGOUT", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setControl(){
        txtTenNS = findViewById(R.id.txtTen);
        txtGioiThieu = findViewById(R.id.txtGioiThieu);
        txtHinh = findViewById(R.id.txtHinh);
        btnThem = findViewById(R.id.btnThem);
    }
    private void goToSuccessActivity() {
        Intent intent = new Intent(NhacSiAddActivity.this, NhacSiSuccessActivity.class);
        startActivity(intent);
    }
    private void setEvent() {
        getDanhSachNS();
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addNhacSi();
                    Toast.makeText(NhacSiAddActivity.this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(NhacSiAddActivity.this, "Lưu thất bại!", Toast.LENGTH_SHORT).show();
                }
                goToSuccessActivity();
            }
        });
    }

    private void addNhacSi() {
        String url = "https://api-qlan-nhom-2.herokuapp.com/musicians";
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        JSONObject jsonObject = new JSONObject();
        try {
            NhacSiEntity nhacSi = getNhacSi();
            jsonObject.put("tenNS", nhacSi.getTenNS());
            jsonObject.put("gioiThieu", nhacSi.getGioiThieu());
            jsonObject.put("hinh", nhacSi.getHinh());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                response -> {
                }, e -> {
            e.printStackTrace();
        }
        );
        requestQueue.add(jsonObjectRequest);
    }

    private NhacSiEntity getNhacSi() {
        NhacSiEntity nhacSi = new NhacSiEntity();
        nhacSi.setTenNS(txtTenNS.getText().toString());
        nhacSi.setGioiThieu(txtGioiThieu.getText().toString());
        nhacSi.setHinh(txtHinh.getText().toString());
        return nhacSi;
    }

    private void troVe() {
        Intent intent = new Intent(NhacSiAddActivity.this, MainNhacSiActivity.class);
        startActivity(intent);
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
