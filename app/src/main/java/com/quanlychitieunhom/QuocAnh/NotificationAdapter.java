package com.quanlychitieunhom.QuocAnh;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import java.text.SimpleDateFormat;

import com.quanlychitieunhom.R;

import java.util.List;

public class NotificationAdapter extends ArrayAdapter<NotificationClass> {
    Context context;
    List arrayList;
    int layout;

    public NotificationAdapter(@NonNull Context context, int layout,@NonNull List arrayList) {
        super(context, layout, arrayList);
        this.context = context;
        this.arrayList = arrayList;
        this.layout = layout;
    }

    @Override
public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_notice, parent, false);
    }

    NotificationClass thongBao = getItem(position);
    TextView txtTieuDe = convertView.findViewById(R.id.tvTieude);
    TextView txtNoiDung = convertView.findViewById(R.id.tvNoiDung);
    TextView txtNgayTao = convertView.findViewById(R.id.tvNgayTao);

    txtTieuDe.setText(thongBao.getTieude());

    // Log the value of noidung
    Log.d("NotificationAdapter", "noidung: " + thongBao.getNoidung());

    txtNoiDung.setText(thongBao.getNoidung());

    // Check if the TextView was found successfully
    if (txtNoiDung == null) {
        Log.d("NotificationAdapter", "txtNoiDung is null");
    } else {
        Log.d("NotificationAdapter", "txtNoiDung is not null");
    }

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String date = sdf.format(thongBao.getNgaytao());
    txtNgayTao.setText(date);

    return convertView;
}

}