package com.example.besedka;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.InfoViewHolder> {

    private List<Message> messages;
    private String usernameMain;

    public MessageAdapter(List<Message> messages, String username) {
        this.messages = messages;
        this.usernameMain = username;
    }

    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message_item, parent, false);
        return new InfoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder holder, int position) {
        Message message = messages.get(position);

        holder.userName.setText(message.getUsername() + ":");
        holder.text.setText(message.getText());
        if (message.getUsername().equalsIgnoreCase(usernameMain)) {
            holder.userName.setGravity(Gravity.RIGHT);
            holder.text.setGravity(Gravity.RIGHT);
            holder.text.setTextColor(Color.BLACK);
        }
        else{
            holder.userName.setGravity(Gravity.LEFT);
            holder.text.setGravity(Gravity.LEFT);
            holder.text.setTextColor(Color.DKGRAY);
        }
        ;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class InfoViewHolder extends RecyclerView.ViewHolder {

        public TextView userName, text;

        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            text = itemView.findViewById(R.id.text);
        }

    }

    Message getItem(int id) {
        return messages.get(id);
    }

}
