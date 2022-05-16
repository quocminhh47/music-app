package com.ptithcm.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ptithcm.mobile.model.BuoiBieuDien;
import com.ptithcm.mobile.model.Comment;
import com.ptithcm.mobile.service.SendMailActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChiTietBuoiDienActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    ArrayList<BuoiBieuDien> listBD = new ArrayList<>();
    ArrayList<Comment> listComment = new ArrayList<>();
    ListView lVBuoiBD , lvCMT;
    Button btnThongKe, btnXemComment;
    ImageView imgThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_buoi_dien);
        
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
                startActivity(new Intent(ChiTietBuoiDienActivity.this, MainActivity.class));

                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();

                break;
            case R.id.idLogin:
                startActivity(new Intent(ChiTietBuoiDienActivity.this, LoginActivity.class));
                Toast.makeText(this, "LOGIN", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idMail:

                startActivity(new Intent(ChiTietBuoiDienActivity.this, SendMailActivity.class));
                Toast.makeText(this, "MAIL", Toast.LENGTH_SHORT).show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void setEvent() {
        //get buoi dien from list view
        lVBuoiBD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ChiTietBuoiDienActivity.this, SingleBuoiBieuDien.class);
                BuoiBieuDien buoiDien = listBD.get(i);
                intent.putExtra("buoiDien", buoiDien);
                startActivity(intent);
            }
        });

        //comment
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(ChiTietBuoiDienActivity.this, ThongKeBuoiDienActivity.class));
            }
        });

        //xem cmt
        btnXemComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChiTietBuoiDienActivity.this, XemCommentActivity.class));
            }
        });

    }

    private void setControl() {
        btnThongKe = findViewById(R.id.btnThongKe);
        getTTBuoiDien();
        //getComments();
        lVBuoiBD = findViewById(R.id.lvBuoiDien);
        imgThumbnail = findViewById(R.id.idImage);
        btnXemComment = findViewById(R.id.btnXemComment);
    }

 /*   private void getComments() {
        listComment.clear();
        //link api
        String url = "http://192.168.1.56:8080/api/get-all-comments";
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray jsonArray = response;
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject j = jsonArray.getJSONObject(i);
                        Comment comment = new Comment();
                        comment.setName(j.getString("name"));
                        comment.setMessage(j.getString("message"));
                        listComment.add(comment);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                CustomCommentAdapter commentAdapter = new CustomCommentAdapter(ChiTietBuoiDienActivity.this, R.layout.listview_item_comments, listComment);
                System.out.println("size: " + listComment.size());

                lvCMT.setAdapter(commentAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest);


    }*/

    private void getTTBuoiDien() {
        listBD.clear();
        String url = "https://api-qlan-nhom-2.herokuapp.com/api/all/thong-tin-buoi-dien";
//        String url = "http://192.168.1.56:8080/api/all/thong-tin-buoi-dien";
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

                CustomListViewBuoiBDAdapter buoiBDAdapter = new CustomListViewBuoiBDAdapter(ChiTietBuoiDienActivity.this, R.layout.listview_items_buoi_dien, listBD);
                lVBuoiBD.setAdapter(buoiBDAdapter);
                buoiBDAdapter.notifyDataSetChanged();
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