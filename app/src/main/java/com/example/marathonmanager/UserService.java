package com.example.marathonmanager;

import com.example.marathonmanager.pojo.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;

public interface UserService {
    @Headers({
            "Content-type: application/json"
    })

    @GET("api/user/profile")
    Call<User> getUserProfile();

    @PATCH("api/user/")
    Call<Void> updateUserData(@Body User user);
}
