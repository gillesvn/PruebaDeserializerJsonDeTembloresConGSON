package com.example.pruebadeserializerjsondetemblorescongson.models;

import com.google.gson.annotations.SerializedName;

import static com.example.pruebadeserializerjsondetemblorescongson.utils.Utils.CLOCK_FOR_CONVERTION;
import static com.example.pruebadeserializerjsondetemblorescongson.utils.Utils.getDateFormated;

public class Earthquake {
    private String place;
    private double mag;
    @SerializedName("time")
    private Long date;
    private String url;

    public Earthquake(String place, double mag, Long date, String url) {
        this.place = place;
        this.mag = mag;
        this.date = date;
        this.url = url;
    }

    public String getPlace() {
        return place;
    }

    public double getMag() {
        return Math.round(mag * 10.0) / 10.0;
    }

    public Long getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public int getComparator() {
        int mag = (int) Math.round(this.mag * 10);
        return mag;
    }

    public int getHourForComparator() {
        return getDate().intValue();
    }
}
