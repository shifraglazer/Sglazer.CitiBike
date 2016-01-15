package com.example.shifra.sglazercitibike;

import java.util.List;

/**
 * Created by student1 on 12/17/2015.
 */
public interface OnStationSelectedListener {

    void onSelect(List<StationBeanList> citiBikeStations, int position, double currentLat, double currentLon);
}
