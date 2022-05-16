package com.ptithcm.mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;



import com.ptithcm.mobile.service.SendMailActivity;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CaSiChart extends AppCompatActivity {

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ca_si_chart);

        pieChart = findViewById(R.id.piechart);
        getDanhSachCaSi();
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
                startActivity(new Intent(CaSiChart.this, MainActivity.class));

                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();

                break;
            case R.id.idLogin:
                startActivity(new Intent(CaSiChart.this, LoginActivity.class));
                Toast.makeText(this, "LOGIN", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idMail:

                startActivity(new Intent(CaSiChart.this, SendMailActivity.class));
                Toast.makeText(this, "MAIL", Toast.LENGTH_SHORT).show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }


    private RequestQueue requestQueue;
    private List<TK_CS_BBD> danhSachThongKe = new ArrayList<>();

    private void getDanhSachCaSi() {
        String url = "https://api-qlan-nhom-2.herokuapp.com/api/casi/tk-cs-bbd";
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        TK_CS_BBD tk_cs_bbd = new TK_CS_BBD();
                        tk_cs_bbd.setMaCS(j.getString("maCS"));
                        tk_cs_bbd.setTenCS(j.getString("tenCS"));
                        tk_cs_bbd.setBBD(j.getString("soBBD"));
                        danhSachThongKe.add(tk_cs_bbd);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setData();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void setData() {


        LinearLayout linearLayout = findViewById(R.id.lnTextView);
        Random rnd = new Random();

        for (int i = 0; i < danhSachThongKe.size(); i++) {
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            TextView t = new TextView(this);
            t.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            t.setGravity(Gravity.CENTER);
            t.setText(danhSachThongKe.get(i).getMaCS()+" - "+danhSachThongKe.get(i).getTenCS() + " - " + danhSachThongKe.get(i).getBBD() + " Bài Hát");
            t.setTextColor(Color.WHITE);
            t.setBackgroundColor(color);
            t.setHeight(50);
            if (linearLayout != null) {
                linearLayout.addView(t);
            }

            pieChart.addPieSlice(
                    new PieModel(
                            danhSachThongKe.get(i).getTenCS(),
                            Integer.valueOf(danhSachThongKe.get(i).getBBD()),
                            color));
        }
        pieChart.startAnimation();


    }
}