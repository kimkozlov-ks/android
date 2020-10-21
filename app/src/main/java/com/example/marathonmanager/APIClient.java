package com.example.marathonmanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class APIClient {

    private static APIClient mInstance;
    private static final String BASE_URL = "https://fbabbd43bcf3.ngrok.io/";
    private static final String BASE_URL_AUTH = "https://9aa9ce15e0ff.ngrok.io/";
    private Retrofit mRetrofitApi;
    private Retrofit mRetrofitAuth;

    private APIClient() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient
                .Builder()
                .addInterceptor(new AuthInterceptor());
        httpClient.addInterceptor(logging);

        mRetrofitApi = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        mRetrofitAuth = new Retrofit.Builder()
                .baseUrl(BASE_URL_AUTH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
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
