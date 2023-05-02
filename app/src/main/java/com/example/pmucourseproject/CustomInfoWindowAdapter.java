package com.example.pmucourseproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by User on 10/2/2017.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void rendowWindowText(Marker marker, View view){

        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        tvTitle.setText(marker.getTitle());

        TextView tvLocation = (TextView) view.findViewById(R.id.location);
        tvLocation.setText(marker.getSnippet());

        ImageView tvImage = (ImageView) view.findViewById(R.id.image);
        int resID = this.mContext.getResources().getIdentifier((String)marker.getTag(), "drawable", mContext.getPackageName());
        tvImage.setImageResource(resID);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}