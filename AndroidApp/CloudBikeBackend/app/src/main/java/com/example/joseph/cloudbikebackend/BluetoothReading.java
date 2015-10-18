package com.example.joseph.cloudbikebackend;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Joseph on 10/18/15.
 */
public class BluetoothReading {
    public ArrayList<String> bluetoothData;
    public ArrayList<Date> bluetoothDate;

    public BluetoothReading() {
    }

    public void addToList(String data, Date date) {
        bluetoothData.add(data);
        bluetoothDate.add(date);
    }
}
