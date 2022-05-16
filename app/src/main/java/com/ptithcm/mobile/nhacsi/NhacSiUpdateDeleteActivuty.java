package com.ptithcm.mobile.nhacsi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.ptithcm.mobile.service.DownloadImageTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NhacSiUpdateDeleteActivuty extends AppCompatActivity {
    private RequestQueue requestQueue;
    EditText editTextMaNS, editTextTenNS, editTextGioiThieu,editTextHinh;
    Button btnSua, btnXoa, btnTacPham;
    // Thêm vào ImageView bên dưới
    ImageView nhacSiAva;

    NhacSiEntity nhacSi = new NhacSiEntity();
    ArrayList<NhacSiEntity> dsNhacSi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_nhacsi);
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
                startActivity(new Intent(NhacSiUpdateDeleteActivuty.this, AdminMainActivity.class));
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idLogout:
                startActivity(new Intent(NhacSiUpdateDeleteActivuty.this, MainActivity.class));
                Toast.makeText(this, "LOGOUT", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl(){
        editTextMaNS = findViewById(R.id.editTextMaNS);
        editTextTenNS = findViewById(R.id.editTextTenNS);
        editTextGioiThieu = findViewById(R.id.editTextGioiThieu);
        editTextHinh = findViewById(R.id.editTextHinh);
        nhacSiAva = findViewById(R.id.avaNS); // bổ sung

        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);
        btnTacPham = findViewById(R.id.btnTacPham);
    }

    private void setEvent(){
        fillDuLieu();
        btnTacPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bổ sung
                Intent intent = new Intent(NhacSiUpdateDeleteActivuty.this, NhacSiInfo.class);
                intent.putExtra("nhacSi", nhacSi);
                startActivity(intent);
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNhacSi();
                Toast.makeText(NhacSiUpdateDeleteActivuty.this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
                goToSuccessActivity();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xacNhanXoa();
            }
        });
    }

    private void updateNhacSi() {
        NhacSiEntity nhacSi = getNhacSi();

        String url = "https://api-qlan-nhom-2.herokuapp.com/musicians/"+nhacSi.getMaNS();
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("maNS", nhacSi.getMaNS());
            jsonObject.put("tenNS", nhacSi.getTenNS());
            jsonObject.put("gioiThieu", nhacSi.getGioiThieu());
            jsonObject.put("hinh", nhacSi.getHinh());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
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
        nhacSi.setMaNS(Integer.valueOf(editTextMaNS.getText().toString()));
        nhacSi.setTenNS(editTextTenNS.getText().toString());
        nhacSi.setGioiThieu(editTextGioiThieu.getText().toString());
        nhacSi.setHinh(editTextHinh.getText().toString());
        return nhacSi;
    }

    private void troVe() {
        Intent intent = new Intent(NhacSiUpdateDeleteActivuty.this, MainNhacSiActivity.class);
        startActivity(intent);
    }

    public void fillDuLieu(){
        getDanhSachNS();
        nhacSi = layDulieu();
        editTextMaNS.setText((nhacSi.getMaNS().toString()));
        editTextMaNS.setEnabled(false);
        editTextTenNS.setText(nhacSi.getTenNS());
        editTextGioiThieu.setText(nhacSi.getGioiThieu());
        editTextHinh.setText(nhacSi.getHinh());
        // Bổ sung
        String url = nhacSi.getHinh();
        new DownloadImageTask((ImageView) findViewById(R.id.avaNS)).execute(url);
//        editTextHinh.setText(nhacSi.getHinh());
    }

    private NhacSiEntity layDulieu() {
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            return  (NhacSiEntity) b.get("nhacSi");
        }
        else{
            return  null;
        }
    }
    private void goToSuccessActivity() {
        Intent intent = new Intent(NhacSiUpdateDeleteActivuty.this, NhacSiSuccessActivity.class);
        startActivity(intent);
    }
    private void xacNhanXoa(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Thông báo");
        alertDialog.setIcon(R.drawable.ic_warning);
        alertDialog.setMessage("Bạn có chắc chắn muốn xóa?");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    NhacSiEntity nhacSi = getNhacSi();
                    deleteNhacSi(nhacSi.getMaNS());
                    Toast.makeText(NhacSiUpdateDeleteActivuty.this, "Deleted!", Toast.LENGTH_SHORT).show();

                    goToSuccessActivity();
                }catch (Exception e){
                    Toast.makeText(NhacSiUpdateDeleteActivuty.this, "Delete Fail!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        alertDialog.setNegativeButton("Trở lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(NhacSiUpdateDeleteActivuty.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
    }

    private void deleteNhacSi(Integer maNS) {
        String url = "https://api-qlan-nhom-2.herokuapp.com/musicians/"+maNS;
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        JSONObject jsonObject = new JSONObject();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                jsonObject,
                response -> {

                }, e -> {
            e.printStackTrace();
        }
        );
        requestQueue.add(jsonObjectRequest);
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
