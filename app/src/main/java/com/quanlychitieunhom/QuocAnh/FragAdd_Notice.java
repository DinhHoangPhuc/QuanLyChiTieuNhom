package com.quanlychitieunhom.QuocAnh;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quanlychitieunhom.R;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragAdd_Notice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragAdd_Notice extends Fragment {
    public static int nhomid = 2;
    Button btnAddNotification,btnBack;
    EditText edtTitle, edtContent;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragAdd_Notice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragAdd_Notice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragAdd_Notice newInstance(String param1, String param2) {
        FragAdd_Notice fragment = new FragAdd_Notice();
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
        View view = inflater.inflate(R.layout.fragment_frag_add__notice, container, false);

        btnAddNotification = view.findViewById(R.id.btnDang);
        btnBack = view.findViewById(R.id.btnBack);
        edtTitle = view.findViewById(R.id.edtTieuDe);
        edtContent = view.findViewById(R.id.edtNoiDung);
        btnBack.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

            }
        });
        btnAddNotification.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();
                String currentDateTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_DATE_TIME);
                addThongBao(title, content, currentDateTime);
            }
        });
        return view;
    }
    public void addThongBao(String tieude, String noiDung, String ngaydang) {
        if (tieude.isEmpty() || noiDung.isEmpty() || tieude.length() > 40) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    if (tieude.isEmpty() || noiDung.isEmpty()) {
                        Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
                    } else if (tieude.length() > 40) {
                        Toast.makeText(getActivity(), "Tiêu đề không được nhập quá 40 kí tự", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return;
        }

        new Thread(() -> {
            try {
                JSONObject newNotification = new JSONObject();
                newNotification.put("nhomid", DanhSachNhom.selectedItemId);
                newNotification.put("tieude", tieude);
                newNotification.put("noiDung", noiDung);
                newNotification.put("ngaydang", ngaydang);

                URL url = new URL("http://10.0.2.2:8080/api/thongbao/addThongBao");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");

                SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "");
                if(!token.isEmpty()) {
                    conn.setRequestProperty("Authorization", "Bearer " + token);
                }
                conn.setDoOutput(true);
                try(OutputStream os = conn.getOutputStream()) {
                    byte[] input = newNotification.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                System.out.println("Response Code: " + responseCode); // Print the response code
                if (responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Đăng thông báo thành công", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Đăng thông báo thất bại", Toast.LENGTH_SHORT).show());
                    }
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error: " + e.getMessage()); // Print the error message
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }
        }).start();
    }
}