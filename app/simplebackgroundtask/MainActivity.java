package com.example.simplebackgroundtask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplebackgroundtask.adapter.UserListAdapter;
import com.example.simplebackgroundtask.api.ApiClient;
import com.example.simplebackgroundtask.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ArrayList<User> mWordList = new ArrayList<>();
    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private UserListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView1);
        progressBar = findViewById(R.id.progressBar);
        mRecyclerView = findViewById(R.id.recyclerview);

        mAdapter = new UserListAdapter(mWordList, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btnRefresh).callOnClick();

        findViewById(R.id.btnCreate).setOnClickListener(view -> {
            Intent intent = new Intent(this, UserDetailActivity.class);
            intent.putExtra("userId", 0);
            startActivity(intent);
        });
    }

    public void startTask(View view) {
        view.setEnabled(false);
        mWordList.clear();
        mTextView.setText(R.string.loading);
        progressBar.setVisibility(View.VISIBLE);
        ApiClient.getAPI().getAllUsers().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<User>> call, @NonNull Response<ArrayList<User>> response) {
                ArrayList<User> userList = response.body();
                mTextView.setText("Number of Users: " + userList.size());
                mWordList.addAll(userList);
                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                view.setEnabled(true);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<User>> call, @NonNull Throwable t) {
                mTextView.setText("Error:" + t.getMessage());
                view.setEnabled(true);
            }
        });
    }
}