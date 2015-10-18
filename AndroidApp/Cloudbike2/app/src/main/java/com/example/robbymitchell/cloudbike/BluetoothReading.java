package com.example.robbymitchell.cloudbike;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Joseph on 10/18/15.
 */
public class BluetoothReading {
    public ArrayList<String> bluetoothData;
    public ArrayList<Long> bluetoothDate;

    public BluetoothReading() {
        bluetoothData = new ArrayList<>();
        bluetoothDate = new ArrayList<>();
    }

    public void addToList(String data, Long date) {
        bluetoothData.add(data);
        bluetoothDate.add(date);
    }
}
