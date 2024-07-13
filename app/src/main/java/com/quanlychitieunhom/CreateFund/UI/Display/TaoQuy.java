package com.quanlychitieunhom.CreateFund.UI.Display;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.quanlychitieunhom.CreateFund.UI.State.CreateFundState;
import com.quanlychitieunhom.CreateFund.UI.State.CreateFundViewModel;
import com.quanlychitieunhom.CreateFund.UI.State.FundModel;
import com.quanlychitieunhom.Fund.UI.Display.QuyFragment;
import com.quanlychitieunhom.Login.Data.Repository.LoginResonpse;
import com.quanlychitieunhom.R;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;
import com.quanlychitieunhom.Uitls.SharedReferenceUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class TaoQuy extends Fragment {
    private int nhomID;
    private CreateFundViewModel createFundViewModel;
    private String token;
    private String refreshToken;

    private EditText edtTienLapQuy;
    private ImageButton btnTaoQuy;


    public TaoQuy() {
        // Required empty public constructor
    }

    public TaoQuy(int nhomId) {
        this.nhomID = nhomId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_sheet_taoquy, container, false);
        getControl(view);

        getSharedPreferences();

        createFundViewModel = new ViewModelProvider(requireActivity()).get(CreateFundViewModel.class);

        handleTaoQuy();

        return view;
    }

    private void handleTaoQuy() {
        btnTaoQuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    createFundViewModel.createFund(new FundModel(nhomID, Integer.parseInt(edtTienLapQuy.getText().toString()),
                                    Integer.parseInt(edtTienLapQuy.getText().toString())),
                            new RefreshTokenCallback() {
                                @Override
                                public void onApiResponse(LoginResonpse response) {

                                }
                            }
                            , refreshToken, token, requireContext());
                    createFundViewModel.getCreateFundViewStateLiveData().observe(getViewLifecycleOwner(), response -> {
                        if (response.getState() == CreateFundState.SUCCESS) {
                            showSuccessDialog("Thành công", "Tạo quỹ thành công");
                            startFundFragment();
                        } else if (response.getState() == CreateFundState.ERROR){
                            showSuccessDialog("Thất bại", "Tạo quỹ thất bại");
                        }
                    });
                }
            }
        });
    }

    private void startFundFragment() {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new QuyFragment(nhomID))
                .commit();
    }

    private void showSuccessDialog(String title, String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void getSharedPreferences() {
//        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        token = SharedReferenceUtils.getAccessToken(requireContext());
        refreshToken = SharedReferenceUtils.getRefreshToken(requireContext());
    }

    private void getControl(View view) {
        edtTienLapQuy = view.findViewById(R.id.edtTienLapQuy);
        btnTaoQuy = view.findViewById(R.id.btnTaoQuy);
    }

    private boolean checkInput() {
        if (edtTienLapQuy.getText().toString().isEmpty()) {
            edtTienLapQuy.setError("Vui lòng nhập số tiền");
            return false;
        }
        return true;
    }
}


