package com.example.besedka;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MessageApi {
    @GET("/message/get_messages")
    Call<List<Message>> get_messages();
}
