package com.appprova.daryl.facebook;

import android.os.Bundle;

import com.appprova.daryl.Constants;
import com.appprova.daryl.TrackerAdapter;
import com.facebook.appevents.AppEventsLogger;

import java.util.Map;

public class FacebookTracker implements TrackerAdapter {

    private final AppEventsLogger logger;

    public FacebookTracker(AppEventsLogger logger) {
        this.logger = logger;
    }

    private final String SCREEN_PREFIX = "SCREEN: ";

    @Override
    public void logPageView(String name) {
        logger.logEvent(SCREEN_PREFIX + name);
    }

    @Override
    public void logEvent(Map<String, Object> eventData) {
        String eventName = (String) eventData.get(Constants.EVENT_NAME);
        eventData.remove(Constants.EVENT_NAME);

        Bundle bundle = new Bundle();
        for (Map.Entry<String, ?> entry : eventData.entrySet()) {
            if (entry.getValue() != null) {
                bundle.putString(entry.getKey(), entry.getValue().toString());
            }
        }

        this.logger.logEvent(eventName, bundle);
    }

    @Override
    public void setUserProperty(String key, Object value) {

    }

    @Override
    public void logException(Throwable e) {

    }
}
