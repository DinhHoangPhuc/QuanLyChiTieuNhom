package com.quanlychitieunhom.Register.Data.Repository;

import java.util.Map;

public interface RegisterCallback {
    void onApiResponse(Map<String, String> response, int statusCode);

//    void onApiResponse(String message);
}
