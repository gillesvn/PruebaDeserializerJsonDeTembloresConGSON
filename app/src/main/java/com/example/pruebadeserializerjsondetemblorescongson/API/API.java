package com.example.pruebadeserializerjsondetemblorescongson.API;

import com.example.pruebadeserializerjsondetemblorescongson.deserializers.MyDeserializer;
import com.example.pruebadeserializerjsondetemblorescongson.models.EarthquakeReport;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    public static final String BASE_URL = "https://earthquake.usgs.gov/fdsnws/event/1/";
    private static Retrofit retrofit = null;

    //Esto es para cuando se usa el deserializador
    //Se instacia retrofit con la url base
    public static Retrofit getAPI() {
        if (retrofit == null) {
            GsonBuilder builder = new GsonBuilder().registerTypeAdapter(EarthquakeReport.class, new MyDeserializer());
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))//para que el Gson haga la conversión de Json a java con el deserializador propio
                    .build();
        }
        return retrofit;
    }
}
