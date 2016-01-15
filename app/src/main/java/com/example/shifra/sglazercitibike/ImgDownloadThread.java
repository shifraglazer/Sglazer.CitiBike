package com.example.shifra.sglazercitibike;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.URL;


public class ImgDownloadThread extends Thread {
    private String url;
    private ImageView imageView;
    private Context context;


    public ImgDownloadThread(Context context, String url, ImageView imageView) {
        this.url = url;
        this.context = context;
        this.imageView = imageView;
    }

    @Override
    public void run() {
        Picasso.with(context).load(url).into(imageView);
    }
}