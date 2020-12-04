package com.example.besedka;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SendMessageApi {
    @POST("/message/create_post")
    Call<String> getResult(@Body SendMessage body);
}
