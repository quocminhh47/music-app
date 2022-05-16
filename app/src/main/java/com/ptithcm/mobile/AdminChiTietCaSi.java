package com.ptithcm.mobile;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.ptithcm.mobile.model.CaSi;
import com.ptithcm.mobile.nhacsi.NhacSiAddActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminChiTietCaSi extends AppCompatActivity {



    Button btnSua, btnXoa;
    EditText txtMaCS, txtTenCS, txtUrl;

    ArrayList<CaSi> listCS = new ArrayList<>();
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chi_tiet_ca_si);

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
                startActivity(new Intent(AdminChiTietCaSi.this, AdminMainActivity.class));
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idLogout:
                startActivity(new Intent(AdminChiTietCaSi.this, MainActivity.class));
                Toast.makeText(this, "LOGOUT", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);
        txtMaCS = findViewById(R.id.txtMaCS);
        txtTenCS = findViewById(R.id.txtTenCS);
        txtUrl = findViewById(R.id.txtUrl);

        //set field value
        CaSi caSi = getCaSi();
        txtMaCS.setText(caSi.getMaCS());
        txtTenCS.setText(caSi.getTenCS());

        txtUrl.setText(caSi.getUrlImage());
        //setDisplay();





    }

    private void setDisplay() {
        getTTCaSi();

    }
    //can be replaced by get extras
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

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    private CaSi getCaSi() {
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            return  (CaSi) b.get("caSi");
        }
        else{
            return  null;
        }
    }
    private void goToSuccessActivity() {
        Intent intent = new Intent(this, CaSiSuccessActivity.class);
        startActivity(intent);
    }
    private void setEvent() {
        //save
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maCS = txtMaCS.getText().toString();

                String tenCS = txtTenCS.getText().toString();

                String url = txtUrl.getText().toString();

                try {
                    saveCaSi(maCS, tenCS, url);
                    Toast.makeText(AdminChiTietCaSi.this, "succes", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(AdminChiTietCaSi.this, AdminCaSi.class);
//                    startActivity(intent);
                    goToSuccessActivity();
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(AdminChiTietCaSi.this, "failed", Toast.LENGTH_SHORT).show();

                }
            }
        });
        //delete
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maCS = txtMaCS.getText().toString();
                deleteCaSi(maCS);
//                Intent intent = new Intent(AdminChiTietCaSi.this, AdminCaSi.class);
//                startActivity(intent);
                goToSuccessActivity();

            }
        });
    }


    private void saveCaSi(String maCS, String tenCS, String url) {
        RequestQueue queue = Volley.newRequestQueue(AdminChiTietCaSi.this);

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("maCS", maCS);


        postParam.put("tenCS", tenCS);
        postParam.put("image", url);
        String api = "https://api-qlan-nhom-2.herokuapp.com/api/update/ca-si?maCS=" + maCS + "&tenCS=" + tenCS + "&image=" + url + "";
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

    private void deleteCaSi(String maCS){

        RequestQueue queue = Volley.newRequestQueue(AdminChiTietCaSi.this);
        String url = "https://api-qlan-nhom-2.herokuapp.com/api/delete/ca-si-"+maCS+"";
        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(AdminChiTietCaSi.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.
                        Toast.makeText(AdminChiTietCaSi.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        queue.add(dr);
    }
}