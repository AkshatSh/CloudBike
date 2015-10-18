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

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by Joseph on 10/17/15.
 */
public class BluetoothManager {
    /**
     * Debugging Tag
     */
    private static final String TAG = "BluetoothManager";

    /**
     * BluetoothAdapter for bluetooth device manipulation
     */
    private BluetoothAdapter bluetoothAdapter;

    /**
     * BluetoothSocket for info communication
     */
    private BluetoothSocket bluetoothSocket;

    /**
     * Stream for outgoing communications
     */
    private OutputStream outputStream;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module
    private static String address = "98:D3:31:70:4F:DA";

    /**
     * Constructor
     */
    public BluetoothManager() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * Creates BluetoothSocket
     * @param device
     * @return
     * @throws IOException
     */
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if(Build.VERSION.SDK_INT >= 10){
            try {
                final Method  m = device.getClass()
                        .getMethod("createInsecureRfcommSocketToServiceRecord",
                                new Class[] { UUID.class });
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection",e);
            }
        }
        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    /**
     * public setter to try connect
     */
    public void connect() {
        Log.d(TAG, "try connect");

        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);

        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.
        try {
            bluetoothSocket = createBluetoothSocket(device);
        } catch (IOException e1) {
            Log.e(TAG, "Socket create failed: " + e1.getMessage() + ".");
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        bluetoothAdapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d(TAG, "Connecting");
        try {
            bluetoothSocket.connect();
            Log.d(TAG, "Connection ok");
        } catch (IOException e) {
            try {
                bluetoothSocket.close();
            } catch (IOException e2) {
                Log.e(TAG, "Unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }

        // Create a data stream so we can talk to server.
        Log.d(TAG, "...Create Socket...");

        // Get OutputStream
        try {
            outputStream = bluetoothSocket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Output stream creation failed:" + e.getMessage() + ".");
        }
    }

    /**
     * Pause Output Stream
     */
    public void pauseOutputStream() {
        Log.d(TAG, "Pause");

        if (outputStream != null) {
            try {
                outputStream.flush();
            } catch (IOException e) {
                Log.e(TAG, "failed to flush output stream: " + e.getMessage() + ".");
            }
        }

        try {
            bluetoothSocket.close();
        } catch (IOException e2) {
            Log.e(TAG, "failed to close socket." + e2.getMessage() + ".");
        }
    }

    /**
     * Check for Bluetooth support post initialization
     */
    private boolean checkBluetoothEnabled() {
        // Check for Bluetooth support and then check to make sure it is turned on
        if(bluetoothAdapter == null) {
            Log.e(TAG, "Bluetooth not supported");
            return false;
        }
        else {
            if (bluetoothAdapter.isEnabled()) {
                Log.d(TAG, "Bluetooth ON");
                return true;
            }
            else {
                // We need to prompt user to turn on Bluetooth!
                return false;
            }
        }
    }

    /**
     * Public void to send data!
     * @param message
     */
    public void sendData(String message) {
        byte[] msgBuffer = message.getBytes();

        Log.d(TAG, "Send data: " + message);

        try {
            outputStream.write(msgBuffer);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
