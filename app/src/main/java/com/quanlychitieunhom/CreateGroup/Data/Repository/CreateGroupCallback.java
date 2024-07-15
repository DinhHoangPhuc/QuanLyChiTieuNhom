package com.quanlychitieunhom.CreateGroup.Data.Repository;

public interface CreateGroupCallback {
    void onCreateGroupSuccess(CreateGroupResponse createGroupResponse);
    void onCreateGroupFailed(String message);
}
