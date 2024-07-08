package com.quanlychitieunhom.Home;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quanlychitieunhom.R;

import java.util.ArrayList;

public class ThuListViewAdapter extends BaseAdapter {
    private Activity activity;
    private int layoutItem;
    private ArrayList<Thu> thuArrayList;

    public ThuListViewAdapter(Activity activity, int layoutItem, ArrayList<Thu> thuArrayList) {
        this.activity = activity;
        this.layoutItem = layoutItem;
        this.thuArrayList = thuArrayList;
    }

    @Override
    public int getCount() {
        return thuArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return thuArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Thu thu = thuArrayList.get(position);

        View rowView = activity.getLayoutInflater().inflate(layoutItem, null, true);

        TextView tvNgayThu = rowView.findViewById(R.id.tvNgayThu);
        TextView tvSoTienThu = rowView.findViewById(R.id.tvSoTien);
        TextView tvMoTa = rowView.findViewById(R.id.tvMoTa);

        tvNgayThu.setText(thu.getNgayThu());
        tvSoTienThu.setText(String.valueOf(thu.getSoTienThu()));
        tvMoTa.setText(thu.getMoTa());

        return rowView;
    }
}
