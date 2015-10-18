package com.example.joseph.cloudbikebackend;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.BandException;
import com.microsoft.band.BandIOException;
import com.microsoft.band.BandInfo;
import com.microsoft.band.BandPendingResult;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.UserConsent;
import com.microsoft.band.sensors.BandHeartRateEvent;
import com.microsoft.band.sensors.BandHeartRateEventListener;
import com.microsoft.band.sensors.HeartRateConsentListener;
import com.microsoft.band.sensors.HeartRateQuality;

import java.lang.ref.WeakReference;

/**
 * Created by Joseph on 10/17/15.
 *
 * Class to manage all bluetooth interactions including with the Microsoft Band and Arduino
 */
public class MicrosoftBandManager implements HeartRateConsentListener {
    /**
     * Debugging Tag
     */
    private static final String TAG = "MicrosoftBandManager";

    /**
     * Microsoft Band client
     */
    private BandClient client;

    /**
     * List of Bands paired to the app
     */
    private BandInfo[] pairedBands;

    /**
     * Activity used to instantiate this Manager
     */
    private Activity activity;

    /**
     * Boolean to track if band had successfully connected
     */
    private boolean connectedBandClient;

    /**
     * Event listener to track when heart rate has changed
     */
    private BandHeartRateEventListener mHeartRateEventListener;

    /**
     * Rate of HeartRate
     */
    private int heartRate;

    /**
     * Quality of Reading from sensor
     */
    private HeartRateQuality heartRateQuality;

    /**
     * Constructor
     * @param _activity activity of the instantiating class
     */
    public MicrosoftBandManager(Activity _activity) {
        activity = _activity;
        mHeartRateEventListener = new BandHeartRateEventListener() {
            @Override
            public void onBandHeartRateChanged(BandHeartRateEvent event) {
                // update values from reading
                heartRate = event.getHeartRate();
                heartRateQuality = event.getQuality();
            }
        };
    }

    /**
     * Public void to connect to band
     */
    public void connectToBand() {
        pairedBands = BandClientManager.getInstance().getPairedBands();
        client = BandClientManager.getInstance().create(activity, pairedBands[0]);
        if(client.getSensorManager().getCurrentHeartRateConsent() != UserConsent.GRANTED) {
            // user hasn’t consented, request consent
            // the calling class is an Activity and implements HeartRateConsentListener
            client.getSensorManager().requestHeartRateConsent(activity, this);
        }
        new HeartRateSubscriptionTask().execute();
        // Note: The BandPendingResult.await() method must be called from a background thread.
        // An exception will be thrown if called from the UI thread.
        BandPendingResult<ConnectionState> pendingResult = client.connect();
        try {
            ConnectionState state = pendingResult.await();
            if(state == ConnectionState.CONNECTED) {
                // do work on success
                connectedBandClient = true;
            }
            else {
                // do work on failure
                connectedBandClient = false;
            }
        }
        catch(InterruptedException ex) {
            // handle InterruptedException
            Log.e(TAG, ex.getMessage());
        }
        catch(BandException ex) {
            // handle BandException
            Log.e(TAG, ex.getMessage());
        }
    }

    /**
     * Overriden listening method to track when user gives consent
     * @param consentGiven
     */
    @Override
    public void userAccepted(boolean consentGiven) {
        // handle user's heart rate consent decision
        // check current user heart rate consent
        if(client.getSensorManager().getCurrentHeartRateConsent() != UserConsent.GRANTED) {
            // user hasn’t consented, request consent
            // the calling class is an Activity and implements HeartRateConsentListener
            client.getSensorManager().requestHeartRateConsent(activity, this);
        }
        mHeartRateEventListener = new BandHeartRateEventListener() {
            @Override
            public void onBandHeartRateChanged(BandHeartRateEvent event) {
                // update values from reading
                heartRate = event.getHeartRate();
                heartRateQuality = event.getQuality();
            }
        };
    };

    /**
     * Public getter to see if band is connected
     * @return
     */
    public boolean getConnectedBandClient() {
        return connectedBandClient;
    }

    private class HeartRateSubscriptionTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                if (getConnectedBandClient()) {
                    if (client.getSensorManager().getCurrentHeartRateConsent() == UserConsent.GRANTED) {
                        client.getSensorManager().registerHeartRateEventListener(mHeartRateEventListener);
                    } else {
                        Log.i(TAG, "You have not given this application consent to access heart rate data yet."
                                + " Please press the Heart Rate Consent button.\n");
                    }
                } else {
                    Log.i(TAG, "Band isn't connected. Please make sure bluetooth is on and the band is in range.\n");
                }
            } catch (BandException e) {
                String exceptionMessage = "";
                switch (e.getErrorType()) {
                    case UNSUPPORTED_SDK_VERSION_ERROR:
                        exceptionMessage = "Microsoft Health BandService doesn't support your SDK Version. Please update to latest SDK.\n";
                        break;
                    case SERVICE_ERROR:
                        exceptionMessage = "Microsoft Health BandService is not available. Please make sure Microsoft Health is installed and that you have the correct permissions.\n";
                        break;
                    default:
                        exceptionMessage = "Unknown error occured: " + e.getMessage() + "\n";
                        break;
                }
                Log.e(TAG, exceptionMessage);

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }
    }

    /**
     * Public setter to start listening to the heart rate sensor
     */
    public void startListening() {
        try {
            // register the listener
            client.getSensorManager().registerHeartRateEventListener(mHeartRateEventListener);
        } catch(BandIOException e) {
            e.printStackTrace();
        } catch (BandException e) {
            e.printStackTrace();
        }
    }

    /**
     * public setter to stop listening to the heart rate sensor
     */
    public void stopListening() {
        try {
            // unregister the listener
            client.getSensorManager().unregisterHeartRateEventListener(mHeartRateEventListener);
        } catch(BandIOException ex) {
            // handle BandException
        }
    }

    /**
     * integer of heart rate
     * @return
     */
    public int getHeartRate() {
        return heartRate;
    }

    /**
     * HeartRateQuality
     * @return
     */
    public HeartRateQuality getHeartRateQuality() {
        return heartRateQuality;
    }

}
