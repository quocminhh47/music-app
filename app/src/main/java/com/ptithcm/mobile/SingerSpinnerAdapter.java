package com.ptithcm.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SingerSpinnerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> listSinger
            ;

    public SingerSpinnerAdapter(Context context, ArrayList<String> listSinger) {
        this.context = context;
        this.listSinger = listSinger;
    }

    @Override
    public int getCount() {
        return listSinger != null ? listSinger.size() : 0;
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
                .inflate(R.layout.items_spinner_singer, viewGroup, false);
        TextView diaDiem = rootView.findViewById(R.id.tvCaSi);
        ImageView img = rootView.findViewById(R.id.imgSpinner);
        diaDiem.setText(listSinger.get(i));
        img.setImageResource(R.drawable.ic_singer);
        return rootView;
    }
}
