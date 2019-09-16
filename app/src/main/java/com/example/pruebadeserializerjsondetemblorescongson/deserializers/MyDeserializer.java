package com.example.pruebadeserializerjsondetemblorescongson.deserializers;

import com.example.pruebadeserializerjsondetemblorescongson.models.Earthquake;
import com.example.pruebadeserializerjsondetemblorescongson.models.EarthquakeReport;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyDeserializer implements JsonDeserializer<EarthquakeReport> {
    @Override
    public EarthquakeReport deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonArray features = json.getAsJsonObject().get("features").getAsJsonArray();
        List<Earthquake> earthquakeList = new ArrayList<>();
        for (int i = 0; i < features.size(); i++) {
            earthquakeList.add(new Earthquake(features.get(i).getAsJsonObject().get("properties").getAsJsonObject().get("place").getAsString(),
                    features.get(i).getAsJsonObject().get("properties").getAsJsonObject().get("mag").getAsDouble(),
                    features.get(i).getAsJsonObject().get("properties").getAsJsonObject().get("time").getAsLong(),
                    features.get(i).getAsJsonObject().get("properties").getAsJsonObject().get("url").getAsString()));
        }
        return new EarthquakeReport(earthquakeList);
    }
}
