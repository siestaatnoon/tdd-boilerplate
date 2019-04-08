package com.oscarrrweb.sandbox.seeder.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.oscarrrweb.sandbox.Constants;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ResUtils {

    private static final String TAG = com.oscarrrweb.sandbox.seeder.utils.ResUtils.class.getSimpleName();

    public static String getRawResourceString(Context context, int resId) {
        InputStream input;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String resString;

        try {
            input = context.getResources().openRawResource(resId);
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }

        try {
            int result = input.read();
            while (result != -1) {
                output.write((byte) result);
                result = input.read();
            }
            resString = output.toString(Constants.CHAR_ENCODING);
            input.close();
            output.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }

        return resString;
    }
}
