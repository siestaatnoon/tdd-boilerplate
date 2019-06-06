package com.cccdlabs.sample.data.storage.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.cccdlabs.sample.data.entity.DoodadEntity;
import com.cccdlabs.sample.data.entity.GizmoEntity;
import com.cccdlabs.sample.data.entity.WidgetEntity;
import com.cccdlabs.sample.data.storage.dao.DoodadDao;
import com.cccdlabs.sample.data.storage.dao.GizmoDao;
import com.cccdlabs.sample.data.storage.dao.WidgetDao;
import com.cccdlabs.tddboilerplate.data.storage.converters.ByteArrayConverter;
import com.cccdlabs.tddboilerplate.data.storage.converters.DateConverter;
import com.cccdlabs.tddboilerplate.data.storage.utils.DatabaseUtils;

/**
 * {@link Room} abstraction for the application database. Note that all tables and
 * {@link androidx.room.TypeConverter}s are set in this class.
 *
 * @author Johnny Spence
 * @version 1.0.0
 */
@Database(
        entities = {
                GizmoEntity.class,
                WidgetEntity.class,
                DoodadEntity.class
        },
        version = 1
)
@TypeConverters({DateConverter.class, ByteArrayConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    protected static final Object LOCK = new Object();

    protected static final Object TEST_LOCK = new Object();

    protected static volatile AppDatabase sInstance = null;

    protected static volatile AppDatabase sTestInstance = null;

    abstract public WidgetDao widgetDao();

    abstract public GizmoDao gizmoDao();

    abstract public DoodadDao doodadDao();

    public static AppDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        DatabaseUtils.getCurrentUserDatabaseName(context)
                ).build();
            }
            return sInstance;
        }
    }

    public static AppDatabase getInstance(Context context, boolean isTest) {
        return getInstance(context, isTest, false);
    }

    public static AppDatabase getInstance(Context context, boolean isTest, boolean refreshInstance) {
        if (! isTest) {
            return getInstance(context);
        }

        synchronized (TEST_LOCK) {
            if (sTestInstance == null || refreshInstance) {
                sTestInstance = Room.inMemoryDatabaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class
                ).allowMainThreadQueries().build();
            }
            return sTestInstance;
        }
    }
}
