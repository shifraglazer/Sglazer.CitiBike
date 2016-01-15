package com.example.shifra.sglazercitibike;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by student1 on 10/29/2015.
 */
public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        CitiBikeStationDetailFragment fragment = (CitiBikeStationDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_station_detail);
        List<StationBeanList> citiBikeStations = (List<StationBeanList>) intent.getSerializableExtra("STATIONS");
        int position = intent.getIntExtra("POSITION", 0);
        double currentLat = intent.getDoubleExtra("CURRENTLAT", 40.71494807);
        double currentLon = intent.getDoubleExtra("CURRENTLON", -74.00666661);
        fragment.showStationDetail(citiBikeStations, position, currentLat, currentLon);

    }
}
