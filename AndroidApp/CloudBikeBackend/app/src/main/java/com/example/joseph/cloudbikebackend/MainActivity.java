package com.example.joseph.cloudbikebackend;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    private String TAG = "MainActivity";
    private int heartRate = 0;
    private MicrosoftBandManager microsoftBandManager;
    private BluetoothManager bluetoothManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        microsoftBandManager = new MicrosoftBandManager(this);
        bluetoothManager = new BluetoothManager();

//        ArrayList<String> devicesConnected = bluetoothManager.getListOfPairedDevices();
//        for(int i = 0; i < devicesConnected.size(); i++) {
//            Log.i(TAG, devicesConnected.get(i));
//        }

        new LongOperation().execute("");
    }

    @Override
    public void onClick(View v) {

    }

    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            microsoftBandManager.connectToBand();
            microsoftBandManager.startListening();
//            bluetoothManager.setupRecieve();
//            bluetoothManager.connectInput();

            while(heartRate == 0) {
                heartRate = microsoftBandManager.getHeartRate();
            }

            while(heartRate < 80) {
                heartRate = microsoftBandManager.getHeartRate();
                Log.i(TAG, "heartrate: " + heartRate);
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}

    }



}
