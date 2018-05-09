package com.widgetexample.net;

import com.widgetexample.entities.UsersResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface RetrofitEndPoint {

    @GET("/users")
    Call<List<UsersResponse>> getUsers();

}
