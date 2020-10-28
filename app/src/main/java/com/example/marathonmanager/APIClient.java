package com.example.marathonmanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class APIClient {

    private static APIClient mInstance;
    private static final String BASE_URL = "https://4884c522febc.ngrok.io/";
    private static final String BASE_URL_AUTH = "https://8bf4655b57f9.ngrok.io/";
    private Retrofit mRetrofitApi;
    private Retrofit mRetrofitAuth;

    private APIClient() {

        Gson gsonDate = new GsonBuilder()
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
                .addConverterFactory(GsonConverterFactory.create(gsonDate))
                .client(httpClient.build())
                .build();

        mRetrofitAuth = new Retrofit.Builder()
                .baseUrl(BASE_URL_AUTH)
                .addConverterFactory(GsonConverterFactory.create(gsonDate))
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

    public UserService getUserService() { return  mRetrofitApi.create(UserService.class); }
}
