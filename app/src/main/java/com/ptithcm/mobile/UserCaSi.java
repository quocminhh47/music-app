package com.ptithcm.mobile;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
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
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.ptithcm.mobile.model.CaSi;
import com.ptithcm.mobile.service.SendMailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class UserCaSi extends AppCompatActivity{
    private RequestQueue requestQueue;
    ArrayList<CaSi> listCS = new ArrayList<>();
    ListView lvCaSi;
    Button btnXuat, btnThongKe;
    ImageView imgThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ca_si);

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
                startActivity(new Intent(UserCaSi.this, MainActivity.class));

                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();

                break;
            case R.id.idLogin:
                startActivity(new Intent(UserCaSi.this, LoginActivity.class));
                Toast.makeText(this, "LOGIN", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idMail:

                startActivity(new Intent(UserCaSi.this, SendMailActivity.class));
                Toast.makeText(this, "MAIL", Toast.LENGTH_SHORT).show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void setEvent(){
        lvCaSi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(UserCaSi.this, ChiTietCaSi.class);
                CaSi caSi = listCS.get(i);
                intent.putExtra("caSi", caSi);
                startActivity(intent);
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCaSi.this, CaSiChart.class);
                startActivity(intent);
            }
        });

        btnXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createPDF();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void setControl(){

        lvCaSi = findViewById(R.id.lvCaSi);
        btnThongKe = findViewById(R.id.btnThongKe);
        btnXuat = findViewById(R.id.btnXuatPDF);
        imgThumbnail = findViewById(R.id.idImage);

        getTTCaSi();

    }

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

                CustomListViewCaSiAdapter caSiAdapter = new CustomListViewCaSiAdapter(UserCaSi.this, R.layout.listview_item_ca_si, listCS);
                lvCaSi.setAdapter(caSiAdapter);
                caSiAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void createPDF() throws FileNotFoundException {

        Toast.makeText(this, "PDF file created", Toast.LENGTH_SHORT).show();

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

                    String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

                    File file  = new File(pdfPath,"myCSPDF.pdf");
                    OutputStream outputStream = new FileOutputStream(file);

                    PdfWriter writer = new PdfWriter(file);
                    PdfDocument pdfDocument = new PdfDocument(writer);
                    Document document = new Document(pdfDocument);

                    pdfDocument.setDefaultPageSize(PageSize.A6);
                    document.setMargins(0,0,0,0);

                    Drawable drawable = getDrawable(R.drawable.banner);
                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] bitmapData = stream.toByteArray();

                    ImageData imageData = ImageDataFactory.create(bitmapData);
                    Image image = new Image(imageData);

                    Paragraph title = new Paragraph("WELCOME TO THE SHOW")
                            .setBold().setFontSize(24)
                            .setTextAlignment(TextAlignment.CENTER);
                    Paragraph message = new Paragraph("SINGER INFORMATION")
                            .setBold().setFontSize(18)
                            .setTextAlignment(TextAlignment.CENTER);
                    float[] width = {200f,200f};
                    Table table = new Table(width);
                    table.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    for(int i = 0; i<listCS.size(); i++){
                        table.addCell(new Cell().add(new Paragraph(listCS.get(i).getMaCS())));
                        table.addCell(new Cell().add(new Paragraph(listCS.get(i).getTenCS())));
                    }

////                    table.addCell(new Cell().add(new Paragraph("Ma ca si")));
////                    table.addCell(new Cell().add(new Paragraph(maCS)));
////
////                    table.addCell(new Cell().add(new Paragraph("Ma ca si")));
////                    table.addCell(new Cell().add(new Paragraph(maCS)));
////
////                    table.addCell(new Cell().add(new Paragraph("Ma bai hat")));
////                    table.addCell(new Cell().add(new Paragraph(maBH)));
////
////                    table.addCell(new Cell().add(new Paragraph("Ngay bieu dien")));
////                    table.addCell(new Cell().add(new Paragraph(ngayBD)));
////
////                    table.addCell(new Cell().add(new Paragraph("Dia diem")));
////                    table.addCell(new Cell().add(new Paragraph(diaDiem)));
////
////                    BarcodeQRCode qrCode = new BarcodeQRCode(maBD+"\n"+ngayBD+"\n"+diaDiem);
//                    PdfFormXObject qrCodeObject = qrCode.createFormXObject(ColorConstants.BLACK, pdfDocument);
//                    Image qrCodeImage = new Image(qrCodeObject).setWidth(80).setHorizontalAlignment(HorizontalAlignment.CENTER);

                    document.add(image);
                    document.add(title);
                    document.add(message);
                    document.add(table);
//                    document.add(qrCodeImage);
                    document.close();

                } catch (JSONException | FileNotFoundException e) {
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