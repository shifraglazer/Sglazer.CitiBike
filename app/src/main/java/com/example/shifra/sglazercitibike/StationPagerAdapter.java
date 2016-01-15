package com.example.shifra.sglazercitibike;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by student1 on 10/29/2015.
 */
public class StationPagerAdapter extends PagerAdapter {
    private List<StationBeanList> citiBikeStations;
    private Context context;
    private double currentLat;
    private double currentLon;

    public StationPagerAdapter(List<StationBeanList> citiBikeStations, double currentLat, double currentLon, Context context) {
        this.citiBikeStations = citiBikeStations;
        this.context = context;
        this.currentLat = currentLat;
        this.currentLon = currentLon;
    }

    @Override
    public int getCount() {
        return citiBikeStations.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View view = inflater.inflate(R.layout.station_pager_item, null);

        TextView name = (TextView) view.findViewById(R.id.stationName);
        TextView availableBikes = (TextView) view.findViewById(R.id.availableBikes);
        // TextView numDocks= (TextView) view.findViewById(R.id.totalDocks);
        TextView availableDocks = (TextView) view.findViewById(R.id.availableDocks);
        TextView distance = (TextView) view.findViewById(R.id.distance);
        ImageView picture = (ImageView) view.findViewById(R.id.map);
        ImageView dockIcon = (ImageView) view.findViewById(R.id.dockIcon);
        ImageView bikeIcon = (ImageView) view.findViewById(R.id.bikeIcon);
        ImageView distanceIcon = (ImageView) view.findViewById(R.id.distanceIcon);
        //TODO icons
        Picasso.with(context).load(R.drawable.bike_dock_icon).into(dockIcon);
        Picasso.with(context).load(R.drawable.bike_icon).into(bikeIcon);
        Picasso.with(context).load(R.drawable.distance_icon).into(distanceIcon);

        picture.setScaleType(ImageView.ScaleType.FIT_XY);
        StationBeanList citiBikeStation = citiBikeStations.get(position);

        name.setText(citiBikeStation.getStationName());

        availableBikes.setText(" " + String.valueOf(citiBikeStation.getAvailableBikes()));
        distance.setText(" " + String.valueOf(new DecimalFormat("0.00").format(citiBikeStation.getDistance() * 69)));
        availableDocks.setText(" " + citiBikeStation.getAvailableDocks());

        String stationLocation = String.valueOf(citiBikeStation.getLatitude()) + "," + String.valueOf(citiBikeStation.getLongitude());
        String url = "https://maps.googleapis.com/maps/api/staticmap?"
                + "&size=640x640" + "&maptype=hybrid" + "&path=color:0x0000ff|weight:5|" + currentLat + "," + currentLon + "|" + stationLocation + "&markers=icon:http://goo.gl/LbZMJG%7C" +
                "%7C" + stationLocation + "&markers=" + currentLat + "," + currentLon
                + "&key=AIzaSyAirHEsA08agmW9uizDvXagTjWS3mRctPE";
        Log.v("URL", "https://maps.googleapis.com/maps/api/staticmap?center=" + stationLocation
                + "&size=1024x1024" + "&maptype=hybrid" + "&markers=size:mid%7Ccolor:red%7C" + stationLocation + "&zoom=17"
                + "&key=AIzaSyAirHEsA08agmW9uizDvXagTjWS3mRctPE");
        Picasso.with(context).load(url).into(picture);
        container.addView(view);
        return view;
    }


}
