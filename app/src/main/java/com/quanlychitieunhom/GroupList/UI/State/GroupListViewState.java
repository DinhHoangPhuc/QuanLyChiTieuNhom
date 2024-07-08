package com.quanlychitieunhom.GroupList.UI.State;

public class GroupListViewState {
    private final GroupListState groupListState;
    private final ListNhomModel listNhomModel;

    public GroupListViewState(GroupListState groupListState, ListNhomModel listNhomModel) {
        this.groupListState = groupListState;
        this.listNhomModel = listNhomModel;
    }

    public GroupListState getGroupListState() {
        return groupListState;
    }

    public ListNhomModel getListNhomModel() {
        return listNhomModel;
    }
}
