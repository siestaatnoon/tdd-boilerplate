package com.oscarrrweb.tddboilerplate.data.storage.utils;

import android.content.Context;
import androidx.annotation.NonNull;

import com.oscarrrweb.tddboilerplate.data.Constants;
import com.oscarrrweb.tddboilerplate.data.settings.GeneralSettingsManager;

public class DatabaseUtils {

    public static String getCurrentUserDatabaseName(@NonNull Context context) {
        GeneralSettingsManager settingsManager = new GeneralSettingsManager(context);
        String username = settingsManager.getUsername();
        String nameAppend = username == null || username.equals("") ? "" : "_" + username.toLowerCase();
        return String.format(Constants.APP_DATABASE, nameAppend);
    }
}
