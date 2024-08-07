package com.quanlychitieunhom.GroupList.Data.Repository;

import android.content.Context;

import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;

public interface GroupListRepo {
    void getGroupList(String username,
                      String token,
                      GroupListCallback callback,
                      RefreshTokenCallback refreshTokenCallback,
                      String refreshToken,
                      Context context);
}
