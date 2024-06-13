package com.quanlychitieunhom.Phuc;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NhomViewModel extends ViewModel {
    private MutableLiveData<Integer> nhomID;
    public NhomViewModel() {
        super();
    }

    public MutableLiveData<Integer> getNhomID() {
        if (nhomID == null) {
            nhomID = new MutableLiveData<Integer>(0);
        }
        return nhomID;
    }

    public void setNhomID(int id) {
        if (nhomID == null) {
            nhomID = new MutableLiveData<Integer>(id);
        } else {
            nhomID.setValue(id);
        }
    }

}
