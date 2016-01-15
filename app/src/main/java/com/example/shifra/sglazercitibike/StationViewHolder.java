package com.example.shifra.sglazercitibike;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;


/**
 * Created by student1 on 10/22/2015.
 */
public class StationViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView distance;

    public StationViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.stationName);
        distance = (TextView) itemView.findViewById(R.id.distance);
    }

    public void bind(StationBeanList citiBikeStation) {
        name.setText(citiBikeStation.getStationName());
        distance.setText(String.valueOf(new DecimalFormat("0.00").format(citiBikeStation.getDistance() * 69)));
    }
}
