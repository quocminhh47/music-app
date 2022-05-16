package com.ptithcm.mobile.baihat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptithcm.mobile.R;
import com.ptithcm.mobile.nhacsi.NhacSiEntity;

import java.util.ArrayList;

public class BaiHatCustomSpinner extends BaseAdapter {
    private Context context;
    private ArrayList<NhacSiEntity> listSong;

    public BaiHatCustomSpinner(Context context, ArrayList<NhacSiEntity> listSong) {
        this.context = context;
        this.listSong = listSong;
    }

    @Override
    public int getCount() {
        return listSong != null ? listSong.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.bai_hat_custom_spinner_item, viewGroup, false);
        TextView baiHat = rootView.findViewById(R.id.tvSong);
        baiHat.setText(listSong.get(i).getMaNS() + "-" +listSong.get(i).getTenNS());
        ImageView img = rootView.findViewById(R.id.imgSpinner);
        img.setImageResource(R.drawable.song_icon);

        return rootView;
    }
}
