package com.example.pruebadeserializerjsondetemblorescongson.API.APIServices;

import com.example.pruebadeserializerjsondetemblorescongson.models.EarthquakeReport;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EarthquakeService {
    @GET("query")
    Call<EarthquakeReport> getReport(@Query("format") String format,
                                     @Query("starttime") String startTime,
                                     @Query("endtime") String endtime,
                                     @Query("limit") String limit,
                                     @Query("minmagnitude") String minmagnitude,
                                     @Query("orderby") String orderby);
}
