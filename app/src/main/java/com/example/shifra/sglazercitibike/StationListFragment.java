package com.example.shifra.sglazercitibike;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

public class StationListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ImageView stationMap;
    private TextView text;
    private SearchView location;
    private Button goButton;

    public double getLatLocation() {
        return latLocation;
    }

    public void setLatLocation(double latLocation) {
        this.latLocation = latLocation;
    }

    private double latLocation;

    public double getLonLocation() {
        return lonLocation;
    }

    public void setLonLocation(double lonLocation) {
        this.lonLocation = lonLocation;
    }

    private double lonLocation;
    private double miles;
    private String[] stationsLocation;
    private StationBeanList[] station;
    private int numStations;
    private View view;
    private CitiBikeStationAdapter adapter;
    private List<StationBeanList> citiBikeStationList;
    private String closeStations;
    private CountDownLatch countDownLatch;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(layoutManager);
        InputStream in = getResources().openRawResource(R.raw.stations);
        stationMap = (ImageView) view.findViewById(R.id.stationMap);
        stationMap.setScaleType(ImageView.ScaleType.FIT_XY);
        location = (SearchView) view.findViewById(R.id.location);

        text = (TextView) view.findViewById(R.id.text);
        text.setText("Citi Bike Stations");
        Gson gson = new GsonBuilder().create();

        CitiBikeStationList citiBikeStations = gson.fromJson(new InputStreamReader(in), CitiBikeStationList.class);
        numStations = citiBikeStations.getStationBeanList().length;
        station = citiBikeStations.getStationBeanList();
        stationsLocation = new String[numStations];
        location.setQueryHint("Enter search location");
        location.setQuery("1602 Ave J,Brooklyn", true);
        location.setSubmitButtonEnabled(true);
        location.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                readLocation();
                adapter.notifyDataSetChanged();
                Log.v("NOTIFIED", "adapter reset");
                Log.v("TEST UPDATE", citiBikeStationList.get(0).getStationName());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        latLocation = 40.758923863777;
        lonLocation = -73.962262272835;
        readLocation();
        miles = 0.01449275362;
        readLocation();
        downloadStationsMap();
        OnStationSelectedListener listener = (OnStationSelectedListener) getActivity();
        findClosestStations();
        adapter = new CitiBikeStationAdapter(citiBikeStationList, listener, latLocation, lonLocation);
        Log.v("START URL", closeStations);
        Picasso.with(view.getContext()).load(closeStations).into(stationMap);
        recyclerView.setAdapter(adapter);
    }

    public void readLocation() {
        countDownLatch = new CountDownLatch(1);
        updateSearch(location.getQuery().toString());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public double getDistance(double lat, double lon) {
        double distance = Math.sqrt(Math.pow(lat - latLocation, 2) + Math.pow(lon - lonLocation, 2));
        return distance;
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_station_list, container, false);


    }

    public void downloadStationsMap() {
        StringBuilder locations = new StringBuilder();
        String url = "https://maps.googleapis.com/maps/api/staticmap?size=1024x1024&maptype=hybrid&markers=size:mid%7Ccolor:green%7Clabel:S";
        locations.append(url);
        for (int i = 0; i < numStations; i++) {
            StationBeanList s = station[i];
            stationsLocation[i] = String.valueOf(s.getLatitude()) + "," + String.valueOf(s.getLongitude());
            double distance = getDistance(s.getLatitude(), s.getLongitude());
            s.setDistance(distance);
            if (distance < miles) {
                locations.append("%7C");
                locations.append(String.valueOf(s.getLatitude()) + "," + String.valueOf(s.getLongitude()));
            }

        }

        locations.append("&key=AIzaSyAirHEsA08agmW9uizDvXagTjWS3mRctPE");
        closeStations = locations.toString();
        Log.v("LENGTH", String.valueOf(locations.toString().length()));
        Log.v("MAP URL", locations.toString());



    }

    public void updateSearch(String location) {
        Log.v("CURRENT ADDRESS", location);
        try {

            AddressThread thread = new AddressThread(this, location, countDownLatch);
            thread.start();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void findClosestStations() {
        downloadStationsMap();
        citiBikeStationList = Arrays.asList(station);
        Collections.sort(citiBikeStationList, new Comparator<StationBeanList>() {
            @Override
            public int compare(StationBeanList a, StationBeanList b) {
                return Double.valueOf(a.getDistance()).compareTo(b.getDistance());
            }
        });
        Log.v("UPDATING", citiBikeStationList.get(0).getStationName());

        countDownLatch.countDown();

    }

    public void updateCurrentLocation(double currentLat, double currentLog) {
        this.latLocation = currentLat;
        this.lonLocation = currentLog;
        findClosestStations();
        if (adapter != null) {
            adapter.updateCurrentLocation(latLocation, lonLocation);
        }
    }
}
