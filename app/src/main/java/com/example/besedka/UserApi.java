package com.example.besedka;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {
//    @SerializedName("users_status")
    @GET("/user/get_status")
    Call<List<User>> get_users();

}