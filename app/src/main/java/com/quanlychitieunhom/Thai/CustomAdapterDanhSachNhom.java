package com.quanlychitieunhom.Thai;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.quanlychitieunhom.R;

import java.util.ArrayList;

public class CustomAdapterDanhSachNhom extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<Nhom> nhoms;
    int layoutItem;
    public CustomAdapterDanhSachNhom(Activity context, int layoutItem, ArrayList<Nhom> thanhVienArrayList) {
        this.layoutInflater = context.getLayoutInflater();
        this.nhoms = thanhVienArrayList;
        this.layoutItem = layoutItem;
    }
    @Override
    public int getCount() {
        return this.nhoms.size();
    }
    @Override
    public Object getItem(int position) {
        return this.nhoms.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup
            parent) {
        Nhom thanhVien = nhoms.get(position);
        View rowView = layoutInflater.inflate(layoutItem,null,true);
        ImageView avatar = (ImageView) rowView.findViewById(R.id.imgAvatar);
        avatar.setImageResource(thanhVien.getHinhNhom());
        TextView tvName = (TextView) rowView.findViewById(R.id.tvTenNhom);
        tvName.setText(thanhVien.getTenNhom());

        return rowView;
    }
}
