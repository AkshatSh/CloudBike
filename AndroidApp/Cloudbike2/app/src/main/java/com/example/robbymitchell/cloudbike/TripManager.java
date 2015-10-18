package com.example.robbymitchell.cloudbike;

import android.location.Location;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripManager extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final String TAG = "MyAwesomeApp";

    private TextView mLocationView;

    private GoogleApiClient mGoogleApiClient;

    private Calendar startTime;

    private LocationRequest mLocationRequest;

    private ArrayList<Location> myLocationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.trip_manager);



        Button start = (Button) findViewById(R.id.startBtn);
        start.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        mGoogleApiClient = new GoogleApiClient.Builder(TripManager.this)
                                .addApi(LocationServices.API)
                                .addConnectionCallbacks(TripManager.this)
                                .addOnConnectionFailedListener(TripManager.this)
                                .build();
                        TripManager.super.onStart();
                        mGoogleApiClient.connect();
                    }
                }
        );

        Button pause;

        Button end = (Button) findViewById(R.id.endTrip);
        end.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        moveToJson();
                        mGoogleApiClient.disconnect();
                        TripManager.super.onStop();
                    }
                }
        );
    }


    @Override
    public void onConnected(Bundle bundle) {
        myLocationData = new ArrayList<Location>();
        startTime = Calendar.getInstance();
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // Update location every second

        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "GoogleApiClient connection has been suspend");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "GoogleApiClient connection has failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        myLocationData.add(location);
    }

    public void moveToJson() {
        List list = new ArrayList();
        for (int i = 0; i < myLocationData.size(); i++) {
            Map map = new HashMap();
            map.put("lat", myLocationData.get(i).getLatitude());
            map.put("lng", myLocationData.get(i).getLongitude());
            map.put("freeTime", myLocationData.get(i).getTime());
            list.add(map);
        }
        JSONArray something = listmap_to_json_string(list);
        Map map = new HashMap();
        map.put("bikeData", something);
        map.put("startTime", startTime.getTime().toString());
        Calendar end = Calendar.getInstance();
        long timeElapsed = end.getTime().getTime() - startTime.getTime().getTime();
        map.put("endTime", Calendar.getInstance().getTime().toString());
        map.put("timeElapsed", timeElapsed);
        JSONObject jsonObject = new JSONObject( map );
        BackendManager test = new BackendManager();
        String crap = "";
        try {
            crap = test.post(test.CB_POST_URL, jsonObject);
        } catch (Exception e) {
            Log.i("error", e.getMessage());
        }

    }

    public JSONArray listmap_to_json_string(List<Map<String, Object>> list)
    {
        JSONArray json_arr=new JSONArray();
        for (Map<String, Object> map : list) {
            JSONObject json_obj=new JSONObject();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                try {
                    json_obj.put(key,value);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            json_arr.put(json_obj);
        }
        return json_arr;
    }
}
