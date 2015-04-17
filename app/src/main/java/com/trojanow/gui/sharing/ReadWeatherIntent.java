package com.trojanow.gui.sharing;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by heetae on 4/11/15.
 */
public class ReadWeatherIntent extends CallbackIntent {
    public ReadWeatherIntent(ResultReceiver receiver, Bundle sendingData) {
        super("com.trojanow.sensor.services.action.READ_WEATHER", receiver, sendingData);
    }
}
