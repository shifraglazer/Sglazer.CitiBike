package com.example.shifra.sglazercitibike;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.concurrent.CountDownLatch;

public class AddressAsyncTask extends AsyncTask<Long, String, String> {
    private String address;
    //	private String address2;
    private Gson gson;
    private String lat;
    private String log;
    StationListFragment fragment;

    public AddressAsyncTask(StationListFragment fragment, String address) throws UnsupportedEncodingException {
        this.fragment = fragment;
        this.address = URLEncoder.encode(address, "UTF-8");
        //this.address2 = URLEncoder.encode(address2, "UTF-8");
        gson = new Gson();
    }


    @Override
    protected String doInBackground(Long... params) {
        try {

            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + address
                    + "&key=AIzaSyAirHEsA08agmW9uizDvXagTjWS3mRctPE");
            URLConnection connection = url.openConnection();
            InputStream in = connection.getInputStream();
            Gson gson = new GsonBuilder().create();
            AdrResults info = gson.fromJson(new InputStreamReader(in), AdrResults.class);
            AdrResult[] results = info.getResults();

            for (AdrResult i : results) {
                AdrLocation location = i.getGeometry().getLocation();
                if (location != null) {
                    lat = location.getLat();
                    log = location.getLng();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        double lat1 = Double.parseDouble(lat);
        double log1 = Double.parseDouble(log);
        Log.v("RETRIEVE LOCATION", String.valueOf(lat1 + log1));
        fragment.updateCurrentLocation(lat1, log1);
    }
}