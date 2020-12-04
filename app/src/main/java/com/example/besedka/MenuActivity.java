package com.example.besedka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        TextView userNameTextView = findViewById(R.id.userName);
        Button buttonListUsers = findViewById(R.id.listUsers);
        Button buttonChat = findViewById(R.id.chat);
        Button buttonExit = findViewById(R.id.exit);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        userNameTextView.setText("Добро пожаловать, " + username + "!");

        buttonListUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, UsersActivity.class);
                startActivity(intent);

            }
        });
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ChatActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_retrofit(username);
            }
        });

    }

    public void call_retrofit(String username) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://besedka.rsb0807.kz")
                .addConverterFactory((GsonConverterFactory.create()))
                .build();
        LogoutApi logoutApi = retrofit.create(LogoutApi.class);
        Logout logout = new Logout(username);
        Call<String> response = logoutApi.getResult(logout);

        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body();
                Boolean result = (body.equals("true"));
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                if (result == false) {
                    Toast.makeText(MenuActivity.this, "Для пользователя '" + username + "' не удалось поменять статус на 'offline'!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.i("Hello", t.getMessage());
            }
        });
    }

}