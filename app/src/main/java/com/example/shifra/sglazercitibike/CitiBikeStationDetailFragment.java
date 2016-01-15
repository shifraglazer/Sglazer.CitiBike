package com.example.shifra.sglazercitibike;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shifra.sglazercitibike.StationPagerAdapter;

import java.util.List;

/**
 * Created by student1 on 12/10/2015.
 */
public class CitiBikeStationDetailFragment extends Fragment {

    private ViewPager viewPager;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);


    }

    public void showStationDetail(List<StationBeanList> citiBikeStations, int position, double currentLat, double currentLon) {
        StationPagerAdapter adapter = new StationPagerAdapter(citiBikeStations, currentLat, currentLon, this.getActivity());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_station_detail, container, false);
    }
}
