package com.example.joseph.cloudbikebackend;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
=======
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
>>>>>>> a0cce49... BackendManager, Bluetooth Manager started, BandManager completed

/**
 * Created by Joseph on 10/17/15.
 */
public class BluetoothManager {
    /**
     * Debugging Tag
     */
    private static final String TAG = "BluetoothManager";

    /**
<<<<<<< HEAD
     * Recieve message constant
     */
    private final int RECIEVE_MESSAGE = 1;        // Status  for Handler

    /**
     * BluetoothAdapter for bluetooth device manipulation
     */
    private BluetoothAdapter bluetoothAdapter;

    /**
     * BluetoothSocket for info communication
     */
    private BluetoothSocket bluetoothSocket;

    /**
     * StringBuilder
     */
    private StringBuilder stringBuilder;

    /**
     *
     */
    private ConnectedThread mConnectedThread;

    /**
     *
     */
    private Handler handler;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module
    private static String address = "98:D3:31:70:4F:DA";

    /**
     * Constructor
     */
    public BluetoothManager() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        stringBuilder = new StringBuilder();

        setupRecieve();
        connectInput();
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
     *
     */
    public void connectInput() {
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
        Log.d(TAG, "Create Socket");

        mConnectedThread = new ConnectedThread(bluetoothSocket, handler);
        mConnectedThread.start();
    }

    /**
     *
     */
    public void startListen() {
        mConnectedThread.write("1");
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
     * public void to recieve data from arduino
     */
    public void setupRecieve() {
        handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:                                                   /* if receive massage */
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);                 /*create string from bytes array*/
                        stringBuilder.append(strIncom);                                     /*append string*/
                        int endOfLineIndex = stringBuilder.indexOf("\r\n");                 /*determine the end of-line*/
                        if (endOfLineIndex > 0) {                                           /* if end-of-line,*/
                            String sbprint = stringBuilder.substring(0, endOfLineIndex);    /* extract string */
                            stringBuilder.delete(0, stringBuilder.length());                /* and clear */
                        }
                        Log.d(TAG, "String: "+ stringBuilder.toString());
                        break;
                }
            };
        };
    }

    /**
     * Public getter of list of paired devices
     * @return
     */
    public ArrayList<String> getListOfPairedDevices() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        ArrayList<String> s = new ArrayList<String>();
        for (BluetoothDevice bt : pairedDevices) {
            s.add(bt.getName());
        }

        return s;
    }
=======
     * BluetoothAdapter
     */
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;
>>>>>>> a0cce49... BackendManager, Bluetooth Manager started, BandManager completed
}
