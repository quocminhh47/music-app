package com.ptithcm.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.ptithcm.mobile.model.Comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class XemCommentActivity extends AppCompatActivity {
    private RequestQueue requestQueue;

    ArrayList<Comment> listComment = new ArrayList<>();
    ListView lvCMT;
    Button btnCMT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_comment);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnCMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(XemCommentActivity.this, CommentActivity.class));
            }
        });
    }

    private void setControl() {
        lvCMT = findViewById(R.id.lvComments);
        btnCMT = findViewById(R.id.btnComment);
        getComments();
    }
    private void getComments() {
        listComment.clear();
        //link api
        String url = "https://api-qlan-nhom-2.herokuapp.com/api/get-all-comments";
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

                CustomCommentAdapter commentAdapter = new CustomCommentAdapter(XemCommentActivity.this, R.layout.listview_item_comments, listComment);
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


    }

}