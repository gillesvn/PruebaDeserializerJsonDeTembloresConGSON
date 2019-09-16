package com.example.pruebadeserializerjsondetemblorescongson.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EarthquakeReport {
    @SerializedName("features")
    private List<Earthquake> earthquakeList;

    public EarthquakeReport(List<Earthquake> earthquakeList) {
        this.earthquakeList = earthquakeList;
    }

    public List<Earthquake> getEarthquakeList() {
        return earthquakeList;
    }

}
