package com.ptithcm.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ptithcm.mobile.model.CaSi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class AddCaSi extends AppCompatActivity {
    ListView lvCaSi;

    ArrayList<CaSi> listCS = new ArrayList<>();
    private RequestQueue requestQueue;

    EditText txtTenCS, txtMaCS, txtHinh;
    Button btnThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ca_si);
        setControl();
        setEvent();
    }

    public void setControl(){
        txtMaCS = findViewById(R.id.txtMaCS);
        txtTenCS= findViewById(R.id.txtTenCS);
        txtHinh = findViewById(R.id.txtUrl);
        btnThem = findViewById(R.id.btnInsert);
    }
    private void goToSuccessActivity() {
        Intent intent = new Intent(this, CaSiSuccessActivity.class);
        startActivity(intent);
    }
    private void setEvent() {
        //new
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveCaSi();
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

    private  void saveCaSi(){
        String maCS = txtMaCS.getText().toString();
        String tenCS = txtTenCS.getText().toString();

        String urlImg = txtHinh.getText().toString();
        //RequestQueue queue = Volley.newRequestQueue(InsertBuoiDienActivity.this);
        final String api = "https://api-qlan-nhom-2.herokuapp.com/api/insert/ca-si?maCS="+maCS+"&tenCS="+tenCS+"&image="+urlImg;
        System.out.println(api);
        // prepare the Request
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(AddCaSi.this);
            //String URL = "http://192.168.1.56:8080/api/comment-buoi-dien?name="+name+"&message="+mess+"";
            // System.out.println(URL);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("maCS", maCS);
            jsonBody.put("tenCS", tenCS);

            jsonBody.put("url", urlImg);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, api, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    Toast.makeText(AddCaSi.this, "success", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(AddCaSi.this, AdminCaSi.class);
//                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddCaSi.this, "failed", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(AddCaSi.this, "failed", Toast.LENGTH_SHORT).show();

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
            Toast.makeText(AddCaSi.this, "failed", Toast.LENGTH_SHORT).show();
            Toast.makeText(AddCaSi.this, " failed!!", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }


    }
}