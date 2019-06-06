package com.cccdlabs.tddboilerplate.data.storage.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;

import com.cccdlabs.tddboilerplate.data.entity.SampleEntity;
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
                SampleEntity.class
        },
        version = 1
)
@TypeConverters({DateConverter.class, ByteArrayConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    /**
     * Object to use for synchronizing database.
     */
    protected static final Object LOCK = new Object();

    /**
     * Object to use for synchronizing the test database.
     */
    protected static final Object TEST_LOCK = new Object();

    /**
     * Singleton instance of the production database.
     */
    protected static volatile AppDatabase sInstance = null;

    /**
     * Singleton instance of the test database.
     */
    protected static volatile AppDatabase sTestInstance = null;

    // ADD Dao GETTERS BELOW
    // abstract public SampleDao sampleDao();

    /**
     * Returns a singleton instance of the production {@link Room} database.
     *
     * @param context   The Android application context
     * @return          The room database
     */
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

    /**
     * Returns a singleton instance of the production or test {@link Room} database.
     *
     * @param context   The Android application context
     * @param isTest    True to return the test database (for unit or instrumentation tests),
     *                  false for production
     * @return          The room database
     */
    public static AppDatabase getInstance(Context context, boolean isTest) {
        return getInstance(context, isTest, false);
    }

    /**
     * Returns a singleton instance of the production or test {@link Room} database. Allows
     * a parameter to refresh the database instance in use between unit tests, for example.
     *
     * @param context           The Android application context
     * @param isTest            True to return the test database (for unit or instrumentation tests),
     *                          false for production
     * @param refreshInstance   True to refresh the database instance, only for test database
     * @return                  The room database
     */
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

    /*
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };
    */
}
