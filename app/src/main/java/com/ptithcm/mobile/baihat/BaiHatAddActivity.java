package com.ptithcm.mobile.baihat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.ptithcm.mobile.nhacsi.NhacSiEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BaiHatAddActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    //
    EditText txtTenBH, txtNamST, textViewURL;
    //Use for insert function
    Integer maNS;

    Button btnThem, btnTroVe;


    //For Spinner
    ArrayList<NhacSiEntity> danhSachNhacSi = new ArrayList<>();
    BaiHatCustomSpinner baiHatCustomSpinner;
    Spinner spinnerDanhSachNhacSi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bai_hat_add_activity);
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
                startActivity(new Intent(BaiHatAddActivity.this, AdminMainActivity.class));
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idLogout:
                startActivity(new Intent(BaiHatAddActivity.this, MainActivity.class));
                Toast.makeText(this, "LOGOUT", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToSuccessActivity() {
        Intent intent = new Intent(BaiHatAddActivity.this, BaiHatSuccessActivity.class);
        startActivity(intent);
    }

    private void troVe() {
        Intent intent = new Intent(BaiHatAddActivity.this, BaiHatSuccessActivity.class);
        startActivity(intent);
    }

    private void setControl() {
        textViewURL = findViewById(R.id.textViewURL);
        txtTenBH = findViewById(R.id.txtTenBH);
        spinnerDanhSachNhacSi = findViewById(R.id.spinnerDSMNS);
//        txtNamST = findViewById(R.id.txtNamST);
        btnTroVe = findViewById(R.id.btnTroVe);
        btnThem = findViewById(R.id.btnThem);
    }

    private void setEvent() {
        init();
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                troVe();
            }
        });
        spinnerDanhSachNhacSi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maNS = danhSachNhacSi.get(i).getMaNS();
                Toast.makeText(BaiHatAddActivity.this, maNS.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addBaiHat();
                    Toast.makeText(BaiHatAddActivity.this, "Lưu bài hát thành công!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(BaiHatAddActivity.this, "Lưu bài hát thất bại!", Toast.LENGTH_SHORT).show();
                }
                goToSuccessActivity();
            }
        });
    }

    private void init() {
        fillData();
    }

    private void fillData() {
        getDanhSachNhacSi();
    }

    //get data form edit text
    private BaiHatEntity getBaiHat() {
        BaiHatEntity BaiHat = new BaiHatEntity();
        BaiHat.setTenBH(txtTenBH.getText().toString());
        BaiHat.setUrl(textViewURL.getText().toString());
        NhacSiEntity nhacSiEntity = new NhacSiEntity();
        nhacSiEntity.setMaNS(maNS);
        BaiHat.setNhacSi(nhacSiEntity);
        return BaiHat;
    }

    //set data for spinner
    private void getDanhSachNhacSi() {
        String url = "https://api-qlan-nhom-2.herokuapp.com/musicians";
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        NhacSiEntity nhacSiEntity = new NhacSiEntity();
                        nhacSiEntity.setMaNS(j.getInt("maNS"));
                        nhacSiEntity.setTenNS(j.getString("tenNS"));
                        danhSachNhacSi.add(nhacSiEntity);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                baiHatCustomSpinner = new BaiHatCustomSpinner(BaiHatAddActivity.this, danhSachNhacSi);
                spinnerDanhSachNhacSi.setAdapter(baiHatCustomSpinner);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void addBaiHat() {
        String url = "https://api-qlan-nhom-2.herokuapp.com/musics";
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        JSONObject jsonObject = new JSONObject();
        JSONObject nhacSiJSONObject = new JSONObject();
        BaiHatEntity baiHatEntity = getBaiHat();
        try {
            nhacSiJSONObject.put("maNS", baiHatEntity.getNhacSi().getMaNS());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject.put("tenBH", baiHatEntity.getTenBH());
            jsonObject.put("url", baiHatEntity.getUrl());
            jsonObject.put("nhacSi", nhacSiJSONObject);

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
}