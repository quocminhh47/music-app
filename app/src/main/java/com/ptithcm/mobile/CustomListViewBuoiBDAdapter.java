package com.ptithcm.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.ptithcm.mobile.model.BuoiBieuDien;
import com.ptithcm.mobile.service.DownloadImageTask;

import java.util.ArrayList;

public class CustomListViewBuoiBDAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<BuoiBieuDien> listBD;
    public CustomListViewBuoiBDAdapter(@NonNull Context context, int resource, @NonNull ArrayList<BuoiBieuDien> listBD) {
        super(context, resource, listBD);
        this.context = context;
        this.resource = resource;
        this.listBD = listBD;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView tvMaBD = convertView.findViewById(R.id.tvMaBD);
        TextView tvNgayBD = convertView.findViewById(R.id.tvNgayBD);
        TextView tvDiaDiem = convertView.findViewById(R.id.tvDiaDiem);
        ImageView img = convertView.findViewById(R.id.idImage);


        BuoiBieuDien buoiBieuDien = listBD.get(position);

        tvMaBD.setText(buoiBieuDien.getMABD());
        tvDiaDiem.setText(buoiBieuDien.getDIADIEM());
        tvNgayBD.setText(buoiBieuDien.getNGAYBD());
        String url = buoiBieuDien.getUrl();
        new DownloadImageTask(img)
                .execute(url);

        return convertView;
    }
}
