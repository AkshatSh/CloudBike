package com.example.robbymitchell.cloudbike;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Joseph on 10/18/15.
 */
public class BluetoothReading {
    public ArrayList<String> bluetoothData;
    public ArrayList<Long> bluetoothDate;

    public BluetoothReading(List<String> data, List<Long> dates) {
        bluetoothData = (ArrayList<String>)data;
        bluetoothDate = (ArrayList<Long>)dates;
    }
}
