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

import com.ptithcm.mobile.model.CaSi;
import com.ptithcm.mobile.service.DownloadImageTask;

import java.util.ArrayList;

public class CustomListViewCaSiAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<CaSi> listCS;
    public CustomListViewCaSiAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CaSi> listCS) {
        super(context, resource, listCS);
        this.context = context;
        this.resource = resource;
        this.listCS = listCS;
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

        TextView tvMaCS = convertView.findViewById(R.id.tvMaCS);
        TextView tvTenCS = convertView.findViewById(R.id.tvTenCS);
        ImageView img = convertView.findViewById(R.id.idImage);



        CaSi caSi = listCS.get(position);

        tvMaCS.setText(caSi.getMaCS());
        tvTenCS.setText(caSi.getTenCS());

        String url = caSi.getUrlImage();
        new DownloadImageTask(img)
                .execute(url);

        return convertView;
    }
}
