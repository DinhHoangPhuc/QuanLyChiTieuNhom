package com.quanlychitieunhom.QuocAnh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.quanlychitieunhom.R;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragJoin_Group#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragJoin_Group extends Fragment {

    EditText edtDuongDan;
    Button btnThamGia;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragJoin_Group() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragJoin_Group.
     */
    // TODO: Rename and change types and number of parameters
    public static FragJoin_Group newInstance(String param1, String param2) {
        FragJoin_Group fragment = new FragJoin_Group();
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
        View view = inflater.inflate(R.layout.fragment_frag_join__group, container, false);
        edtDuongDan = view.findViewById(R.id.edtDuongDan);
        btnThamGia = view.findViewById(R.id.btnThamGia);
        btnThamGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = edtDuongDan.getText().toString();
                String prefix = "http://localhost:8080/api/nhom/getnhomid?nhomid=";
                if(url.startsWith(prefix)) {
                    int nhomId = Integer.parseInt(url.substring(prefix.length()));
                    SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
                    int nguoiDungId = sharedPreferences.getInt("nguoiDungId", 1);
                    addThanhVien(nhomId, nguoiDungId);
                } else {
                    Toast.makeText(getActivity(), "Đường dẫn không hợp lệ", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    private void addThanhVien(int nhomId, int nguoiDungId) {
        new Thread(() -> {
            try {
                JSONObject newMember = new JSONObject();
                newMember.put("nhomId", nhomId);
                newMember.put("nguoiDungId", nguoiDungId);
                newMember.put("quyen", 0); // Default role is 0

                URL url = new URL("http://10.0.2.2:8080/api/thanhvien/addThanhVien");

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
                    byte[] input = newMember.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                System.out.println("Response Code: " + responseCode); // Print the response code
                if (responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Đã tham gia", Toast.LENGTH_SHORT).show());
                        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Thêm thành viên thành công", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Thêm thành viên thất bại", Toast.LENGTH_SHORT).show());
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