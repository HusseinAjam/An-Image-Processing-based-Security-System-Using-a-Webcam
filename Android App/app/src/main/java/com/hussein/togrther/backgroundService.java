package com.hussein.togrther;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;


public class backgroundService extends Service
{
    private static final String TAG = "MyService";

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    public void onDestroy()
    {
        Log.d(TAG, "onDestroy");
    }
    @Override
    public void onStart(Intent myintent, int startId)
    {
        Log.d(TAG, "onStart");
        startService(new Intent(getBaseContext(), SendNotificationsService.class));
    }
}
