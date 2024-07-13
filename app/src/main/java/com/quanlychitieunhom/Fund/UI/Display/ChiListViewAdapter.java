package com.quanlychitieunhom.Fund.UI.Display;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.quanlychitieunhom.Fund.UI.State.ChiModel;
import com.quanlychitieunhom.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChiListViewAdapter extends BaseAdapter {
    private Activity activity;
    private int layoutItem;
    private ArrayList<ChiModel> chiArrayList;

    public ChiListViewAdapter(Activity activity, int layoutItem, ArrayList<ChiModel> chiArrayList) {
        this.activity = activity;
        this.layoutItem = layoutItem;
        this.chiArrayList = chiArrayList;
    }
    @Override
    public int getCount() {
        return chiArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return chiArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChiModel chi = chiArrayList.get(position);

        View view = activity.getLayoutInflater().inflate(layoutItem, null);

        TextView tvNgayChi = view.findViewById(R.id.tvNgayThu);
        TextView tvSoTienChi = view.findViewById(R.id.tvSoTien);
        TextView tvMoTa = view.findViewById(R.id.tvMoTa);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String ngayThu = sdf.format(chi.getNgayChi());
        tvNgayChi.setText(ngayThu);
        tvSoTienChi.setText(String.valueOf(chi.getSoTien()));
        tvMoTa.setText(chi.getMoTa());

        return view;
    }
}
