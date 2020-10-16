package com.example.marathonmanager;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthService {

    @Headers({
            "Content-type: application/json"
    })

    @POST("api/auth/authenticate")
    Call<AuthUser> authenticate(@Body AuthUser authUser);
}