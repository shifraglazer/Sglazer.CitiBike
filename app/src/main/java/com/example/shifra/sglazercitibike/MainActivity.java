package com.example.shifra.sglazercitibike;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnStationSelectedListener {

    private StationListFragment listFragment;
    private CitiBikeStationDetailFragment detailFragment;
    private FragmentManager manager;
    private SharedPreferences preferences;
    private SearchView location;
    //TODO private CitiBikeStationMapFragment mapFragment;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = this.getSharedPreferences("DEFAULT", MODE_PRIVATE);
        manager = getSupportFragmentManager();
        listFragment = (StationListFragment) manager.findFragmentById(R.id.list_fragment);
        detailFragment = (CitiBikeStationDetailFragment) manager.findFragmentById(R.id.fragment_station_detail);
        //TODO mapFragment= (CitiBikeStationMapFragment) manager.findFragmentById(R.id.station_map_fragment);
        location = (SearchView) listFragment.getView().findViewById(R.id.location);

    }

    @Override
    protected void onResume() {
        super.onResume();
        location.setQuery(preferences.getString("ADDRESS", ""), true);
        listFragment.setCloseStations(preferences.getString("MAP URL", ""));
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ADDRESS", location.getQuery().toString());
        editor.putString("MAP URL", listFragment.getCloseStations());
        editor.apply();
    }


    @Override
    public void onSelect(List<StationBeanList> citiBikeStations, int position, double currentLat, double currentLon) {
        if (detailFragment != null) {
            detailFragment.showStationDetail(citiBikeStations, position, listFragment.getLatLocation(), listFragment.getLonLocation());
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("STATIONS", (Serializable) citiBikeStations);
            intent.putExtra("POSITION", position);
            intent.putExtra("CURRENTLAT", currentLat);
            intent.putExtra("CURRENTLON", currentLon);
            this.startActivity(intent);
        }
    }

}
