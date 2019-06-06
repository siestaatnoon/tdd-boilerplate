package com.cccdlabs.tddboilerplate.data.settings;

import android.content.Context;
import androidx.annotation.NonNull;

import com.cccdlabs.tddboilerplate.data.settings.base.SettingsManager;
import com.cccdlabs.tddboilerplate.domain.settings.GeneralSettings;
import com.cccdlabs.tddboilerplate.R;

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
