package com.quanlychitieunhom.QuocAnh;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quanlychitieunhom.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragNotice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragNotice extends Fragment {
    public static int nhomid = 1;
    Button btnBack;
    ListView lvNotifications;
    TextView textView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragNotice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragNotice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragNotice newInstance(String param1, String param2) {
        FragNotice fragment = new FragNotice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_notice, container, false);
        btnBack = view.findViewById(R.id.btnBack);
        lvNotifications = view.findViewById(R.id.lvNotification);
        textView = view.findViewById(R.id.textView);
        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        if(!token.isEmpty()) {
            loadDataToListView("http://10.0.2.2:8080/api/thongbao/getThongBao?nhomId=" + nhomid, token);
        }
        return view;
    }

   public void loadDataToListView(String url, String token) {
    new Thread(() -> {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + token)
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseStr = response.body().string();
                Gson gson = new Gson();
                NotificationClass[] data = gson.fromJson(responseStr, NotificationClass[].class);
                List<NotificationClass> dataArray = Arrays.asList(data);
                Collections.sort(dataArray, new Comparator<NotificationClass>() {
                    @Override
                    public int compare(NotificationClass o1, NotificationClass o2) {
                        return o1.getNgaytao().compareTo(o2.getNgaytao()); // nếu bạn muốn sắp xếp tăng dần
                    }
                });
                getActivity().runOnUiThread(() -> {
                    NotificationAdapter adapter = new NotificationAdapter(getActivity(), R.layout.listview_notice, dataArray);
                    lvNotifications.setAdapter(adapter);
                    Toast.makeText(getActivity(), "Load data successful", Toast.LENGTH_SHORT).show();
                });
            } else {
                getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Load data failed", Toast.LENGTH_SHORT).show());
            }
        } catch (Exception e) {
            e.printStackTrace();
            getActivity().runOnUiThread(() -> {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                textView.setText(e.getMessage());
            });
        }
    }).start();
}
}