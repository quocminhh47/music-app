package com.ptithcm.mobile.baihat;

import android.content.DialogInterface;
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
import com.ptithcm.mobile.nhacsi.NhacSiEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BaiHatEditDeleteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bai_hat_edit_delete_activity);
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
                startActivity(new Intent(BaiHatEditDeleteActivity.this, AdminMainActivity.class));
                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idLogout:
                startActivity(new Intent(BaiHatEditDeleteActivity.this, MainActivity.class));
                Toast.makeText(this, "LOGOUT", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private RequestQueue requestQueue;
    BaiHatCustomSpinner baiHatCustomSpinner;
    Spinner spinnerDanhSachNhacSi;
    EditText editTextMaBH, editTextTenBH, editTextNamST, editTextURL;

    Button btnSua, btnXoa, btnTroVe;
    ArrayList<NhacSiEntity> danhSachNhacSi = new ArrayList<>();
    Integer maNS;

    BaiHatEntity baiHatEntity;

    private void setControl() {
        editTextMaBH = findViewById(R.id.editTextMaBH);
        editTextTenBH = findViewById(R.id.editTextTenBH);
//        editTextNamST = findViewById(R.id.editTextNamST);
        editTextURL = findViewById(R.id.editTextURL);
        spinnerDanhSachNhacSi = findViewById(R.id.spinnerDanhSachNhacSi);

        btnTroVe = findViewById(R.id.btnTroVe);
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);
    }
    private void goToSuccessActivity() {
        Intent intent = new Intent(BaiHatEditDeleteActivity.this, BaiHatSuccessActivity.class);
        startActivity(intent);
    }

    private void setEvent() {
        khoiTao();
        spinnerDanhSachNhacSi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maNS = danhSachNhacSi.get(i).getMaNS();
                Toast.makeText(BaiHatEditDeleteActivity.this, maNS.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                troVe();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBaiHat();
                Toast.makeText(BaiHatEditDeleteActivity.this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
                goToSuccessActivity();
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                deleteBaiHat(baiHatEntity.getMaBH());
                xacNhanXoa();
            }
        });

        spinnerDanhSachNhacSi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maNS = danhSachNhacSi.get(i).getMaNS();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void khoiTao() {
        fillDuLieu();
    }

    private void updateBaiHat() {
        BaiHatEntity baiHatEntity = getBaiHat();
        String url = "https://api-qlan-nhom-2.herokuapp.com/musics/" + baiHatEntity.getMaBH();
        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();
        JSONObject jsonObject = new JSONObject();

        JSONObject nhacSiJSONObject = new JSONObject();
        try {
            nhacSiJSONObject.put("maNS", baiHatEntity.getNhacSi().getMaNS());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject.put("maBH", baiHatEntity.getMaBH());
            jsonObject.put("tenBH", baiHatEntity.getTenBH());
            jsonObject.put("url", baiHatEntity.getUrl());
            jsonObject.put("nhacSi", nhacSiJSONObject);
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
    private void xacNhanXoa(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Thông báo");
        alertDialog.setIcon(R.drawable.ic_warning);
        alertDialog.setMessage("Bạn có chắc chắn muốn xóa?");
        alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    deleteBaiHat(baiHatEntity.getMaBH());
                    Toast.makeText(BaiHatEditDeleteActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                    goToSuccessActivity();
                }catch (Exception e){
                    Toast.makeText(BaiHatEditDeleteActivity.this, "Delete Fail!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.setNegativeButton("Trở lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(BaiHatEditDeleteActivity.this, "Cancel!", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
    }
    private void deleteBaiHat(Integer id) {
        String url = "https://api-qlan-nhom-2.herokuapp.com/musics/" + id;
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
                baiHatCustomSpinner = new BaiHatCustomSpinner(BaiHatEditDeleteActivity.this, danhSachNhacSi);
                spinnerDanhSachNhacSi.setAdapter(baiHatCustomSpinner);
                for (int j = 0; j < danhSachNhacSi.size(); j++) {
                    if (baiHatEntity.getNhacSi().getMaNS().equals(danhSachNhacSi.get(j).getMaNS())) {
                        spinnerDanhSachNhacSi.setSelection(j);
                    }
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

    public void fillDuLieu() {
        getDanhSachNhacSi();
        baiHatEntity = layDuLieu();
        editTextMaBH.setText(baiHatEntity.getMaBH().toString());
        editTextMaBH.setEnabled(false);
        editTextTenBH.setText(baiHatEntity.getTenBH());
        editTextURL.setText(baiHatEntity.getUrl());

    }

    public BaiHatEntity layDuLieu() {
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            return (BaiHatEntity) b.get("baiHat");
        } else {
            return null;
        }
    }

    private BaiHatEntity getBaiHat() {
        BaiHatEntity BaiHat = new BaiHatEntity();
        BaiHat.setMaBH(Integer.valueOf(editTextMaBH.getText().toString()));
        BaiHat.setTenBH(editTextTenBH.getText().toString());
        BaiHat.setUrl(editTextURL.getText().toString());
//        BaiHat.setMaNS(Integer.valueOf(strMaNS));
        NhacSiEntity nhacSiEntity = new NhacSiEntity();
        nhacSiEntity.setMaNS(maNS);
        BaiHat.setNhacSi(nhacSiEntity);
        return BaiHat;
    }

    private void troVe() {
        Intent intent = new Intent(BaiHatEditDeleteActivity.this, BaiHatSuccessActivity.class);
        startActivity(intent);
    }
}