package com.quanlychitieunhom.Thai;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.quanlychitieunhom.Phuc.NhomViewModel;
import com.quanlychitieunhom.Phuc.QuyFragment;
import com.quanlychitieunhom.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class DanhSachNhom extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    ListView lvNhom;
    TextView username, chucVu;
    Button taonhom,test;
    ArrayList<Nhom> lsNhom = new ArrayList<>();
    CustomAdapterDanhSachNhom customAdapterDanhSachNhom;

    String token;
    String usernamee;

    public DanhSachNhom() {
        // Required empty public constructor
    }

    public static DanhSachNhom newInstance(String param1, String param2) {
        DanhSachNhom fragment = new DanhSachNhom();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_activity_nhom, container, false);
        taonhom = view.findViewById(R.id.btn_TaoNhom);
        lvNhom = view.findViewById(R.id.lvDanhSachNhom);
        test = view.findViewById(R.id.btn_ThamGiaNhom);
        username = view.findViewById(R.id.tvUserName);
        chucVu = view.findViewById(R.id.tvChucVu);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        usernamee = sharedPreferences.getString("username", "");

        username.setText(usernamee);
        chucVu.setText(usernamee);

        addEvents();
        return view;
    }
    private void addEvents(){
//        username.setText(usernamee);


        String usn = username.getText().toString();
        //call api load danh sach nhom theo username
        String urlGetNhom ="http://10.0.2.2:8080/api/nhom/getAllNhom?username="+ usernamee;
        StringRequest stringRequestDSN = new StringRequest(Request.Method.GET, urlGetNhom, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "That bai" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+ token);
                return headers;
            }
        };
        RequestQueue requestQueueDSN = Volley.newRequestQueue(getActivity());
        requestQueueDSN.add(stringRequestDSN);
        taonhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new TaoNhom());
            }
        });
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        lvNhom.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(android.widget.AdapterView<?> parent, View view, int position, long id) {
                int selectedItemId = lsNhom.get(position).getId();
                NhomViewModel nhomViewModel = new ViewModelProvider(getActivity()).get(NhomViewModel.class);
                nhomViewModel.setNhomID(selectedItemId);
//                loadFragment(new QuyFragment());
//                Bundle bundle = new Bundle();
//                bundle.putInt("selectedItemId", selectedItemId);
//                TaoQuy taoQuy = new TaoQuy();
//                taoQuy.setArguments(bundle);
//                String urlGetQuy = "http://10.0.2.2:8080/api/quy/getQuy?nhomId=" + selectedItemId;
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGetQuy, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
////                        loadFragment(new QuyFragment());
////                        Toast.makeText(getActivity(), "Quỹ tồn tại cho nhóm này!", Toast.LENGTH_SHORT).show();
//                        NhomViewModel nhomViewModel = new ViewModelProvider(getActivity()).get(NhomViewModel.class);
//                        nhomViewModel.setNhomID(selectedItemId);
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        loadFragment(taoQuy);
//                        Toast.makeText(getActivity(), "Không có Quỹ tồn tại cho nhóm này, vui lòng tạo quỹ!", Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Map<String, String> headers = new HashMap<>();
//                        headers.put("Authorization", "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0X3VzZXIxIiwiaWF0IjoxNzE4MjI0NTkwLCJleHAiOjE3MTgzMTA5OTB9.f6TnMz47-Gl6PL_gPTuztyZWtUij5lfHlt6_1YmXVgbORwt9W4j8U5T8Je96lpvb");
//                        return headers;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//                requestQueue.add(stringRequest);
////                loadFragment(taoQuy);
//                checkQuyExistence(selectedItemId);
            }
        });

    }
    void parseJson(String jsonString){
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                int idNhom = object.getInt("id");
                String tenNhom = object.getString("tenNhom");
                String anhNhom = object.optString("anhNhom");
                lsNhom.add(new Nhom(idNhom,tenNhom,anhNhom));
            }
            customAdapterDanhSachNhom = new CustomAdapterDanhSachNhom(getActivity(),R.layout.layout_item_thanhvien, lsNhom);
            lvNhom.setAdapter(customAdapterDanhSachNhom);
            Toast.makeText(getActivity(), "Thanh cong", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public void checkQuyExistence(int nhomId) {
        String urlGetQuy = "http://10.0.2.2:8080/api/quy/getQuy?nhomId=" + nhomId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGetQuy, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loadFragment(new giaodientest());
                Toast.makeText(getActivity(), "Quỹ tồn tại cho nhóm này!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadFragment(new TaoQuy());
                Toast.makeText(getActivity(), "Không có Quỹ tồn tại cho nhóm này, vui lòng tạo quỹ!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    private void showBottomDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_sheet_taonhom);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
    public void loadFragment (Fragment fragment)
    {
        Fragment newFragment = fragment;
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}
