package com.cccdlabs.sample.seeder.data;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.sqlite.db.SimpleSQLiteQuery;

import com.cccdlabs.sample.data.entity.DoodadEntity;
import com.cccdlabs.sample.data.entity.GizmoEntity;
import com.cccdlabs.sample.data.entity.WidgetEntity;
import com.cccdlabs.sample.data.storage.dao.DoodadDao;
import com.cccdlabs.sample.data.storage.dao.GizmoDao;
import com.cccdlabs.sample.data.storage.dao.WidgetDao;
import com.cccdlabs.sample.data.storage.database.AppDatabase;

public class DataSource {

    private static final String TAG = DataSource.class.getSimpleName();
    private AppDatabase mDb;

    public DataSource(Context context) {
        mDb = (AppDatabase) AppDatabase.getInstance(context);
    }

    public boolean truncateTables() {
        boolean hasExecuted = false;

        mDb.beginTransaction();
        try {
            mDb.clearAllTables();
            mDb.setTransactionSuccessful();
            hasExecuted = true;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            mDb.endTransaction();
            mDb.close();
        }

        String message = hasExecuted
                ? "Database cleared successully"
                : "Database FAILED to be cleared";
        Log.d(TAG, message);

        return hasExecuted;
    }

    public boolean resetAutoIncrements() {
        boolean hasExecuted = false;

        mDb.beginTransaction();
        try {
            Cursor cursor;
            cursor = mDb.query(new SimpleSQLiteQuery("DELETE FROM sqlite_sequence WHERE name='doodads'"));
            cursor.moveToFirst();
            cursor.close();
            cursor = null;
            Log.d(TAG, "DELETE auto_increment [doodads], count: " + getDeleteCount());

            cursor = mDb.query(new SimpleSQLiteQuery("DELETE FROM sqlite_sequence WHERE name='widgets'"));
            cursor.moveToFirst();
            cursor.close();
            cursor = null;
            Log.d(TAG, "DELETE auto_increment [widgets], count: " + getDeleteCount());

            cursor = mDb.query(new SimpleSQLiteQuery("DELETE FROM sqlite_sequence WHERE name='gizmos'"));
            cursor.moveToFirst();
            cursor.close();
            cursor = null;
            Log.d(TAG, "DELETE auto_increment [gizmos], count: " + getDeleteCount());

            mDb.setTransactionSuccessful();
            hasExecuted = true;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            mDb.endTransaction();
        }

        String message = hasExecuted
                ? "Auto increments reset successully"
                : "Auto increments FAILED reset";
        Log.d(TAG, message);

        return hasExecuted;
    }

    public int insertGizmo(GizmoEntity entity) {
        if (entity == null) {
            return -1;
        }

        GizmoDao dao = mDb.gizmoDao();
        int id = 0;
        try {
            entity.touch();
            id = (int) dao.insert(entity);
        } catch (Exception e) {
            Log.e(TAG, "[" + entity.getUuid() + "] " + e.getMessage());
            return -1;
        }

        return id;
    }

    public int insertWidget(WidgetEntity entity) {
        if (entity == null) {
            return -1;
        }

        WidgetDao dao = mDb.widgetDao();
        int id = 0;
        try {
            entity.touch();
            id = (int) dao.insert(entity);
        } catch (Exception e) {
            Log.e(TAG, "[" + entity.getUuid() + "] " + e.getMessage());
            return -1;
        }

        return id;
    }

    public int insertDoodad(DoodadEntity entity) {
        if (entity == null) {
            return -1;
        }

        DoodadDao dao = mDb.doodadDao();
        int id = 0;
        try {
            entity.touch();
            id = (int) dao.insert(entity);
        } catch (Exception e) {
            Log.e(TAG, "[" + entity.getUuid() + "] " + e.getMessage());
            return -1;
        }

        return id;
    }

    public void close() {
        mDb.close();
    }

    private int getDeleteCount() {
        int deleteCount = -1;
        try {
            Cursor cursor = mDb.query(new SimpleSQLiteQuery("SELECT changes() AS affected_rows"));
            if (cursor != null) {
                if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                    deleteCount = cursor.getInt(cursor.getColumnIndex("affected_rows"));
                }
                cursor.close();
                cursor = null;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return -1;
        }
        return deleteCount;
    }
}
