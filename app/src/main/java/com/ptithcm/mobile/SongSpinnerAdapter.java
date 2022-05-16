package com.ptithcm.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SongSpinnerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> listSong;

    public SongSpinnerAdapter(Context context, ArrayList<String> listSong) {
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
                .inflate(R.layout.items_spinner_song, viewGroup, false);
        TextView baiHat = rootView.findViewById(R.id.tvSong);
        ImageView img = rootView.findViewById(R.id.imgSpinner);
        baiHat.setText(listSong.get(i));
        img.setImageResource(R.drawable.ic_music);
        return rootView;
    }
}
