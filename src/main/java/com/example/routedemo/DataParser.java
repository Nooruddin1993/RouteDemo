package com.example.routedemo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    private HashMap<String, String> getPlace(JSONObject jsonObject) {
        HashMap<String, String> place = new HashMap<>();
        String placeName = "N/A";
        String vicinity = "N/A";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
            if (!jsonObject.isNull("name"))
                placeName = jsonObject.getString("name");

            if (!jsonObject.isNull("vicinity"))
                vicinity = jsonObject.getString("vicinity");

            latitude = jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = jsonObject.getJSONObject("geometry").getJSONObject("location").getString("lng");

            reference = jsonObject.getString("refrence");

            place.put("placeName", placeName);
            place.put("vicinity", vicinity);
            place.put("latitude", latitude);
            place.put("longitude", longitude);
            place.put("refrence", reference);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return place;
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray) {
        int count = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> place = null;

        for (int i=0; i<count; i++) {
            try {
                place = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(place);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;
    }

    public List<HashMap<String, String>> parse(String jsonData) {
        JSONArray jsonArray = null;

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getPlaces(jsonArray);
    }
}
