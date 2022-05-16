package com.ptithcm.mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


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
import com.ptithcm.mobile.model.BuoiBieuDien;
import com.ptithcm.mobile.service.DownloadImageTask;
import com.ptithcm.mobile.service.SendMailActivity;
import com.sun.mail.iap.ByteArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class SingleBuoiBieuDien extends AppCompatActivity {
    TextView tvMaBuoiDien, tvMaBaiHat, tvMaCaSi, tvDiaDiem, tvNgayBD;
    ImageView imgView;
    Button btnPdf, btnMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_buoi_dien2);

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
                startActivity(new Intent(SingleBuoiBieuDien.this, MainActivity.class));

                Toast.makeText(this, "HOME", Toast.LENGTH_SHORT).show();

                break;
            case R.id.idLogin:
                startActivity(new Intent(SingleBuoiBieuDien.this, LoginActivity.class));
                Toast.makeText(this, "LOGIN", Toast.LENGTH_SHORT).show();
                break;
            case R.id.idMail:

                startActivity(new Intent(SingleBuoiBieuDien.this, SendMailActivity.class));
                Toast.makeText(this, "MAIL", Toast.LENGTH_SHORT).show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void setEvent() {
        //export pdf
        btnPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maBD = tvMaBuoiDien.getText().toString();
               // String maBH = tvMaBaiHat.getText().toString();
                String maBH = "BH01";
                //String maCS = tvMaCaSi.getText().toString();
                String maCS = "cs02";
                String diaDiem = tvDiaDiem.getText().toString();
                String ngayBD = tvNgayBD.getText().toString();
                try {
                    createPDF(maBD,maCS, maBH, diaDiem, ngayBD);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        // check location
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SingleBuoiBieuDien.this, MapActivity.class));
            }
        });
    }

    private void setControl() {
        tvMaBuoiDien = findViewById(R.id.tvMaBuoiDien);
        tvMaBaiHat = findViewById(R.id.tvMaBaiHat);
        tvMaCaSi = findViewById(R.id.tvMaCaSi);
        tvDiaDiem = findViewById(R.id.tvDiaDiem);
        tvNgayBD = findViewById(R.id.tvNgayBieuDien);
        imgView = findViewById(R.id.imgView);
        btnPdf = findViewById(R.id.btnPDF);
        btnMap = findViewById(R.id.btnMap);
        //
        getData();

    }

    private void createPDF(String maBD, String maCS, String maBH, String diaDiem, String ngayBD) throws FileNotFoundException {

        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

        File file  = new File(pdfPath,"myPDF.pdf");
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

        ImageData imageData = ImageDataFactory .create(bitmapData);
        Image image = new Image(imageData);

        Paragraph title = new Paragraph("WELCOME TO THE SHOW")
                .setBold().setFontSize(24)
                .setTextAlignment(TextAlignment.CENTER);
        Paragraph message = new Paragraph("SHOW INFORMATION")
                .setBold().setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER);
        float[] width = {200f,200f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);

        table.addCell(new Cell().add(new Paragraph("Ma buoi dien")));
        table.addCell(new Cell().add(new Paragraph(maBD)));

        table.addCell(new Cell().add(new Paragraph("Ma ca si")));
        table.addCell(new Cell().add(new Paragraph(maCS)));

        table.addCell(new Cell().add(new Paragraph("Ma bai hat")));
        table.addCell(new Cell().add(new Paragraph(maBH)));

        table.addCell(new Cell().add(new Paragraph("Ngay bieu dien")));
        table.addCell(new Cell().add(new Paragraph(ngayBD)));

        table.addCell(new Cell().add(new Paragraph("Dia diem")));
        table.addCell(new Cell().add(new Paragraph(diaDiem)));

        BarcodeQRCode qrCode = new BarcodeQRCode(maBD+"\n"+ngayBD+"\n"+diaDiem);
        PdfFormXObject qrCodeObject = qrCode.createFormXObject(ColorConstants.BLACK, pdfDocument);
        Image qrCodeImage = new Image(qrCodeObject).setWidth(80).setHorizontalAlignment(HorizontalAlignment.CENTER);

        document.add(image);
        document.add(title);
        document.add(message);
        document.add(table);
        document.add(qrCodeImage);
        document.close();
        Toast.makeText(this, "PDF file created", Toast.LENGTH_SHORT).show();



    }

    private void getData() {
        BuoiBieuDien bd = getBuoiDien();

        System.out.println(bd.toString());

        tvMaBuoiDien.setText(bd.getMABD());
        tvMaCaSi.setText(bd.getMACS());
        tvMaBaiHat.setText(bd.getMABH());
        tvNgayBD.setText(bd.getNGAYBD());
        tvDiaDiem.setText(bd.getDIADIEM());

        String url = bd.getUrl();
        new DownloadImageTask((ImageView) findViewById(R.id.imgView))
                .execute(url);
    }

    private BuoiBieuDien getBuoiDien() {
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null) {
            return  (BuoiBieuDien) b.get("buoiDien");
        }
        else{
            return  null;
        }
    }
}