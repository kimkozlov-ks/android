package com.example.marathonmanager;

import com.example.marathonmanager.pojo.Measurement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MeasurementService {

    @Headers({
            "Content-type: application/json"
    })

    @POST("api/measurement/")
    Call<Measurement> sendMeasurement(@Body Measurement measurement);

    @GET("api/measurement/")
    Call<List<Measurement>> getAllMeasurement();
}
