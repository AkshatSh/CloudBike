package com.example.robbymitchell.cloudbike;

import android.location.Location;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
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
import java.util.concurrent.TransferQueue;

public class TripManager extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final String TAG = "MyAwesomeApp";

    private TextView mLocationView;

    private GoogleApiClient mGoogleApiClient;

    private Calendar startTime;

    private LocationRequest mLocationRequest;

    private ArrayList<Location> myLocationData;

    private Handler customHandler = new Handler();

    private BluetoothManager myBluetooth;

    long starter = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private TextView timerValue;

    private BluetoothReading bluetoothReading;

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
                        timerValue = (TextView) findViewById(R.id.chronometer);
                        starter = SystemClock.uptimeMillis();
                        customHandler.postDelayed(updateTimerThread, 0);

                    }
                }
        );

        Button end = (Button) findViewById(R.id.endTrip);
        end.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        bluetoothReading = myBluetooth.getBluetoothReading();
                        moveToJson();
                        mGoogleApiClient.disconnect();
                        TripManager.super.onStop();
                        customHandler.removeCallbacks(updateTimerThread);
                        timerValue.setText("00:00:00");
                        starter = 0L;
                        timeInMilliseconds = 0L;
                        timeSwapBuff = 0L;
                        updatedTime = 0L;


                    }
                }
        );

        myBluetooth = new BluetoothManager();
        myBluetooth.setupRecieve();
        myBluetooth.connectInput();
    }

    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            myBluetooth.setupRecieve();
            myBluetooth.connectInput();

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        myLocationData = new ArrayList<Location>();
        startTime = Calendar.getInstance();
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(200); // Update location every second

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
            map.put("speed", myLocationData.get(i).getSpeed());
            map.put("freeTime", myLocationData.get(i).getTime());
            list.add(map);
        }
        JSONArray something = listmap_to_json_string(list);
        JSONArray something2 = new JSONArray(bluetoothReading.bluetoothData);
        JSONArray something3 = new JSONArray(bluetoothReading.bluetoothDate);



        Map map = new HashMap();
        map.put("bikeData", something);
        map.put("rpmData", something2);
        map.put("rpmTimeStamp", something3);
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

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - starter;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };


    private ArrayList<rpmObj> getRPM(List<String> nums, List<Long> times) {
        boolean seen0 = false;
        int last0index = 0;
        ArrayList<rpmObj> rpms = new ArrayList();
        long lasttime = 0;
        for (int i = 0; i < times.size(); i++) {
            String currNum = nums.get(i);
            if (!seen0 && currNum.equals("0")) {
                seen0 = true;
                last0index = i;
                lasttime = times.get(i);
            } else if (seen0 && currNum.equals("0") && last0index != i - 1) {
                double rpmTemp = 1.0 / (double)(times.get(i) - lasttime) * 1000.0 * 60.0;
                lasttime = times.get(i);
                rpmObj temp = new rpmObj();
                temp.rpm = rpmTemp;
                temp.timeStamp = times.get(i);
                rpms.add(temp);
            } else if (last0index == -1 && currNum.equals("0")) {
                last0index = i;
            }
        }

        return rpms;
    }

    private class rpmObj extends TripManager {
        public long timeStamp;
        public Double rpm;
    }
}
