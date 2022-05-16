package com.ptithcm.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.ptithcm.mobile.model.Account;
import com.ptithcm.mobile.model.BuoiBieuDien;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    private RequestQueue requestQueue;
    EditText txtUser, txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        setControl();
        setEvent();
    }

    private void setEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUser.getText().toString();
                String password = txtPass.getText().toString();
                Account account = new Account(username, password);
                System.out.println("user account: "+ account.toString());
                try {
                    isAccountValid(account);
                } catch (Exception e){
                   Toast.makeText(LoginActivity.this, "Account is invalid in 59", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void setControl() {
        btnLogin = findViewById(R.id.btnLogin);
        txtUser = findViewById(R.id.username_input);
        txtPass = findViewById(R.id.pass);
    }

    private void isAccountValid(Account userInput) {
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        String user = userInput.getUser();
        String pass = userInput.getPass();
        String url = "https://api-qlan-nhom-2.herokuapp.com/api/check-login?user="+user+"&password="+pass;
        //String url = "http://192.168.1.56:8080/api/check-login?user="+user+"&password="+pass;
        System.out.println(url);
        // prepare the Request
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("user", user);
            jsonBody.put("password", pass);

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    Toast.makeText(LoginActivity.this, "Login success!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    Toast.makeText(LoginActivity.this, "Account is invalid in 102", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LoginActivity.this, "Account exception here 114", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(LoginActivity.this, "Account is invalid in 136 line", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}