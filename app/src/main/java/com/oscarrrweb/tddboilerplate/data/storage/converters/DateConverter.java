package com.oscarrrweb.tddboilerplate.data.storage.converters;

import androidx.room.TypeConverter;
import android.util.Log;

import com.oscarrrweb.tddboilerplate.data.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date fromSqlString(String dateStr) {
        if (dateStr == null) {
            return null;
        }

        try {
            return DateUtils.sqlStringToDate(dateStr);
        } catch (ParseException e) {
            Log.e(DateConverter.class.getSimpleName(), e.getMessage());
            return null;
        }
    }

    @TypeConverter
    public static String toSqlString(Date date) {
        if (date == null) {
            return null;
        }
        return DateUtils.dateToSqlString(date);
    }
}
