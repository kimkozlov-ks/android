package com.example.marathonmanager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    public AuthInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest  = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + AuthTokens.accessToken)
                .build();

        return chain.proceed(newRequest);
    }
}
