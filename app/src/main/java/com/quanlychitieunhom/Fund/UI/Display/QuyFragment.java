package com.quanlychitieunhom.Fund.UI.Display;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.quanlychitieunhom.CreateFund.UI.Display.TaoQuy;
import com.quanlychitieunhom.Fund.UI.State.ChiModel;
import com.quanlychitieunhom.Fund.UI.State.ThuModel;
import com.quanlychitieunhom.Fund.UI.State.QuyModel;
import com.quanlychitieunhom.Fund.UI.State.QuyState;
import com.quanlychitieunhom.Fund.UI.State.QuyViewModel;
import com.quanlychitieunhom.R;
import com.quanlychitieunhom.Uitls.ViewModel.NhomViewModel;

import java.util.ArrayList;
import java.util.List;

public class QuyFragment extends Fragment {
    private TextView tenNhom, soTienBanDau, soTienHienTai;
    private ListView lvThu;
    private Button btnThu, btnChi, btnTaoQuy;
    private ProgressBar progressBar;
    private LinearLayout quyContainer, taoQuyContainer;
    private ArrayList<ThuModel> thuArrayList = new ArrayList<>();
    private ArrayList<ChiModel> chiArrayList = new ArrayList<>();
    private int nhomID;
    private ThuListViewAdapter thuListViewAdapter;
    private ChiListViewAdapter chiListViewAdapter;
    private NhomViewModel nhomViewModel;

    private QuyViewModel quyViewModel;
    private String token, refreshToken;

    public QuyFragment(int nhomId) {
        this.nhomID = nhomId;
        nhomViewModel = new ViewModelProvider(requireActivity()).get(NhomViewModel.class);
        nhomViewModel.setNhomID(nhomId);
    }

    public QuyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.fragment_quy, container, false);

        getControl(view);

        getSharedPref();

        getQuy(nhomID);

        handleTaoQuyEvent();

        handleChiEvent();

        handleThuEvent();

        return view;
    }

    private void getControl(View view) {
        tenNhom = view.findViewById(R.id.tenNhom);
        soTienBanDau = view.findViewById(R.id.soTienBanDau);
        soTienHienTai = view.findViewById(R.id.soTienHienTai);
        lvThu = view.findViewById(R.id.lvThu);
        btnThu = view.findViewById(R.id.btnThu);
        btnChi = view.findViewById(R.id.btnChi);
        progressBar = view.findViewById(R.id.progressBar);
        quyContainer = view.findViewById(R.id.quyContainer);
        taoQuyContainer = view.findViewById(R.id.taoQuyContainer);
        btnTaoQuy = view.findViewById(R.id.btnTaoQuy);
        tenNhom.setVisibility(View.GONE);
    }

    private void handleChiEvent() {
        btnChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChiListViewAdapter();
            }
        });
    }

    private void handleThuEvent() {
        btnThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setThuListViewAdapter();
            }
        });
    }

    private void handleTaoQuyEvent() {
        btnTaoQuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start tao quy fragment
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new TaoQuy(nhomID))
                        .commit();
            }
        });
    }

    private void getQuy(int nhomID) {
        quyViewModel = new ViewModelProvider(requireActivity()).get(QuyViewModel.class);
        quyViewModel.getQuy(nhomID, token, refreshToken, getContext());
        loadQuy();
    }

    private void loadQuy() {
        quyViewModel.getQuyViewStateLiveData().observe(getViewLifecycleOwner(), quyViewState -> {
            if(quyViewState.getQuyState() == QuyState.LOADING) {
                progressBar.setVisibility(View.VISIBLE);
                quyContainer.setVisibility(View.GONE);
            } else if(quyViewState.getQuyState() == QuyState.ERROR) {
                progressBar.setVisibility(View.GONE);
                quyContainer.setVisibility(View.GONE);
                taoQuyContainer.setVisibility(View.VISIBLE);
                Toast.makeText(requireContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            } else if(quyViewState.getQuyState() == QuyState.SUCCESS) {
                progressBar.setVisibility(View.GONE);
                taoQuyContainer.setVisibility(View.GONE);
                quyContainer.setVisibility(View.VISIBLE);
                QuyModel quyModel = quyViewState.getQuyModel();
                soTienBanDau.setText(String.valueOf(quyModel.getSoTienBD()));
                soTienHienTai.setText(String.valueOf(quyModel.getSoTienHT()));
                thuArrayList.clear();
                chiArrayList.clear();
                setThuList(quyModel);
                setThuListViewAdapter();
                setChiList(quyModel);
//                tenNhom.setText(quyModel.());
            } else {
                Toast.makeText(requireContext(), "Quỹ không tồn tại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setThuList(QuyModel quyModel) {
        if(quyModel.getThus() != null) {
//                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            List<ThuModel> thuList = quyModel.getThus();
            thuList.stream().forEach(thuModel -> {
//                        String formattedDate = formatter.format(thuModel.getNgayThu());
                thuArrayList.add(new ThuModel(thuModel.getId(), thuModel.getSoTien(), thuModel.getMoTa(), thuModel.getNgayThu()));
            });
        }
    }

    private void setThuListViewAdapter() {
        thuListViewAdapter = new ThuListViewAdapter(requireActivity(), R.layout.thu_row, thuArrayList);
        lvThu.setAdapter(thuListViewAdapter);
    }

    private void setChiList(QuyModel quyModel) {
        if(quyModel.getThus() != null) {
            List<ChiModel> chiList = quyModel.getChis();
            chiList.stream().forEach(chiModel -> {
                chiArrayList.add(new ChiModel(chiModel.getId(), chiModel.getSoTien(), chiModel.getNgayChi(), chiModel.getMoTa()));
            });
        }
    }

    private void setChiListViewAdapter() {
        chiListViewAdapter = new ChiListViewAdapter(requireActivity(), R.layout.thu_row, chiArrayList);
        lvThu.setAdapter(chiListViewAdapter);
    }

    private void getSharedPref() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        refreshToken = sharedPreferences.getString("refreshToken", "");
    }



}