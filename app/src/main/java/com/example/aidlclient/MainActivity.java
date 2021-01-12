package com.example.aidlclient;

import android.app.Activity;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import java.util.NoSuchElementException;

import android.hardware.vibrator.V1_0.IVibrator;


public class MainActivity extends Activity {

    private static final String TAG = "AidlClient";

    private IVibrator hal = null;

    private TextView nativeTV;
    private Button vibrateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate()");

        nativeTV = findViewById(R.id.nativeTV);
        vibrateButton = findViewById(R.id.button);

        try {
            hal = android.hardware.vibrator.V1_0.IVibrator.getService(true);
            Log.d(TAG, "Got the hal instance");
        } catch (NoSuchElementException e) {
            Log.e(TAG, "Can't get HAL: " + e);
        } catch (RemoteException e) {
            Log.e(TAG, "Can't get HAL: " + e);
        }

        if (hal == null) {
            return;
        }

        vibrateButton.setOnClickListener(v -> {
            try {
                hal.on(1000);
                Log.d(TAG, "The device is vibrating!");
                nativeTV.setText("The device is vibrating!");
            } catch (RemoteException e){
            Log.e(TAG, "Can't set listener: " + e);
        }
        });   
    }
}
