package com.quanlychitieunhom.Fund.UI.Display;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quanlychitieunhom.Fund.UI.State.ThuModel;
import com.quanlychitieunhom.Home.Thu;
import com.quanlychitieunhom.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThuListViewAdapter extends BaseAdapter {
    private Activity activity;
    private int layoutItem;
    private ArrayList<ThuModel> thuArrayList;

    public ThuListViewAdapter(Activity activity, int layoutItem, ArrayList<ThuModel> thuArrayList) {
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
        ThuModel thu = thuArrayList.get(position);

        View rowView = activity.getLayoutInflater().inflate(layoutItem, null, true);

        TextView tvNgayThu = rowView.findViewById(R.id.tvNgayThu);
        TextView tvSoTienThu = rowView.findViewById(R.id.tvSoTien);
        TextView tvMoTa = rowView.findViewById(R.id.tvMoTa);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String ngayThu = sdf.format(thu.getNgayThu());
        tvNgayThu.setText(ngayThu);
        tvSoTienThu.setText(String.valueOf(thu.getSoTien()));
        tvMoTa.setText(thu.getMoTa());

        return rowView;
    }
}
