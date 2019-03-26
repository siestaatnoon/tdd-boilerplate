package com.oscarrrweb.tddboilerplate.data.settings;

import android.content.Context;
import androidx.annotation.NonNull;

import com.oscarrrweb.tddboilerplate.R;
import com.oscarrrweb.tddboilerplate.data.settings.base.SettingsManager;
import com.oscarrrweb.tddboilerplate.domain.settings.GeneralSettings;

public class GeneralSettingsManager extends SettingsManager implements GeneralSettings {

    private final String sKeyStringName;

    public GeneralSettingsManager(@NonNull Context context) {
        super(context);
        sKeyStringName = context.getResources().getString(R.string.pref_username_key);
    }

    @Override
    public String getUsernameKey() {
        return sKeyStringName;
    }

    @Override
    public String getUsername() {
        return mSharedPreferences.getString(sKeyStringName, null);
    }

    @Override
    public void setUsername(String screenName) {
        mSharedPreferences.edit().putString(sKeyStringName, screenName).apply();
    }
}
