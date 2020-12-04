package com.example.besedka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersActivity extends AppCompatActivity {
    private List<User> userList;
    UserAdapter userAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        recyclerView = findViewById(R.id.recyclerView);
        userList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(UsersActivity.this));
        retrofit();
    }

    public void retrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://besedka.rsb0807.kz")
                .addConverterFactory((GsonConverterFactory.create()))
                .build();
        UserApi userApi = retrofit.create(UserApi.class);
        Call<List<User>> users = userApi.get_users();
        users.enqueue(new retrofit2.Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userList = response.body();
//               Log.i("Hello", Integer.toString(userList.size()));
                userAdapter = new UserAdapter(userList);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                t.printStackTrace();
                Log.i("Hello", t.getMessage());
            }

        });
    }

}