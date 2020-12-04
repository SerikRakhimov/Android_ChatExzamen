package com.example.besedka;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginApi {
    @POST("/user/login_post")
    Call<String> getResult(@Body Resp body);
}
