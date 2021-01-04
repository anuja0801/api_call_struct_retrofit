package com.interf;

import com.adapter.JSONResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {
    @GET("users?page=2")
    Call<JSONResponse> getJSON();
    @GET("live-data")
    Call<JSONResponse> getUser();
}
