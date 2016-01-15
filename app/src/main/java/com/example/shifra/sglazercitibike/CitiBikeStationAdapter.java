package com.example.shifra.sglazercitibike;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by student1 on 10/22/2015.
 */
public class CitiBikeStationAdapter extends RecyclerView.Adapter<StationViewHolder> {

    private List<StationBeanList> citiBikeStations;
    private OnStationSelectedListener listener;
    private double currentLat;
    private double currentLon;

    public CitiBikeStationAdapter(List<StationBeanList> citiBikeStations, OnStationSelectedListener listener,
                                  double currentLat, double currentLon) {
        this.citiBikeStations = citiBikeStations;
        this.listener = listener;
        this.currentLat = currentLat;
        this.currentLon = currentLon;
    }

    @Override
    public StationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_list_item, parent, false);
        return new StationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StationViewHolder holder, final int position) {
        holder.bind(citiBikeStations.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onSelect(citiBikeStations, position, currentLat, currentLon);
            }
        });
    }


    @Override
    public int getItemCount() {
        return citiBikeStations.size();
    }

    public void updateCurrentLocation(double latLocation, double lonLocation) {
        this.currentLat = latLocation;
        this.currentLon = lonLocation;
    }
}
