package com.quanlychitieunhom.Thai;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface CallApiNhom {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0X3VzZXIxIiwiaWF0IjoxNzE3OTIxNTYzLCJleHAiOjE3MTgwMDc5NjN9._D6ZbGCbOWKQVEizpQM7Hg0LuWf6iGY24utqGGs44K9UJnIsNm-BjZAWbnVqYcXa")
    @GET("nhom")
    Call<Nhom> getNhom();
}
