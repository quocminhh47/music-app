package com.ptithcm.mobile.baihat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.ptithcm.mobile.R;

import java.util.ArrayList;
import java.util.Locale;

public class BaiHatCustomAdapterForListView extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<BaiHatEntity> danhSachBaiHat, tempList;

    public BaiHatCustomAdapterForListView(@NonNull Context context, int resource, @NonNull ArrayList<BaiHatEntity> danhSachBaiHat) {
        super(context, resource, danhSachBaiHat);
        this.context = context;
        this.resource = resource;
        this.danhSachBaiHat = danhSachBaiHat;
        //For Search View
        tempList = new ArrayList<>();
        tempList.addAll(this.danhSachBaiHat);
    }

    public void filter(String text) {
        danhSachBaiHat.clear();
        text = text.toLowerCase(Locale.getDefault());
        if (text.length() == 0) {
            danhSachBaiHat.addAll(tempList);
        } else {
            for (BaiHatEntity baiHat : tempList) {
                if (baiHat.getNhacSi().getTenNS().toLowerCase().contains(text)) {
                    danhSachBaiHat.add(baiHat);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int i) {
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

        TextView textViewMaBH = convertView.findViewById(R.id.textViewMaBH);
        TextView textViewTenBH = convertView.findViewById(R.id.textViewTenBH);
        TextView textViewMaNS = convertView.findViewById(R.id.textViewMaNS);
//        TextView textViewNamST = convertView.findViewById(R.id.textViewNamST);

        BaiHatEntity BaiHat = danhSachBaiHat.get(position);

        textViewMaBH.setText(BaiHat.getMaBH().toString());
        textViewTenBH.setText(BaiHat.getTenBH());
        textViewMaNS.setText(BaiHat.getNhacSi().getTenNS());
//        textViewNamST.setText(BaiHat.getNamST());
        return convertView;
    }

    private RequestQueue requestQueue;


}
