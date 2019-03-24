package com.oscarrrweb.tddboilerplate.data.settings.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceManager;

abstract public class SettingsManager {

    protected final SharedPreferences mSharedPreferences;

    public SettingsManager(@NonNull Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
