package com.example.marathonmanager;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class APIClient {

    private static APIClient mInstance;
    private static final String BASE_URL = "https://d98564cac720.ngrok.io/";
    private Retrofit mRetrofit;

    private APIClient() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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
        return mRetrofit.create(MeasurementService.class);
    }
}
