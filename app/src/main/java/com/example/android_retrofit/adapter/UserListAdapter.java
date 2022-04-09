package com.example.android_retrofit.adapter;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_retrofit.R;
import com.example.android_retrofit.model.User;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private final ArrayList<User> mUserList;
    private Context context;

    public UserListAdapter(ArrayList<User> mUserList, Context context) {
        this.mUserList = mUserList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View mItemView = mInflater.inflate(R.layout.userlist_item, parent, false);
        return new UserViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mUserList.get(position);
        String mCurrent = user.name;
        holder.tvUserName.setText(mCurrent);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, com.example.android_retrofit.UserDetailActivity.class);
            intent.putExtra("userId", user.id);
            intent.putExtra("userName", user.name);
            intent.putExtra("userEmail", user.email);
            intent.putExtra("userGender", user.gender);
            intent.putExtra("userStatus", user.status);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvUserName;
        final UserListAdapter mAdapter;

        public UserViewHolder(@NonNull View itemView, UserListAdapter adapter) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.uName);
            this.mAdapter = adapter;
        }
    }
}