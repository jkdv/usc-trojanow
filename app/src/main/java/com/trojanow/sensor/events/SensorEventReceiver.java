package com.trojanow.sensor.events;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.ResultReceiver;
import android.util.Log;

import com.trojanow.sensor.services.EnvironmentSensorService;

/**
 * Created by heetae on 4/11/15.
 */
public class SensorEventReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SensorEventReceiver", "onReceive");
        final String action  = intent.getAction();
        if (EnvironmentSensorService.ACTION_READ_WEATHER.equals(action)) {
            startActionReadWeather(context, intent);
        }
    }

    private void startActionReadWeather(Context context, Intent intent) {
        Log.d("SensorEventReceiver", "startActionReadWeather");
        final ResultReceiver receiver = intent.getParcelableExtra(
                EnvironmentSensorService.EXTRA_RESULT_RECEIVER);
        EnvironmentSensorService.startActionReadWeather(context, receiver);
    }
}
