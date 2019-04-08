package com.oscarrrweb.tddboilerplate.presentation.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.oscarrrweb.tddboilerplate.R;
import com.oscarrrweb.tddboilerplate.data.settings.DataSettingsManager;
import com.oscarrrweb.tddboilerplate.domain.settings.DataSettings;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceScreen;

public class DataPreferencesFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private DataSettings mDataSyncSettings;

    public DataPreferencesFragment() {
        mDataSyncSettings = new DataSettingsManager(getActivity().getApplicationContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.prefs_data_sync);
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int count = prefScreen.getPreferenceCount();

        for (int i = 0; i < count; i++) {
            Preference preference = prefScreen.getPreference(i);

            if (preference instanceof ListPreference) {
                String value = sharedPreferences.getString(preference.getKey(), null);
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(value);
                if (prefIndex >= 0) {
                    listPreference.setSummary(listPreference.getEntries()[prefIndex]);
                }
            } else if (preference instanceof EditTextPreference) {
                String value = sharedPreferences.getString(preference.getKey(), null);
                preference.setSummary(value);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference == null) {
            return;
        }

        if (key.equals(mDataSyncSettings.getSyncEnabledKey())) {
            boolean value = sharedPreferences.getBoolean(key, false);
            mDataSyncSettings.setSyncEnabled(value);
        } else if (key.equals(mDataSyncSettings.getSyncOnlyWhileChargingKey())) {
            boolean value = sharedPreferences.getBoolean(key, false);
            mDataSyncSettings.setSyncOnlyWhileCharging(value);
        } else if (key.equals(mDataSyncSettings.getSyncWifiOnlyKey())) {
            boolean value = sharedPreferences.getBoolean(key, false);
            mDataSyncSettings.setSyncWifiOnly(value);
        }
    }
}