package com.example.besedka;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.InfoViewHolder> {

    private List<User> users;

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserAdapter.InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_user_item, parent, false);
        return new UserAdapter.InfoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.InfoViewHolder holder, int position) {
        User user = users.get(position);

        holder.userName.setText(user.getName());
        if (user.getOnline() == 1) {
            holder.status.setImageResource(R.drawable.ic_online);
        } else {
            holder.status.setImageResource(R.drawable.ic_offline);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class InfoViewHolder extends RecyclerView.ViewHolder {

        public TextView userName;
        public ImageView status;

        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            status = itemView.findViewById(R.id.status);
        }

    }

    User getItem(int id) {
        return users.get(id);
    }

}
