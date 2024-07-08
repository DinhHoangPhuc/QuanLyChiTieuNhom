package com.quanlychitieunhom.GroupList.UI.State;

import java.util.List;

public class ListNhomModel {
    private List<NhomModel> listNhom;

    public ListNhomModel(List<NhomModel> listNhom) {
        this.listNhom = listNhom;
    }

    public List<NhomModel> getListNhom() {
        return listNhom;
    }

    public void setListNhom(List<NhomModel> listNhom) {
        this.listNhom = listNhom;
    }
}
