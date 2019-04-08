package com.oscarrrweb.sandbox.seeder.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.oscarrrweb.sandbox.data.entity.base.AbstractEntity;
import com.oscarrrweb.sandbox.data.entity.tights.Tights;
import com.oscarrrweb.sandbox.data.entity.tights.TightsBrand;
import com.oscarrrweb.sandbox.data.entity.tights.TightsJournal;
import com.oscarrrweb.sandbox.data.entity.tights.TightsStore;
import com.oscarrrweb.sandbox.seeder.data.DataSource;
import com.oscarrrweb.sandbox.seeder.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;

public class Populate {

    private static final String TAG = com.oscarrrweb.sandbox.seeder.model.Populate.class.getSimpleName();
    private Context mContext;
    private DataSource mData;

    public Populate(Context context) {
        mContext = context;
        mData = new DataSource(mContext);
    }

    public boolean clear() {
        return mData.truncateTables() && mData.resetAutoIncrements();
    }

    public List<String> tights() {
        ArrayList<String> results = new ArrayList<>();
        int resId = mContext.getResources().getIdentifier("tights", "raw", mContext.getPackageName());
        String json = ResUtils.getRawResourceString(mContext, resId);
        if (json == null) {
            Log.e(TAG, "JSON resource [tights.json] not found");
            return null;
        }

        try {
            List<AbstractEntity> entities = AbstractEntity.fromJsonArray(json, Tights.class);
            for (AbstractEntity entity : entities) {
                Tights tights = (Tights) entity;
                int id = mData.insertTights(tights);
                String message = id == -1
                        ? "Tights: " + tights.getName() + " insert FAILED"
                        : "Tights ID " + id + ": " + tights.getName() + " added";
                results.add(message);
            }
        } catch (JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
        
        return results;
    }

    public List<String> tightsBrand() {
        ArrayList<String> results = new ArrayList<>();
        int resId = mContext.getResources().getIdentifier("tights_brands", "raw", mContext.getPackageName());
        String json = ResUtils.getRawResourceString(mContext, resId);
        if (json == null) {
            Log.e(TAG, "JSON resource [tights_brands.json] not found");
            return null;
        }

        try {
            List<AbstractEntity> entities = AbstractEntity.fromJsonArray(json, TightsBrand.class);
            for (AbstractEntity entity : entities) {
                TightsBrand tightsBrand = (TightsBrand) entity;
                int id = mData.insertTightsBrand(tightsBrand);
                String message = id == -1
                        ? "TightsBrand: " + tightsBrand.getName() + " insert FAILED"
                        : "TightsBrand ID " + id + ": " + tightsBrand.getName() + " added";
                results.add(message);
            }
        } catch (JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
        
        return results;
    }

    public List<String> tightsStore() {
        ArrayList<String> results = new ArrayList<>();
        int resId = mContext.getResources().getIdentifier("tights_stores", "raw", mContext.getPackageName());
        String json = ResUtils.getRawResourceString(mContext, resId);
        if (json == null) {
            Log.e(TAG, "JSON resource [tights_stores.json] not found");
            return null;
        }

        try {
            List<AbstractEntity> entities = AbstractEntity.fromJsonArray(json, TightsStore.class);
            for (AbstractEntity entity : entities) {
                TightsStore tightsStore = (TightsStore) entity;
                int id = mData.insertTightsStore(tightsStore);
                String message = id == -1
                        ? "TightsStore: " + tightsStore.getName() + " insert FAILED"
                        : "TightsStore ID " + id + ": " + tightsStore.getName() + " added";
                results.add(message);
            }
        } catch (JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }

        return results;
    }

    public List<String> tightsJournal() {
        ArrayList<String> results = new ArrayList<>();
        int resId = mContext.getResources().getIdentifier("tights_journal", "raw", mContext.getPackageName());
        String json = ResUtils.getRawResourceString(mContext, resId);
        if (json == null) {
            Log.e(TAG, "JSON resource [tights_journal.json] not found");
            return null;
        }

        try {
            List<AbstractEntity> entities = AbstractEntity.fromJsonArray(json, TightsJournal.class);
            for (AbstractEntity entity : entities) {
                TightsJournal tightsJournal = (TightsJournal) entity;
                int id = mData.insertTightsJournal(tightsJournal);
                String message = id == -1
                        ? "TightsJournal: " + tightsJournal.getTitle() + " insert FAILED"
                        : "TightsJournal ID " + id + ": " + tightsJournal.getTitle() + " added";
                results.add(message);
            }
        } catch (JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }

        return results;
    }
}
