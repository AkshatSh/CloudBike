package com.example.joseph.cloudbikebackend;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import com.example.joseph.cloudbikebackend.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Joseph on 10/17/15.
 */
public class BluetoothManager {
    /**
     * Debugging Tag
     */
    private static final String TAG = "BluetoothManager";

    /**
     * BluetoothAdapter
     */
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;
}
