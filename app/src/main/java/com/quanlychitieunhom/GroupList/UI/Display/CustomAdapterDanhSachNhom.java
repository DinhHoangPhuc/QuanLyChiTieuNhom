package com.quanlychitieunhom.GroupList.UI.Display;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.quanlychitieunhom.GroupList.UI.State.NhomModel;
import com.quanlychitieunhom.R;

import java.util.ArrayList;

public class CustomAdapterDanhSachNhom extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<NhomModel> nhoms;
    int layoutItem;
    public CustomAdapterDanhSachNhom(Activity context, int layoutItem, ArrayList<NhomModel> thanhVienArrayList) {
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
        NhomModel nhom = nhoms.get(position);
        View rowView = layoutInflater.inflate(layoutItem,null,true);
        ImageView avatar = (ImageView) rowView.findViewById(R.id.imgAvtNhom);
//        Glide.with(layoutInflater.getContext()).load(thanhVien.getHinhNhom()).into(avatar);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvTenNhom);
        tvName.setText(nhom.getTenNhom());
        return rowView;
    }
}
