package com.appprova.daryl;


import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.answers.LevelEndEvent;
import com.crashlytics.android.answers.LevelStartEvent;

import java.util.Map;

public class CrashlyticsLogTracker implements TrackerAdapter {
    @Override
    public void logPageView(String name) {
        Answers.getInstance().logContentView(new ContentViewEvent().putContentName(name));
    }

    public final static String LEVEL_START = "LevelStart";
    public final static String LEVEL_END = "LevelEnd";

    @Override
    public void logEvent(Map<String, Object> eventData) {
        final String name = (String) eventData.get(Constants.EVENT_NAME);

        if (LEVEL_START.equals(name)) {
            Answers.getInstance().logLevelStart(new LevelStartEvent());
            return;
        }

        if (LEVEL_END.equals(name)) {
            Answers.getInstance().logLevelEnd(new LevelEndEvent());
            return;
        }

        CustomEvent customEvent = new CustomEvent(name);
        final String category = (String) eventData.get(Constants.EVENT_CATEGORY);
        if (category != null) {
            customEvent.putCustomAttribute(Constants.EVENT_CATEGORY, category);
        }
        final String action = (String) eventData.get(Constants.EVENT_ACTION);
        if (action != null) {
            customEvent.putCustomAttribute(Constants.EVENT_ACTION, action);
        }
        final String label = (String) eventData.get(Constants.EVENT_LABEL);
        if (label != null) {
            customEvent.putCustomAttribute(Constants.EVENT_LABEL, label);
        }
        final String campaignUrl = (String) eventData.get(Constants.EVENT_CAMPAIGN_URL);
        if (campaignUrl != null) {
            customEvent.putCustomAttribute(Constants.EVENT_CAMPAIGN_URL, campaignUrl);
        }
        final Long value = (Long) eventData.get(Constants.EVENT_VALUE);
        if (value != null) {
            customEvent.putCustomAttribute(Constants.EVENT_VALUE, value);
        }
        Answers.getInstance().logCustom(customEvent);
    }

    @Override
    public void setUserProperty(String key, Object value) {
        switch (key) {
            case Constants.USER_PROPERTY_EMAIL:
                Crashlytics.setUserEmail(value.toString());
            case Constants.USER_PROPERTY_ID:
                Crashlytics.setUserIdentifier(value.toString());
            case Constants.USER_PROPERTY_NAME:
                Crashlytics.setUserName(value.toString());
        }
    }

    @Override
    public void logException(Throwable e) {
        Crashlytics.logException(e);
    }
}
