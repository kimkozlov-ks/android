package com.example.marathonmanager;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class APIClient {

    private static APIClient mInstance;
    private static final String BASE_URL = "https://fbabbd43bcf3.ngrok.io/";
    private static final String BASE_URL_AUTH = "https://9aa9ce15e0ff.ngrok.io/";
    private Retrofit mRetrofitApi;
    private Retrofit mRetrofitAuth;

    private APIClient() {
        mRetrofitApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRetrofitAuth = new Retrofit.Builder()
                .baseUrl(BASE_URL_AUTH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static APIClient getInstance() {
        if (mInstance == null) {
            mInstance = new APIClient();
        }
        return mInstance;
    }

    public MeasurementService getMeasurementService() {
        return mRetrofitApi.create(MeasurementService.class);
    }

    public AuthService getAuthService(){
        return mRetrofitAuth.create(AuthService.class);
    }
}
