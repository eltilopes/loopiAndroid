package br.com.aio.utils;


import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.aio.entity.GoogleDirectionsResponse;

/**
 * Created by elton on 07/11/2017.
 */

public class DirectionUtils {

    public DirectionUtils(){   }

    public GoogleDirectionsResponse getGoogleDirectionsResponse(LatLng start, LatLng end) {
        String link = "https://maps.googleapis.com/maps/api/directions/json?"
                + "origin=" + start.latitude + "," + start.longitude
                + "&destination=" + end.latitude + "," + end.longitude
                + "&sensor=false&units=metric&mode=driving&key=AIzaSyBcU9bk64c7cOig4us1WMlO_o_k7Ococ6o";
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        GoogleDirectionsResponse googleDirectionsResponse = null;
        try {
            URL url = new URL(link);
            urlConnection = (HttpURLConnection) url.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    response.append(line);
                }
                String json = response.toString();
                googleDirectionsResponse =  GoogleDirectionsResponse.fromRawJson(json);
            }
        } catch (Exception e) {
            Log.d("Directions", e.getLocalizedMessage());
        }finally {
            urlConnection.disconnect();
            urlConnection = null;
        }
        return googleDirectionsResponse;
    }

}