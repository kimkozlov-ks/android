package com.example.marathonmanager;

import com.example.marathonmanager.pojo.AccessToken;
import com.example.marathonmanager.pojo.AuthUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthService {

    @Headers({
            "Content-type: application/json"
    })

    @POST("api/auth/authenticate")
    Call<AccessToken> authenticate(@Body AuthUser authUser);

    @GET("api/auth/refresh")
    Call<AccessToken> refresh(@Header("Cookie") String refreshToken);
}