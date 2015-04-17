package com.trojanow.gui.entity;

import android.os.Bundle;

/**
 * Created by heetae on 4/16/15.
 */
public class Weather {
    public static final String LONGITUDE = "lon";
    public static final String LATITUDE = "lat";

    public static final String DESCRIPTION = "description";
    public static final String TEMP = "temp";

    private Bundle mBundle;

    public Weather() {
        mBundle = new Bundle();
    }

    public Weather(final Bundle bundle) {
        if (bundle == null)
            mBundle = new Bundle();
        else
            mBundle = (Bundle) bundle.clone();
    }

    public Weather(final Weather weather) {
        if (weather == null)
            mBundle = new Bundle();
        else
            mBundle = (Bundle) weather.toBundle().clone();
    }

    public Bundle toBundle() {
        return mBundle;
    }

    public void putLatitude(double latitude) {
        mBundle.putDouble(LATITUDE, latitude);
    }

    public double getLatitude() {
        return mBundle.getDouble(LATITUDE);
    }

    public void putLongitude(double longitude) {
        mBundle.putDouble(LONGITUDE, longitude);
    }

    public double getLongitude() {
        return mBundle.getDouble(LONGITUDE);
    }

    public void putDescription(String description) {
        mBundle.putString(DESCRIPTION, description);
    }

    public String getDescription() {
        return mBundle.getString(DESCRIPTION);
    }

    public void putTemp(String temp) {
        mBundle.putString(TEMP, temp);
    }

    public String getTemp() {
        return mBundle.getString(TEMP);
    }
}
