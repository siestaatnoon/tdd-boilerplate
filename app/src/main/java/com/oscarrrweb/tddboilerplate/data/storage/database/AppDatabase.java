package com.oscarrrweb.tddboilerplate.data.storage.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.oscarrrweb.tddboilerplate.data.entity.sample.Gizmo;
import com.oscarrrweb.tddboilerplate.data.entity.sample.Doodad;
import com.oscarrrweb.tddboilerplate.data.entity.sample.Widget;
import com.oscarrrweb.tddboilerplate.data.storage.converters.ByteArrayConverter;
import com.oscarrrweb.tddboilerplate.data.storage.converters.DateConverter;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.GizmoDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.DoodadDao;
import com.oscarrrweb.tddboilerplate.data.storage.dao.sample.WidgetDao;
import com.oscarrrweb.tddboilerplate.data.storage.utils.DatabaseUtils;

@Database(
        entities = {
                Gizmo.class,
                Widget.class,
                Doodad.class
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
