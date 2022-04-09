package com.example.android_retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_retrofit.R;
import com.example.android_retrofit.api.ApiClient;
import com.example.android_retrofit.model.User;
import com.example.android_retrofit.api.ApiClient;
import com.example.android_retrofit.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserDetailActivity extends AppCompatActivity {

    TextView tvUserId;
    EditText etUserName;
    EditText etUserEmail;
    EditText etUserGender;
    EditText etUserStatus;
    TextView tvMessage;

    Button btnSave;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        tvUserId = findViewById(R.id.userId);
        etUserName = findViewById(R.id.userName);
        etUserEmail = findViewById(R.id.userEmail);
        etUserGender = findViewById(R.id.userGender);
        etUserStatus = findViewById(R.id.userStatus);
        tvMessage = findViewById(R.id.tvMessage);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(view -> {
            int userId = Integer.parseInt(tvUserId.getText().toString());
            ApiClient.getAPI().deleteUser(userId).enqueue(new Callback<User>() {
                @Override
                public void onResponse(retrofit2.Call<User> call, Response<User> response) {
                    tvMessage.setText(response.message());
                }

                @Override
                public void onFailure(retrofit2.Call<User> call, Throwable t) {
                    tvMessage.setText(t.getMessage());
                }
            });
        });

        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId", 0);
        if (userId != 0) {
            String userName = intent.getStringExtra("userName");
            String userEmail = intent.getStringExtra("userEmail");
            String userGender = intent.getStringExtra("userGender");
            String userStatus = intent.getStringExtra("userStatus");
            User user = new User(userId, userName, userEmail, userGender, userStatus);
            updateUI(user);

            // Update User
            btnSave.setOnClickListener(view -> {
                User data = getUserFromInput();
                ApiClient.getAPI().putUser(data, data.id).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(retrofit2.Call<User> call, Response<User> response) {
                        if (response.body() != null) {
                            updateUI(response.body());
                        }
                        tvMessage.setText(response.message());
                    }

                    @Override
                    public void onFailure(retrofit2.Call<User> call, Throwable t) {
                        tvMessage.setText(t.getMessage());
                    }
                });
            });
        } else {
            Log.d("USER", "setup");
            tvUserId.setEnabled(false);
            btnDelete.setVisibility(View.GONE);

            // POST User
            btnSave.setOnClickListener(view -> {
                User data = getUserFromInput();
                Log.d("USER", data.toString());
                ApiClient.getAPI().postUser(data).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(retrofit2.Call<User> call, Response<User> response) {
                        if (response.body() != null) {
                            updateUI(response.body());
                        }
                        tvMessage.setText(response.message());
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        tvMessage.setText(t.getMessage());
                    }
                });
            });
        }

    }

    private void updateUI(User user) {
        tvUserId.setText(String.valueOf(user.id));
        etUserName.setText(user.name);
        etUserEmail.setText(user.email);
        etUserGender.setText(user.gender);
        etUserStatus.setText(user.status);
    }

    private User getUserFromInput() {
        int id = Integer.parseInt(tvUserId.getText().toString());
        String name = etUserName.getText().toString();
        String email = etUserEmail.getText().toString();
        String gender = etUserGender.getText().toString();
        String status = etUserStatus.getText().toString();
        return new User(id, name, email, gender, status);
    }
}