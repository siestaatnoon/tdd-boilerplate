package com.oscarrrweb.tddboilerplate.data.storage.converters;

import android.arch.persistence.room.TypeConverter;
import android.util.Base64;

public class ByteArrayConverter {

    /**
     *  NOTE: Android Base64 used for backward compatibility (API 23+)
     *  java.util.Base64 is Java 8 an API 26+
     */
    @TypeConverter
    public static byte[] toByteArray(String base64Str) {
        if (base64Str == null) {
            return null;
        }

        return Base64.decode(base64Str, Base64.DEFAULT);
    }

    /**
     *  NOTE: Android Base64 used for backward compatibility (API 23+)
     *  java.util.Base64 is Java 8 an API 26+
     */
    @TypeConverter
    public static String fromBase64String(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
