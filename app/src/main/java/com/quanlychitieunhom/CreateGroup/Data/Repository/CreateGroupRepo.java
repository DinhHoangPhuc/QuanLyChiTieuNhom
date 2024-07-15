package com.quanlychitieunhom.CreateGroup.Data.Repository;

import android.content.Context;

import com.quanlychitieunhom.CreateGroup.UI.State.CreateGroupModel;
import com.quanlychitieunhom.RefreshToken.RefreshTokenCallback;

public interface CreateGroupRepo {
    void createGroup(CreateGroupModel createGroupModel,
                     CreateGroupCallback callback,
                     RefreshTokenCallback refreshTokenCallback,
                     String refreshToken,
                     String token,
                     Context context);
}
