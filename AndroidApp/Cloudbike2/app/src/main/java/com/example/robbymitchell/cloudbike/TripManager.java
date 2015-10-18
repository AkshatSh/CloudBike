package com.example.robbymitchell.cloudbike;

/**
 * Created by robbymitchell on 10/17/15.
 */

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;

public class TripManager extends Activity {

    public LocationManager myLocation;
    public String provider;
    final Context context = this;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_manager);
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                myLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                provider = myLocation.getBestProvider(criteria, false);
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Location location = myLocation.getLastKnownLocation(provider);
                    if (location != null) {
                        Log.i("YEA", "Provider " + provider + " has been selected.");
                        onLocationChanged(location);
                    } else {
                        Log.i("TripManager", "Cannot find location.");
                    }
                } else {
                    Log.i("Permission", "Permission not granted.");
                }
                //Toast.makeText(getBaseContext(), "Turn on LED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onLocationChanged(Location location) {

    }

}
