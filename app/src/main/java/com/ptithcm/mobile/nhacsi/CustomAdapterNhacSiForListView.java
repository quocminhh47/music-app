package com.ptithcm.mobile.nhacsi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ptithcm.mobile.R;

import java.util.ArrayList;
import java.util.Locale;

public class CustomAdapterNhacSiForListView extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<NhacSiEntity> dsNhacSi, tempList;

    public CustomAdapterNhacSiForListView(@NonNull Context context, int resource, @NonNull ArrayList<NhacSiEntity> dsNhacSi) {
        super(context, resource, dsNhacSi);
        this.context = context;
        this.resource = resource;
        this.dsNhacSi = dsNhacSi;
        tempList = new ArrayList<>();
        tempList.addAll(this.dsNhacSi);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,null);
        TextView tvMaNhacSi= convertView.findViewById(R.id.tvMaNhacSi);
        TextView tvTenNhacSi= convertView.findViewById(R.id.tvTenNhacSi);
        TextView tvGioiThieu= convertView.findViewById(R.id.tvGioiThieu);
        //TextView tvHinh = convertView.findViewById(R.id.txtHinh);

        NhacSiEntity nhacSi = dsNhacSi.get(position);
        tvMaNhacSi.setText(nhacSi.getMaNS().toString());
        tvTenNhacSi.setText(nhacSi.getTenNS());
        tvGioiThieu.setText(nhacSi.getGioiThieu());
        //tvHinh.setText(nhacSi.getHinh());

        return convertView;
    }

    public void searchNhacSi(String text) {
        dsNhacSi.clear();
        text = text.toLowerCase(Locale.getDefault());
        if (text.length() == 0) {
            dsNhacSi.addAll(tempList);
        } else {
            for (NhacSiEntity nhacSi : tempList) {
                if (nhacSi.getTenNS().toLowerCase().contains(text)) {
                    dsNhacSi.add(nhacSi);
                }
            }
        }
        notifyDataSetChanged();
    }
}
