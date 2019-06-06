package com.cccdlabs.sample.data.settings;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.cccdlabs.sample.domain.settings.DataSettings;
import com.cccdlabs.tddboilerplate.R;
import com.cccdlabs.tddboilerplate.data.settings.base.SettingsManager;

public class DataSettingsManager extends SettingsManager implements DataSettings {

    private final String sKeySyncEnabled;
    private final String sKeySyncOnlyWhileCharging;
    private final String sKeySyncWifiOnly;

    public DataSettingsManager(@NonNull Context context) {
        super(context);
        Resources resources = context.getResources();
        sKeySyncEnabled = resources.getString(R.string.pref_sync_enabled_key);
        sKeySyncOnlyWhileCharging = resources.getString(R.string.pref_sync_only_while_charging_key);
        sKeySyncWifiOnly = resources.getString(R.string.pref_sync_wifi_only_key);
    }

    @Override
    public String getSyncWifiOnlyKey() {
        return sKeySyncWifiOnly;
    }

    @Override
    public boolean isSyncWifiOnly() {
        return mSharedPreferences.getBoolean(sKeySyncWifiOnly, false);
    }

    @Override
    public void setSyncWifiOnly(boolean isSyncWifiOnly) {
        mSharedPreferences.edit().putBoolean(sKeySyncWifiOnly, isSyncWifiOnly).apply();
    }

    @Override
    public String getSyncOnlyWhileChargingKey() {
        return sKeySyncOnlyWhileCharging;
    }

    @Override
    public boolean isSyncOnlyWhileCharging() {
        return mSharedPreferences.getBoolean(sKeySyncOnlyWhileCharging, false);
    }

    @Override
    public void setSyncOnlyWhileCharging(boolean isSyncOnlyWhileCharging) {
        mSharedPreferences.edit().putBoolean(sKeySyncOnlyWhileCharging, isSyncOnlyWhileCharging).apply();
    }

    @Override
    public String getSyncEnabledKey() {
        return sKeySyncEnabled;
    }

    @Override
    public boolean isSyncEnabled() {
        return mSharedPreferences.getBoolean(sKeySyncEnabled, false);
    }

    @Override
    public void setSyncEnabled(boolean isSyncEnabled) {
        mSharedPreferences.edit().putBoolean(sKeySyncEnabled, isSyncEnabled).apply();
    }
}
