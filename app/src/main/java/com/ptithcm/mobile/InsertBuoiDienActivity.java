package com.ptithcm.mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ptithcm.mobile.model.BuoiBieuDien;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class InsertBuoiDienActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    String maCS;
    String maBH;
    Spinner spinner_maCS ;
    Spinner spinner_maBH;
    SingerSpinnerAdapter singerAdapter;
    SongSpinnerAdapter songAdapter;
    Button btnCreate;
    ArrayList<String> listMaBaiHat = new ArrayList<>();
    ArrayList<String> listMaCaSi = new ArrayList<>();
    EditText maBD, ngayBD, diaDiem, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_buoi_dien);

        setControl();
        khoiTao();
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
                startActivity(new Intent(InsertBuoiDienActivity.this, AdminMainActivity.class));
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idLogout:
                startActivity(new Intent(InsertBuoiDienActivity.this, MainActivity.class));
                Toast.makeText(this, "LOGOUT", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void goToSuccessActivity() {
        Intent intent = new Intent(this, BuoiDienSuccessActivity.class);
        startActivity(intent);
    }
    private void setEvent() {
        //new
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveBuoiDien();
//                    Toast.makeText(InsertBuoiDienActivity.this, "success", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(InsertBuoiDienActivity.this, AdminBuoiDien.class);
//                    startActivity(intent);
                    goToSuccessActivity();
                } catch (Exception e){
                    e.printStackTrace();
//                    Toast.makeText(InsertBuoiDienActivity.this, "failed", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private  void saveBuoiDien(){
        String maBuoiDien = maBD.getText().toString();
        String ngayBieuDien = ngayBD.getText().toString();
        String dd = diaDiem.getText().toString();
        String urlImg = url.getText().toString();
        //RequestQueue queue = Volley.newRequestQueue(InsertBuoiDienActivity.this);
        final String api = "https://api-qlan-nhom-2.herokuapp.com/api/insert/buoi-dien?maBD="+maBuoiDien+"&maCS="+maCS+"&maBH="+maBH+"&ngayBD="+ngayBieuDien+"&diaDiem="+dd+"&url="+urlImg;
        System.out.println(api);
        // prepare the Request
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(InsertBuoiDienActivity.this);
            //String URL = "http://192.168.1.56:8080/api/comment-buoi-dien?name="+name+"&message="+mess+"";
           // System.out.println(URL);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("maBD", maBuoiDien);
            jsonBody.put("maCS", maCS);
            jsonBody.put("maBH", maBH);
            jsonBody.put("ngayBD", ngayBieuDien);
            jsonBody.put("diaDiem", dd);
            jsonBody.put("url", urlImg);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, api, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    Toast.makeText(InsertBuoiDienActivity.this, "success", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(InsertBuoiDienActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        Toast.makeText(InsertBuoiDienActivity.this, "failed", Toast.LENGTH_SHORT).show();

                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        System.out.println("response string "+responseString);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            Toast.makeText(InsertBuoiDienActivity.this, "failed", Toast.LENGTH_SHORT).show();
            Toast.makeText(InsertBuoiDienActivity.this, " failed!!", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }

// add it to the RequestQueue
        //queue.add(getRequest);
    }

    private void setControl() {
        spinner_maCS = findViewById(R.id.spinner_maCS);
        spinner_maBH = findViewById(R.id.spinner_maBH);
        btnCreate = findViewById(R.id.btnCreate);
        maBD = findViewById(R.id.txtAdminMaBD);
        ngayBD = findViewById(R.id.txtAdminNgayBD);
        diaDiem = findViewById(R.id.txtAdminDiaDiem);
        url = findViewById(R.id.txtUrl);

    }
    private void khoiTao() {
        maCS="";
        maBH="";
        getBaiHat();
        getCaSi();
       // listMaCaSi =getCaSi();
        //listMaBaiHat = getBaiHat();

        //add spinner ma cs
        //singerAdapter = new SingerSpinnerAdapter(InsertBuoiDienActivity.this, listMaCaSi);
        //ArrayAdapter maCS_Adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listMaCaSi);
        //.setAdapter(maCS_Adapter);
        spinner_maCS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maCS=listMaCaSi.get(i);
                Toast.makeText(InsertBuoiDienActivity.this, maCS, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(InsertBuoiDienActivity.this, maBH, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    private void getBaiHat() {

        listMaBaiHat.clear();

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
                        String maCS = j.getString("maBH");
                        listMaBaiHat.add(maCS);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                songAdapter = new SongSpinnerAdapter(InsertBuoiDienActivity.this, listMaBaiHat);
                spinner_maBH.setAdapter(songAdapter);


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
        listMaCaSi.clear();

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
                        String maCS = j.getString("maCS");
                        listMaCaSi.add(maCS);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                singerAdapter = new SingerSpinnerAdapter(InsertBuoiDienActivity.this, listMaCaSi);
                spinner_maCS.setAdapter(singerAdapter);

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