package com.example.besedka;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LogoutApi {
    @POST("/user/logout_post")
    Call<String> getResult(@Body Logout body);
}
