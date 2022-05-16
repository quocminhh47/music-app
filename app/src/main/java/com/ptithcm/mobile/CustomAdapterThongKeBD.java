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
import com.ptithcm.mobile.model.ThongKeBuoiDien;
import com.ptithcm.mobile.service.DownloadImageTask;

import java.util.ArrayList;


public class CustomAdapterThongKeBD extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<ThongKeBuoiDien> list;

    public CustomAdapterThongKeBD(@NonNull Context context, int resource, @NonNull ArrayList<ThongKeBuoiDien> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
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

        TextView tvPlace = convertView.findViewById(R.id.tvPlace);
        TextView tvSL = convertView.findViewById(R.id.tvSL);

        ThongKeBuoiDien buoiDien = list.get(position);

        tvPlace.setText(buoiDien.getDiaDiem());
        String count = String.valueOf(buoiDien.getCount());
        tvSL.setText(count);
        return convertView;
    }
}
