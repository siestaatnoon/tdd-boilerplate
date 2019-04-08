package com.oscarrrweb.sandbox.seeder.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.oscarrrweb.sandbox.data.entity.base.AbstractEntity;
import com.oscarrrweb.sandbox.data.entity.tights.Tights;
import com.oscarrrweb.sandbox.data.sync.utils.SyncUtils;
import com.oscarrrweb.sandbox.data.utils.DateUtils;
import com.oscarrrweb.sandbox.seeder.data.DataSource;
import com.oscarrrweb.sandbox.seeder.utils.ResUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Update {

    private static final String TAG = com.oscarrrweb.sandbox.seeder.model.Update.class.getSimpleName();
    private Context mContext;
    private DataSource mData;

    public Update(Context context) {
        mContext = context;
        mData = new DataSource(mContext);
    }

    public List<String> perform() {
        int resId = mContext.getResources().getIdentifier("sync_test_updates", "raw", mContext.getPackageName());
        String jsonStr = ResUtils.getRawResourceString(mContext, resId);
        if (jsonStr == null || jsonStr.equals("")) {
            Log.e(TAG, "JSON resource [sync_test_updates.json] not found or empty");
            return null;
        }

        List<String> results = new ArrayList<>();
        JsonObject json = SyncUtils.toJsonObject(jsonStr);
        Set<Map.Entry<String, JsonElement>> tableMap = json.entrySet();
        for (Map.Entry<String, JsonElement> entry: tableMap) {
            String tableName = entry.getKey();
            JsonObject queries = (JsonObject) entry.getValue();
            Set<Map.Entry<String, JsonElement>> queryMap = queries.entrySet();

            for (Map.Entry<String, JsonElement> entry2: queryMap) {
                String query = entry2.getKey();
                JsonArray arr = (JsonArray) entry2.getValue();
                List<String> res = null;

                switch(query) {
                    case "delete":
                        res = performDeletes(tableName, arr);
                        break;
                    case "insert":
                        res = performInserts(tableName, arr);
                        break;
                    case "update":
                        res = performUpdates(tableName, arr);
                        break;
                }

                if (res != null) {
                    results.addAll(res);
                }
            }
        }

        return results;
    }

    private List<String> performDeletes(String tableName, JsonArray data) {
        if (tableName == null || data == null) {
            return null;
        }

        List<String> results = new ArrayList<>();
        switch(tableName) {
            case "tights":
                for (JsonElement element: data) {
                    JsonObject obj = (JsonObject) element;
                    if (!obj.has("uuid") || !obj.has("updated_at")) {
                        continue;
                    }

                    Tights tights = mData.getTights(obj.get("uuid").getAsString());
                    if (tights != null)  {
                        try {
                            Date date = DateUtils.sqlStringToDate(obj.get("updated_at").getAsString());
                            tights.setUpdatedAt(date);
                        } catch(ParseException e) {
                            tights.touch();
                        }

                        int count = mData.deleteTights(tights);
                        String message = count > 0
                                ? "Tights " + tights.getName() + " deleted"
                                : "Tights " + tights.getName() + " delete FAILED";
                        results.add(message);
                    }

                }
                break;
        }

        return results;
    }

    private List<String> performInserts(String tableName, JsonArray data) {
        if (tableName == null || data == null) {
            return null;
        }

        List<String> results = new ArrayList<>();
        switch(tableName) {
            case "tights":
                for (JsonElement element: data) {
                    try {
                        Tights tights = (Tights) AbstractEntity.fromJson(element, Tights.class);
                        if (tights != null) {
                            int id = mData.insertTights(tights);
                            String message = id > 0
                                    ? "Tights ID: " + id + ", " + tights.getName() + " added"
                                    : "Tights " + tights.getName() + " insert FAILED";
                            results.add(message);
                        }
                    } catch (JsonSyntaxException e) {
                        continue;
                    }
                }
                break;
        }

        return results;
    }

    private List<String> performUpdates(String tableName, JsonArray data) {
        if (tableName == null || data == null) {
            return null;
        }

        List<String> results = new ArrayList<>();
        Tights tights = null;
        switch(tableName) {
            case "tights":
                for (JsonElement element: data) {
                    JsonObject obj = (JsonObject) element;
                    if (!obj.has("uuid")) {
                        continue;
                    }

                    String uuid = obj.get("uuid").getAsString();
                    if (tights == null || !tights.getUuid().equals(uuid)) {
                        tights = mData.getTights(uuid);
                        if (tights == null) {
                            continue;
                        }
                    }

                    String diff = element.toString();
                    tights = (Tights) SyncUtils.entityMerge(tights, diff);
                    int count = mData.updateTights(tights, diff);
                    String message = count > 0
                            ? "Tights ID: " + tights.getId() + ", " + tights.getName() + " updated"
                            : "Tights " + tights.getName() + " update FAILED";
                    results.add(message);
                }
                break;
        }

        return results;
    }
}
