package com.quanlychitieunhom.GroupList.UI.Display;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.quanlychitieunhom.GroupList.UI.State.GroupListState;
import com.quanlychitieunhom.GroupList.UI.State.GroupListViewModel;
import com.quanlychitieunhom.GroupList.UI.State.ListNhomModel;
import com.quanlychitieunhom.GroupList.UI.State.NhomModel;
import com.quanlychitieunhom.Quy.UI.Display.QuyFragment;
import com.quanlychitieunhom.Login.UI.Display.DangNhap;
import com.quanlychitieunhom.R;

import java.util.ArrayList;
import java.util.List;

public class DanhSachNhom extends Fragment {
    private ListView lvNhom;
    private TextView username, chucVu;
    private Button taoNhom, thamGiaNhom;
    private ArrayList<NhomModel> lsNhom = new ArrayList<>();
    private CustomAdapterDanhSachNhom customAdapterDanhSachNhom;

    private String token, refreshToken;
    private long expireTime;
    private String username_display;

    public DanhSachNhom() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_activity_nhom, container, false);

        getControl(view);

        getSharedPref();

        setUsername(username_display);

        loadGroupList();

        setEventOnListView();

        return view;
    }

    private void getControl(View view) {
        taoNhom = view.findViewById(R.id.btn_TaoNhom);
        lvNhom = view.findViewById(R.id.lvDanhSachNhom);
        thamGiaNhom = view.findViewById(R.id.btn_ThamGiaNhom);
        username = view.findViewById(R.id.tvUserName);
        chucVu = view.findViewById(R.id.tvChucVu);
    }

    private void getSharedPref() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        username_display = sharedPreferences.getString("username", "");
        refreshToken = sharedPreferences.getString("refreshToken", "");
        expireTime = sharedPreferences.getLong("expireTime", 0);
    }

    private void setUsername(String username) {
        this.username.setText(username);
        chucVu.setText(username);
    }

    private void loadGroupList() {
        GroupListViewModel groupListViewModel = new ViewModelProvider(requireActivity()).get(GroupListViewModel.class);
        groupListViewModel.getGroupList(username_display, token, expireTime, refreshToken);
        groupListViewModel.getGroupListViewState().observe(getViewLifecycleOwner(), groupListViewState -> {
            if(groupListViewState.getGroupListState() == GroupListState.LOADING) {
                Toast.makeText(getActivity(), "Loading", Toast.LENGTH_SHORT).show();
            } else if(groupListViewState.getGroupListState() == GroupListState.SUCCESS) {
                ListNhomModel listNhomModel = groupListViewState.getListNhomModel();
                List<NhomModel> nhomModelList = listNhomModel.getListNhom();
                lsNhom.clear();
                lsNhom.addAll(nhomModelList);
                customAdapterDanhSachNhom = new CustomAdapterDanhSachNhom(requireActivity(), R.layout.layout_item_thanhvien, lsNhom);
                lvNhom.setAdapter(customAdapterDanhSachNhom);
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
            } else if(groupListViewState.getGroupListState() == GroupListState.ERROR) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                callLoginActivity();
            }
        });
        groupListViewModel.getRefreshTokenViewState().observe(getViewLifecycleOwner(), loginResonpse -> {
            if(loginResonpse != null) {
                setToken(loginResonpse.getToken());
                setRefreshToken(loginResonpse.getRefreshToken());
                setExpireTime(decodeExpireTime(loginResonpse.getToken()));
                Log.d("Refresh token in dsnhom", loginResonpse.getToken());
            }
        });
    }

    private void setEventOnListView() {
        lvNhom.setOnItemClickListener((parent, view, position, id) -> {
            NhomModel nhomModel = lsNhom.get(position);
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new QuyFragment(nhomModel.getId()))
                    .commit();
        });
    }

    private void setToken(String token) {
        this.token = token;
        SharedPreferences sharedPreferences = requireContext()
                .getSharedPreferences("dataLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    private void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        SharedPreferences sharedPreferences = requireContext()
                .getSharedPreferences("dataLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("refreshToken", refreshToken);
        editor.apply();
    }

    private void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
        SharedPreferences sharedPreferences = requireContext()
                .getSharedPreferences("dataLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("expireTime", expireTime);
        editor.apply();
    }

    private long decodeExpireTime(String token) {
        String[] parts = token.split("\\.");
        String payload = parts[1];
        byte[] data = android.util.Base64.decode(payload, android.util.Base64.DEFAULT);
        String text = new String(data);
        String[] parts2 = text.split(",");
        String expireTime = parts2[2].split(":")[1];
        expireTime = expireTime.replaceAll("[^0-9]", ""); // Remove non-numeric characters
        return Long.parseLong(expireTime);
    }

    private void callLoginActivity() {
        Intent intent = new Intent(getActivity(), DangNhap.class);
        startActivity(intent);
    }
}
