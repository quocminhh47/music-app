package com.ptithcm.mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ptithcm.mobile.R;
import com.ptithcm.mobile.model.BuoiBieuDien;
import com.ptithcm.mobile.model.Comment;

import java.util.ArrayList;

public class CustomCommentAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<Comment> listCMT;
    public CustomCommentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Comment> listCMT) {
        super(context, resource, listCMT);
        this.context = context;
        this.resource = resource;
        this.listCMT = listCMT;
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

        TextView tvTenCmt = convertView.findViewById(R.id.tvTenNguoiCMT);
        TextView tvMessage = convertView.findViewById(R.id.tvMessage);
        Comment comment = listCMT.get(position);
        tvTenCmt.setText(comment.getName());
        tvMessage.setText(comment.getMessage());

        return convertView;
    }
}
