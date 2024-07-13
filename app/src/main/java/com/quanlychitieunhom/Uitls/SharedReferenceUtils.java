package com.quanlychitieunhom.Uitls;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedReferenceUtils {
    public static String getAccessToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    public static String getRefreshToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        return sharedPreferences.getString("refreshToken", "");
    }
}
