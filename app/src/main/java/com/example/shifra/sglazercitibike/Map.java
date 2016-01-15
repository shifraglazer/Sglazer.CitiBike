package com.example.shifra.sglazercitibike;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Map {
    private static final long serialVersionUID = 1L;
    private double currentLat;
    private double currentLog;
    private String view;


    public Map(double currentlat, double currentlong) throws IOException {
        view = "satellite";

        this.currentLat = currentlat;
        this.currentLog = currentlong;

        // load initial centerMap image - since initial lat and log is hardcoded,
        // hardcoded initial img to save initial setup time
        //img = ImageIO.read(getClass().getResource("pics/centerMap.png"));
    }


    public void loadImg() throws MalformedURLException {
        URL url = new URL("https://maps.googleapis.com/maps/api/staticmap?center=" + currentLat + "," + currentLog
                + "&size=640x640" + "&maptype=" + view + "&zoom=17"
                + "&key=AIzaSyAirHEsA08agmW9uizDvXagTjWS3mRctPE");
        //new ImgDownloadThread(url, this).start();
    }

    public void updateView(String view) throws MalformedURLException {
        this.view = view.toLowerCase();
        loadImg();
    }

}
