package com.ptithcm.mobile;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ptithcm.mobile.baihat.BaiHatAddActivity;
import com.ptithcm.mobile.baihat.BaiHatSuccessActivity;
import com.ptithcm.mobile.model.BuoiBieuDien;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminEditBuoiDien extends AppCompatActivity {
    Button btnSave, btnDelete;
    EditText txtMaBD, txtNgayBD, txtDiaDiem, txtUrl;
    String maCS, maBH;
    ArrayList<BuoiBieuDien> listBD = new ArrayList<>();
    private RequestQueue requestQueue;
    Spinner spinner_maBH, spinner_maCS;
    ArrayList<String> listMaBaiHat = new ArrayList<>();
    ArrayList<String> listMaCaSi = new ArrayList<>();
    SingerSpinnerAdapter singerAdapter;
    SongSpinnerAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_buoi_dien);

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
                startActivity(new Intent(AdminEditBuoiDien.this, AdminMainActivity.class));
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idLogout:
                startActivity(new Intent(AdminEditBuoiDien.this, MainActivity.class));
                Toast.makeText(this, "LOGOUT", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        btnDelete = findViewById(R.id.btnDelete);
        btnSave = findViewById(R.id.btnSave);
        txtMaBD = findViewById(R.id.txtAdminMaBD);
        txtDiaDiem = findViewById(R.id.txtAdminDiaDiem);
        txtNgayBD = findViewById(R.id.txtAdminNgayBD);
        txtUrl = findViewById(R.id.txtUrl);
        spinner_maBH = findViewById(R.id.spinner_maBH);
        spinner_maCS = findViewById(R.id.spinner_maCS);
        //set field value
        BuoiBieuDien bd = getBuoiDien();
        txtMaBD.setText(bd.getMABD());
        txtDiaDiem.setText(bd.getDIADIEM());
        txtNgayBD.setText(bd.getNGAYBD());
        txtUrl.setText(bd.getUrl());
        //get list  to poor into spinner
        getCaSi();
        getBaiHat();
        //display value for spinner
        setDisplay();
        //get macasi and mabh form spinner
        spinner_maCS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maCS=listMaCaSi.get(i);
                Toast.makeText(AdminEditBuoiDien.this, maCS, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinner_maBH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Adapter adapter = adapterView.getAdapter();
                maBH = listMaBaiHat.get(i);
                Toast.makeText(AdminEditBuoiDien.this, maBH, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setDisplay() {
       getTTBuoiDien();
        BuoiBieuDien bd = getBuoiDien();
        String mabaihat = bd.getMABH().trim();
        String macasi = bd.getMACS().trim();
        System.out.println("mabh: "+ bd.getMABH() +"ma cs: " +bd.getMACS());
        System.out.println("list bd: "+ listBD.size());
        for(int i = 0; i < listMaBaiHat.size(); i++ ){
            if(mabaihat.equals(listBD.get(i).getMABH().trim())){
                spinner_maBH.setSelection(i);
                break;
            }
        }
        for(int i = 0; i < listMaCaSi.size(); i++ ){
            if(macasi.equals(listBD.get(i).getMACS().trim())){
                spinner_maCS.setSelection(i);
                break;
            }
        }
    }
    //can be replaced by get extras
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(listBD.size());

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    private BuoiBieuDien getBuoiDien() {
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            return  (BuoiBieuDien) b.get("buoiDien");
        }
        else{
            return  null;
        }
    }
    private void goToSuccessActivity() {
        Intent intent = new Intent(this, BuoiDienSuccessActivity.class);
        startActivity(intent);
    }
    private void setEvent() {
        //save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maBD = txtMaBD.getText().toString();

                String diaDiem = txtDiaDiem.getText().toString();
                String ngayBD = txtNgayBD.getText().toString();
                String url = txtUrl.getText().toString();

                try {
                    saveBuoiDien(maBD, maCS, maBH, ngayBD, diaDiem, url);
                    Toast.makeText(AdminEditBuoiDien.this, "succes", Toast.LENGTH_SHORT).show();
                    goToSuccessActivity();
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(AdminEditBuoiDien.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //delete
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String maBD = txtMaBD.getText().toString();
                deleteBuoiDien(maBD);
                goToSuccessActivity();


            }
        });
    }


    private void saveBuoiDien(String maBD, String maCS, String maBH, String ngayBD, String diaDiem, String url) {
        RequestQueue queue = Volley.newRequestQueue(AdminEditBuoiDien.this);

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("maBD", maBD);
        postParam.put("maBH", maBH);
        postParam.put("maCS", maCS);
        postParam.put("ngayBD", ngayBD);
        postParam.put("diaDiem", diaDiem);
        postParam.put("url", url);
        String api = "https://api-qlan-nhom-2.herokuapp.com/api/update/buoi-dien?maBD=" + maBD + "&maCS=" + maCS + "&maBH=" + maBH + "&ngayBD=" + ngayBD + "&diaDiem=" + diaDiem + "&url=" + url + "";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.PUT,
                api, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                      //  msgResponse.setText(response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        jsonObjReq.setTag(TAG);
       queue.add(jsonObjReq);
    }

    private void deleteBuoiDien(String maBD){

        RequestQueue queue = Volley.newRequestQueue(AdminEditBuoiDien.this);
        String url = "https://api-qlan-nhom-2.herokuapp.com/api/delete/buoi-dien-"+maBD+"";
        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response

                        Toast.makeText(AdminEditBuoiDien.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.
                        Toast.makeText(AdminEditBuoiDien.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }

        );
        queue.add(dr);
    }
    private void getBaiHat() {

        //listMaBaiHat.clear();
        maBH="";
        //link api
        String url = "https://api-qlan-nhom-2.herokuapp.com/api/get-all-bai-hat";
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        String maBaiHat = j.getString("maBH");
                        listMaBaiHat.add(maBaiHat);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                songAdapter = new SongSpinnerAdapter(AdminEditBuoiDien.this, listMaBaiHat);
                spinner_maBH.setAdapter(songAdapter);
                getTTBuoiDien();
                BuoiBieuDien bd = getBuoiDien();
                String mabaihat = bd.getMABH().trim();
                for(int i = 0; i < listMaBaiHat.size(); i++ ){
                    if(mabaihat.equals(listMaBaiHat.get(i).trim())){
                        spinner_maBH.setSelection(i);
                        break;
                    }
                }
//                spinner_maBH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        //Adapter adapter = adapterView.getAdapter();
//                        maBH = listMaBaiHat.get(i);
//                        Toast.makeText(AdminEditBuoiDien.this, maBH, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest);
        //return listMaBaiHat;
    }

    private void getCaSi() {
        //listMaCaSi.clear();
        maCS = "";
        maBH = "";
        //link api
        String url = "https://api-qlan-nhom-2.herokuapp.com/api/get-all-ca-si";
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        String maCaSi = j.getString("maCS");
                        listMaCaSi.add(maCaSi);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                singerAdapter = new SingerSpinnerAdapter(AdminEditBuoiDien.this, listMaCaSi);
                spinner_maCS.setAdapter(singerAdapter);
                getTTBuoiDien();
                BuoiBieuDien bd = getBuoiDien();
                String macasi = bd.getMACS().trim();
                if(!listMaCaSi.isEmpty()){
                    for(int i = 0; i < listMaCaSi.size(); i++ ){
                        if(macasi.equals(listMaCaSi.get(i).trim())){
                            spinner_maCS.setSelection(i);
                            break;
                        }
                    }
                }
//                spinner_maCS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        maCS=listMaCaSi.get(i);
//                        Toast.makeText(AdminEditBuoiDien.this, maCS, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest);
        // return listMaCaSi;
    }

}