package com.trojanow.sensor.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.util.Log;

import com.trojanow.sensor.entity.Weather;
import com.trojanow.sensor.events.ReadWeatherIntent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * EnvironmentSensorService generates environment data that are used for the GUI.
 */
public class EnvironmentSensorService extends IntentService {
    public static final String ACTION_READ_WEATHER = "com.trojanow.sensor.services.action.READ_WEATHER";
    public static final String EXTRA_RESULT_RECEIVER = "android.os.ResultReceiver";

    private Location mLocation;

    /**
     * Starts this service to perform action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionReadWeather(Context context, ResultReceiver receiver) {
        Intent intent = new Intent(context, EnvironmentSensorService.class);
        intent.setAction(ACTION_READ_WEATHER);
        intent.putExtra(EXTRA_RESULT_RECEIVER, receiver);
        context.startService(intent);
    }

    public EnvironmentSensorService() {
        super("EnvironmentSensorService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        String provider = locationManager.getBestProvider(criteria, true);
        locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_READ_WEATHER.equals(action)) {
                final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER);
                handleActionReadWeather(receiver);
            }
        }
    }

    /**
     * Handle action in the provided background thread with the provided
     * parameters.
     */
    private void handleActionReadWeather(ResultReceiver receiver) {
        final int interval = 1000;
        for (int wait = 0; wait < 5000 && mLocation == null; wait += interval) {
            Log.d("EnvironmentSensor", "Waiting for location");
            SystemClock.sleep(interval);
        }
        if (mLocation == null) {
            Bundle bundle = new Bundle();
            bundle.putString("Message", "Network connection or GPS is needed");
            receiver.send(-1, bundle);
            return;
        }
        double longitude = mLocation.getLongitude();
        double latitude = mLocation.getLatitude();
        Weather weather = new Weather();
        weather.putLongitude(longitude);
        weather.putLatitude(latitude);

        Intent intent = new ReadWeatherIntent(
                new ReadWeatherResultReceiver(receiver), weather.toBundle());
        sendBroadcast(intent);
    }

    // Define a listener that responds to location updates
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            mLocation = location;
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        @Override
        public void onProviderEnabled(String provider) {}
        @Override
        public void onProviderDisabled(String provider) {}
    };

    /**
     * Created by heetae on 4/12/15.
     * A callback class to be notified of a result of action related to this class.
     */
    private class ReadWeatherResultReceiver extends ResultReceiver {
        private static final int RESULT_ON_RESPONSE = 1;
        private static final int RESULT_ERROR = -1;
        private static final int MAX_TRIAL = 3;
        private ResultReceiver mReceiver;
        private int trial = 0;

        public ReadWeatherResultReceiver(ResultReceiver receiver) {
            super(null);
            mReceiver = receiver;
        }
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            Bundle bundle = new Bundle();
            if (resultCode == RESULT_ON_RESPONSE) {
                try {
                    String weather = resultData.getString("weather");
                    JSONArray jsonArray = new JSONArray(weather);
                    String description = jsonArray.getJSONObject(0).getString(Weather.DESCRIPTION);

                    String main = resultData.getString("main");
                    JSONObject jsonObject = new JSONObject(main);
                    String temp = jsonObject.getString("temp");
                    temp += "\u2109";

                    bundle.putString(Weather.DESCRIPTION, description);
                    bundle.putString(Weather.TEMP, temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == RESULT_ERROR) {
                if (++trial < MAX_TRIAL) {
                    Log.d("ReadWeatherResult", "Try again");
                    double longitude = mLocation.getLongitude();
                    double latitude = mLocation.getLatitude();
                    Weather weather = new Weather();
                    weather.putLongitude(longitude);
                    weather.putLatitude(latitude);

                    Intent intent = new ReadWeatherIntent(this, weather.toBundle());
                    sendBroadcast(intent);
                }
            }
            mReceiver.send(resultCode, bundle);
        }
    }
}
