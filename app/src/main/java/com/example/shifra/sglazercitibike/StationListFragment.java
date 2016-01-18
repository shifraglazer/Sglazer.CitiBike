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
    private double lonLocation;
    private double miles;
    private String[] stationsLocation;
    private StationBeanList[] station;
    private int numStations;
    private View view;
    private CitiBikeStationAdapter adapter;
    private List<StationBeanList> citiBikeStationList;
    private String closeStations;

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

    public String getCloseStations() {
        return closeStations;
    }

    public void setCloseStations(String closeStations) {
        this.closeStations = closeStations;
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
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
        citiBikeStationList = Arrays.asList(citiBikeStations.getStationBeanList());
        stationsLocation = new String[numStations];
        location.setQueryHint("Enter search location");
        location.setQuery("Goldman Sachs, 200 West Street, Manhattan", true);
        location.setSubmitButtonEnabled(true);
        latLocation = 40.7259432;
        lonLocation = -73.8739765;
        miles = 0.01449275362;
        closeStations = "https://maps.googleapis.com/maps/api/staticmap?size=1024x1024&maptype=hybrid&markers=size:mid%7Ccolor:green%7Clabel:S%7C40.7149787,-74.013012%7C40.7153379,-74.01658354%7C40.71754834,-74.01322069%7C40.71542197,-74.01121978%7C40.711512,-74.015756%7C40.71291224,-74.01020234%7C40.71748752,-74.0104554%7C40.71473993,-74.00910627%7C40.71625008,-74.0091059%7C40.711066,-74.009447%7C40.71870987,-74.0090009%7C40.70834698,-74.01713445%7C40.72043411,-74.01020609%7C40.70905623,-74.01043382%7C40.71450451,-74.00562789%7C40.71911552,-74.00666661%7C40.717571,-74.005549%7C40.70569254,-74.01677685%7C40.70717936,-74.00887308%7C40.70955958,-74.00653609%7C40.70862144,-74.00722156%7C40.71146364,-74.00552427%7C40.72185379,-74.00771779%7C40.71273266,-74.0046073%7C40.70463334,-74.01361706%7C40.72405549,-74.00965965%7C40.70706456,-74.00731853%7C40.70823502,-74.00530063%7C40.7047177,-74.00926027%7C40.70365182,-74.01167797%7C40.72243797,-74.00566443%7C40.71494807,-74.00234482%7C40.70255065,-74.0127234%7C40.7037992,-74.00838676%7C40.70530954,-74.00612572%7C40.71939226,-74.00247214%7C40.701907,-74.013942%7C40.724399,-74.004704%7C40.70355377,-74.00670227%7C40.70122128,-74.01234218%7C40.72165481,-74.00234737%7C40.707873,-74.00167%7C40.71117416,-74.00016545%7C40.71602118,-73.99974372%7C40.728846,-74.008591%7C40.71910537,-73.99973337%7C40.71307916,-73.99851193%7C40.72490985,-74.00154702%7C40.72710258,-74.00297088%7C40.72362738,-73.99949601%7C40.71413089,-73.9970468%7C40.71117444,-73.99682619%7C40.71729,-73.996375%7C40.73172428,-74.00674436%7C40.722103786686,-73.997249007225%7C40.7162469,-74.0334588%7C40.7141454,-74.0335519%7C40.73291553,-74.00711384%7C40.73038599,-74.00214988%7C40.7158155,-73.99422366&key=AIzaSyAirHEsA08agmW9uizDvXagTjWS3mRctPE";
        OnStationSelectedListener listener = (OnStationSelectedListener) getActivity();
        readLocation();
        Log.v("START URL", closeStations);
        Picasso.with(view.getContext()).load("https://maps.googleapis.com/maps/api/staticmap?size=1024x1024&maptype=hybrid&markers=size:mid%7Ccolor:green%7Clabel:S%7C40.7149787,-74.013012%7C40.7153379,-74.01658354%7C40.71754834,-74.01322069%7C40.71542197,-74.01121978%7C40.711512,-74.015756%7C40.71291224,-74.01020234%7C40.71748752,-74.0104554%7C40.71473993,-74.00910627%7C40.71625008,-74.0091059%7C40.711066,-74.009447%7C40.71870987,-74.0090009%7C40.70834698,-74.01713445%7C40.72043411,-74.01020609%7C40.70905623,-74.01043382%7C40.71450451,-74.00562789%7C40.71911552,-74.00666661%7C40.717571,-74.005549%7C40.70569254,-74.01677685%7C40.70717936,-74.00887308%7C40.70955958,-74.00653609%7C40.70862144,-74.00722156%7C40.71146364,-74.00552427%7C40.72185379,-74.00771779%7C40.71273266,-74.0046073%7C40.70463334,-74.01361706%7C40.72405549,-74.00965965%7C40.70706456,-74.00731853%7C40.70823502,-74.00530063%7C40.7047177,-74.00926027%7C40.70365182,-74.01167797%7C40.72243797,-74.00566443%7C40.71494807,-74.00234482%7C40.70255065,-74.0127234%7C40.7037992,-74.00838676%7C40.70530954,-74.00612572%7C40.71939226,-74.00247214%7C40.701907,-74.013942%7C40.724399,-74.004704%7C40.70355377,-74.00670227%7C40.70122128,-74.01234218%7C40.72165481,-74.00234737%7C40.707873,-74.00167%7C40.71117416,-74.00016545%7C40.71602118,-73.99974372%7C40.728846,-74.008591%7C40.71910537,-73.99973337%7C40.71307916,-73.99851193%7C40.72490985,-74.00154702%7C40.72710258,-74.00297088%7C40.72362738,-73.99949601%7C40.71413089,-73.9970468%7C40.71117444,-73.99682619%7C40.71729,-73.996375%7C40.73172428,-74.00674436%7C40.722103786686,-73.997249007225%7C40.7162469,-74.0334588%7C40.7141454,-74.0335519%7C40.73291553,-74.00711384%7C40.73038599,-74.00214988%7C40.7158155,-73.99422366&key=AIzaSyAirHEsA08agmW9uizDvXagTjWS3mRctPE").into(stationMap);
        adapter = new CitiBikeStationAdapter(citiBikeStationList, listener, latLocation, lonLocation);
        location.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                readLocation();


                Log.v("NOTIFIED", "adapter reset");
                Log.v("TEST UPDATE", citiBikeStationList.get(0).getStationName());
                Picasso.with(view.getContext()).load(closeStations).into(stationMap);
                adapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void readLocation() {

        Log.v("CURRENT ADDRESS", location.getQuery().toString());
        try {
            AddressAsyncTask task = new AddressAsyncTask(this, location.getQuery().toString());
            task.execute();
        } catch (UnsupportedEncodingException e) {
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


    public void findClosestStations() {
        StringBuilder locations = new StringBuilder();
        String url = "https://maps.googleapis.com/maps/api/staticmap?size=1024x1024&maptype=hybrid&markers=size:mid%7Ccolor:green%7Clabel:S";
        locations.append(url);
        for (int i = 0; i < numStations; i++) {
            StationBeanList s = station[i];
            stationsLocation[i] = String.valueOf(s.getLatitude()) + "," + String.valueOf(s.getLongitude());
            double distance = getDistance(s.getLatitude(), s.getLongitude());
            s.setDistance(distance);

        }

        Log.v("LENGTH", String.valueOf(locations.toString().length()));
        Log.v("MAP URL", locations.toString());

        citiBikeStationList = Arrays.asList(station);
        Collections.sort(citiBikeStationList, new Comparator<StationBeanList>() {
            @Override
            public int compare(StationBeanList a, StationBeanList b) {
                return Double.valueOf(a.getDistance()).compareTo(b.getDistance());
            }
        });
        for (int i = 0; i < 60; i++) {
            locations.append("%7C");
            locations.append(String.valueOf(citiBikeStationList.get(i).getLatitude()) + "," + String.valueOf(citiBikeStationList.get(i).getLongitude()));
        }
        locations.append("&key=AIzaSyAirHEsA08agmW9uizDvXagTjWS3mRctPE");
        closeStations = locations.toString();
        Log.v("UPDATING", citiBikeStationList.get(0).getStationName());
        Log.v("COUNDOWN", locations.toString());

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
