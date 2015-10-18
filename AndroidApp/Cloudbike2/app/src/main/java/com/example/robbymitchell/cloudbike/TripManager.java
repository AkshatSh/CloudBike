package com.example.robbymitchell.cloudbike;

/**
 * Created by robbymitchell on 10/17/15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class TripManager extends Activity {

    public LocationManager myLocation;
    public String provider;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_manager);
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //Toast.makeText(getBaseContext(), "Turn on LED", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
