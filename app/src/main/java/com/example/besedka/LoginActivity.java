package com.example.besedka;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText editUsername = findViewById(R.id.username);
        EditText editPassword = findViewById(R.id.password);
        Button buttonLogin = findViewById(R.id.login);
        Button buttonExit = findViewById(R.id.exit);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editUsername.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Заполните поле Имя пользователя!", Toast.LENGTH_LONG).show();
                } else if (editPassword.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Заполните поле Пароль!", Toast.LENGTH_LONG).show();
                } else {
                         call_retrofit(editUsername.getText().toString(), editPassword.getText().toString());
                }
            }
        });
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void call_retrofit(String username, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://besedka.rsb0807.kz")
                .addConverterFactory((GsonConverterFactory.create()))
                .build();
        LoginApi loginApi = retrofit.create(LoginApi.class);
        Resp resp = new Resp(username, password);
        Call<String> response = loginApi.getResult(resp);

        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body();
                Boolean result = (body.equals("true"));
                if (result == true) {
                    Toast.makeText(LoginActivity.this, "Успешный вход в чат!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Отказано во входе в чат (возможно неправильно введены имя пользователя и/или пароль)!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Hello", t.getMessage());
            }
        });
    }

}