package com.example.simplebackgroundtask.api;

import com.example.simplebackgroundtask.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("users")
    Call<ArrayList<User>> getAllUsers();

    String token = "f7df80072fd14f617b363ba852eac78a64083fcdfa4f7ee0759a915101c1976c";

    @POST("users?access-token=" + token)
    Call<User> postUser(@Body User user);

    @PUT("users/{id}?access-token=" + token)
    Call<User> putUser(@Body User user, @Path("id") int id);

    @DELETE("users/{id}?access-token=" + token)
    Call<User> deleteUser(@Path("id") int id);
}
