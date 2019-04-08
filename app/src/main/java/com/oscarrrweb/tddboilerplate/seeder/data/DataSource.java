package com.oscarrrweb.sandbox.seeder.data;

import android.arch.persistence.db.SimpleSQLiteQuery;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.oscarrrweb.sandbox.data.entity.base.AbstractEntity;
import com.oscarrrweb.sandbox.data.entity.tights.Tights;
import com.oscarrrweb.sandbox.data.entity.tights.TightsBrand;
import com.oscarrrweb.sandbox.data.entity.tights.TightsJournal;
import com.oscarrrweb.sandbox.data.entity.tights.TightsStore;
import com.oscarrrweb.sandbox.data.storage.dao.tights.TightsBrandDao;
import com.oscarrrweb.sandbox.data.storage.dao.tights.TightsDao;
import com.oscarrrweb.sandbox.data.storage.dao.tights.TightsJournalDao;
import com.oscarrrweb.sandbox.data.storage.dao.tights.TightsStoreDao;
import com.oscarrrweb.sandbox.data.storage.database.TightsDatabase;
import com.oscarrrweb.sandbox.data.sync.entity.History;
import com.oscarrrweb.sandbox.data.sync.storage.dao.HistoryDao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DataSource {

    private static final String TAG = com.oscarrrweb.sandbox.seeder.data.DataSource.class.getSimpleName();
    private TightsDatabase mDb;

    public DataSource(Context context) {
        mDb = TightsDatabase.getInstance(context);
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
            cursor = mDb.query(new SimpleSQLiteQuery("DELETE FROM sqlite_sequence WHERE name='tights_journal'"));
            cursor.moveToFirst();
            cursor.close();
            cursor = null;
            Log.d(TAG, "DELETE auto_increment [tights_journal], count: " + getDeleteCount());

            cursor = mDb.query(new SimpleSQLiteQuery("DELETE FROM sqlite_sequence WHERE name='tights'"));
            cursor.moveToFirst();
            cursor.close();
            cursor = null;
            Log.d(TAG, "DELETE auto_increment [tights], count: " + getDeleteCount());

            cursor = mDb.query(new SimpleSQLiteQuery("DELETE FROM sqlite_sequence WHERE name='tights_stores'"));
            cursor.moveToFirst();
            cursor.close();
            cursor = null;
            Log.d(TAG, "DELETE auto_increment [tights_stores], count: " + getDeleteCount());

            cursor = mDb.query(new SimpleSQLiteQuery("DELETE FROM sqlite_sequence WHERE name='tights_brands'"));
            cursor.moveToFirst();
            cursor.close();
            cursor = null;
            Log.d(TAG, "DELETE auto_increment [tights_brands], count: " + getDeleteCount());

            cursor = mDb.query(new SimpleSQLiteQuery("DELETE FROM sqlite_sequence WHERE name='sync_history'"));
            cursor.moveToFirst();
            cursor.close();
            cursor = null;
            Log.d(TAG, "DELETE auto_increment [sync_history], count: " + getDeleteCount());

            cursor = mDb.query(new SimpleSQLiteQuery("DELETE FROM sqlite_sequence WHERE name='sync_actions'"));
            cursor.moveToFirst();
            cursor.close();
            cursor = null;
            Log.d(TAG, "DELETE auto_increment [sync_actions], count: " + getDeleteCount());

            mDb.setTransactionSuccessful();
            hasExecuted = true;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            mDb.endTransaction();
            mDb.close();
        }

        String message = hasExecuted
                ? "Auto increments reset successully"
                : "Auto increments FAILED reset";
        Log.d(TAG, message);

        return hasExecuted;
    }

    public int deleteTights(Tights tights) {
        if (tights == null) {
            return -1;
        }

        TightsDao dao = mDb.tightsDao();
        TightsJournalDao journalDao = mDb.tightsJournalDao();
        int count = 0;
        try {
            // first delete journal sattached to tights
            List<TightsJournal> list = journalDao.getByTights(tights.getUuid());
            if (list != null) {
                Object[] objects = list.toArray();
                TightsJournal[] journal = Arrays.copyOf(objects, objects.length, TightsJournal[].class);
                count = journalDao.delete(journal);
                if (count > 0) {
                    for (TightsJournal item : journal) {
                        item.setUpdatedAt(tights.getUpdatedAt());
                        addHistory(History.QUERY_DELETE, item, null);
                    }
                }
            }

            count = dao.delete(tights);
            if (count > 0) {
                int historyId = addHistory(History.QUERY_DELETE, tights, null);
                Log.d(TAG, "History added for " + tights.getName() + ", ID: " + historyId + ", DELETE");
            }
        } catch (SQLException e) {
            Log.e(TAG, "[" + tights.getUuid() + "] " + e.getMessage());
            return -1;
        }

        return count;
    }

    public Tights getTights(String uuid) {
        if (uuid == null) {
            return null;
        }

        TightsDao dao = mDb.tightsDao();
        return dao.fromUuid(uuid);
    }

    public int insertTights(Tights tights) {
        if (tights == null) {
            return -1;
        }

        TightsDao dao = mDb.tightsDao();
        int id = 0;
        try {
            id = (int) dao.insert(tights);
        } catch (SQLException e) {
            Log.e(TAG, "[" + tights.getUuid() + "] " + e.getMessage());
            return -1;
        }

        if (id > 0) {
            int historyId = addHistory(History.QUERY_INSERT, tights, tights.toJson());
            Log.d(TAG, "History added for " + tights.getName() + ", ID: " + historyId + ", INSERT");
        }

        return id;
    }

    public int updateTights(Tights tights, String diff) {
        if (tights == null) {
            return -1;
        }

        TightsDao dao = mDb.tightsDao();
        Tights original = dao.fromUuid(tights.getUuid());
        int count = 0;
        try {
            count = dao.update(tights);
        } catch (SQLException e) {
            Log.e(TAG, "[" + tights.getUuid() + "] " + e.getMessage());
            return -1;
        }

        if (count > 0) {
            int historyId = addHistory(History.QUERY_UPDATE, tights, diff);
            Log.d(TAG, "History added for " + tights.getName() + ", ID: " + historyId + ", UPDATE");
        }

        return count;
    }

    public int insertTightsBrand(TightsBrand tightsBrand) {
        if (tightsBrand == null) {
            return -1;
        }

        TightsBrandDao dao = mDb.tightsBrandDao();
        int id = (int) dao.insert(tightsBrand);
        if (id > 0) {
            int historyId = addHistory(History.QUERY_INSERT, tightsBrand, tightsBrand.toJson());
            Log.d(TAG, "History added for " + tightsBrand.getName() + ", ID: " + historyId + ", INSERT");
        }
        return id;
    }

    public int insertTightsStore(TightsStore tightsStore) {
        if (tightsStore == null) {
            return -1;
        }

        TightsStoreDao dao = mDb.tightsStoreDao();
        int id = (int) dao.insert(tightsStore);
        if (id > 0) {
            int historyId = addHistory(History.QUERY_INSERT, tightsStore, tightsStore.toJson());
            Log.d(TAG, "History added for " + tightsStore.getName() + ", ID: " + historyId + ", INSERT");
        }
        return id;
    }

    public int insertTightsJournal(TightsJournal tightsJournal) {
        if (tightsJournal == null) {
            return -1;
        }

        TightsJournalDao dao = mDb.tightsJournalDao();
        int id = (int) dao.insert(tightsJournal);
        if (id > 0) {
            int historyId = addHistory(History.QUERY_INSERT, tightsJournal, tightsJournal.toJson());
            Log.d(TAG, "History added for " + tightsJournal.getTitle() + ", ID: " + historyId + ", INSERT");
        }
        return id;
    }

    private int addHistory(String queryType, AbstractEntity entity, String data) {
        if (entity == null) {
            return -1;
        }

        Date date = entity.getUpdatedAt();
        HistoryDao dao = mDb.historyDao();
        History history = new History();
        history.setUuid();
        history.setTable(entity.getTableName());
        history.setModelUuid(entity.getUuid());
        history.setQuery(queryType);
        history.setData(data);
        history.setResolved(false);
        history.setCreatedAt(date);
        history.setUpdatedAt(date);
        return (int) dao.insert(history);
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
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
            return -1;
        }
        return deleteCount;
    }
}
