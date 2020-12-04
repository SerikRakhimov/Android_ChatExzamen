package com.example.besedka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {

    private List<Message> messageList;
    MessageAdapter messageAdapter;
    ScrollView scrollView;
    RecyclerView recyclerView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        recyclerView = findViewById(R.id.recyclerView);
        messageList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(ChatActivity.this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        editText = findViewById(R.id.text);
        ImageButton buttonSend = findViewById(R.id.send);

        MessagesTask mt = new MessagesTask();
        mt.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, username);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_retrofitSendMessage(username, editText.getText().toString());
            }
        });
    }

    class MessagesTask extends AsyncTask<String, Void, Void> {
        int number, sec;

        protected Void doInBackground(String... params) {
            try {
                do {
                    call_retrofitViewMessages(params[0]);
                    TimeUnit.SECONDS.sleep(3);
                } while (true);

            } catch (InterruptedException e) {
                Log.d("Hello", "Interrupted");
                e.printStackTrace();
                return null;
            }
        }

    }

    public void call_retrofitViewMessages(String username) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://besedka.rsb0807.kz")
                .addConverterFactory((GsonConverterFactory.create()))
                .build();
        MessageApi messageApi = retrofit.create(MessageApi.class);
        Call<List<Message>> messages = messageApi.get_messages();
        messages.enqueue(new retrofit2.Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                messageList = response.body();
                messageAdapter = new MessageAdapter(messageList, username);
                recyclerView.setAdapter(messageAdapter);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                t.printStackTrace();
                Log.i("Hello", t.getMessage());
            }

        });
    }

    public void call_retrofitSendMessage(String username, String text) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://besedka.rsb0807.kz")
                .addConverterFactory((GsonConverterFactory.create()))
                .build();
        SendMessageApi sendMessageApi = retrofit.create(SendMessageApi.class);
        SendMessage message = new SendMessage(username, text);
        Call<String> response = sendMessageApi.getResult(message);

        response.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body();
                Boolean result = (body.equals("true"));

                if (result == false) {
                    Toast.makeText(ChatActivity.this, "Для пользователя '" + username + "' не удалось отправить сообщение в чат!", Toast.LENGTH_SHORT).show();
                }
                editText.setText("");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.i("Hello", t.getMessage());
            }
        });
    }

}