package com.oscarrrweb.tddboilerplate.data.storage.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;

import com.oscarrrweb.tddboilerplate.data.entity.sample.DoodadEntity;
import com.oscarrrweb.tddboilerplate.data.entity.sample.GizmoEntity;
import com.oscarrrweb.tddboilerplate.data.entity.sample.WidgetEntity;
import com.oscarrrweb.tddboilerplate.data.storage.converters.ByteArrayConverter;
import com.oscarrrweb.tddboilerplate.data.storage.converters.DateConverter;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.GizmoDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.DoodadDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.WidgetDao;
import com.oscarrrweb.tddboilerplate.data.storage.utils.DatabaseUtils;

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

    private static final Object LOCK = new Object();

    private static final Object TEST_LOCK = new Object();

    private static volatile AppDatabase sInstance = null;

    private static volatile AppDatabase sTestInstance = null;

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
        if (! isTest) {
            return getInstance(context);
        }

        synchronized (TEST_LOCK) {
            if (sTestInstance == null) {
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
