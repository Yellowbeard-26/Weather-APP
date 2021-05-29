package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("weather?&appid=d3079bea2fd16071432de8069a5ed7d0")
    Call<Data> getdata(@Query("q") String name);
}
