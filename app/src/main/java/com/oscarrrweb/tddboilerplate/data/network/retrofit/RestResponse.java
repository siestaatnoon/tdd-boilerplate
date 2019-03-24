package com.oscarrrweb.tddboilerplate.data.network.retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.oscarrrweb.tddboilerplate.data.entity.base.Entity;
import com.oscarrrweb.tddboilerplate.data.network.base.ApiResponse;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public final class RestResponse implements ApiResponse {

    public static final int HTTP_OK = 200;
    public static final int HTTP_ERROR_UNAUTHORIZED = 401;
    public static final int HTTP_ERROR_LOCKOUT = 423;
    public static final int HTTP_ERROR_SERVER = 500;

    private final JsonObject mJsonResult;
    private final Class<? extends Entity> mEntityClass;
    private final boolean mIsSuccess;
    private final String mErrorMsg;
    private final Map<String, String> mHeaders;
    private final int mStatusCode;


    @SuppressWarnings("unchecked")
    public RestResponse(int statusCode, JsonObject jsonResult, Map<String, String> headers, Type entityClass) {
        try {
            mEntityClass = (Class<? extends Entity>) entityClass;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("entityClass parameter must be Class object of AbstractEntity subclass");
        }

        boolean isSuccess = false;
        String errorMessage = "JSON parse error";
        if (jsonResult != null) {
            try {
                if (jsonResult.has("success")) {
                    isSuccess = jsonResult.get("success").getAsBoolean();
                }

                if (jsonResult.has("message")) {
                    errorMessage = jsonResult.get("message").getAsString();
                }
            } catch (JsonSyntaxException e) {
                Timber.e(e.getMessage());
            }
        }

        mStatusCode = statusCode;
        mJsonResult = jsonResult;
        mIsSuccess = isSuccess;
        mHeaders = headers;
        mErrorMsg = mIsSuccess ? null : errorMessage;
    }

    @Override
    public int getCount() {
        if ( ! mIsSuccess) {
            return -1;
        }

        if (mJsonResult.has("count")) {
            try {
                return mJsonResult.get("count").getAsInt();
            } catch (JsonSyntaxException e) {
                Timber.e(e.getMessage());
                return -1;
            }
        }

        return -1;
    }

    @Override
    public Entity getEntity() {
        if ( ! mIsSuccess) {
            return null;
        }

        try {
            if (mJsonResult.has("item")) {
                JsonElement element = mJsonResult.get("item");
                return Entity.fromJson(element, mEntityClass);
            } else {
                return Entity.fromJson(mJsonResult, mEntityClass);
            }
        } catch (JsonSyntaxException e) {
            Timber.e(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Entity> getEntityCollection() {
        if ( ! mIsSuccess) {
            return null;
        }

        if (mJsonResult.has("items")) {
            try {
                JsonArray elements = mJsonResult.getAsJsonArray("items");
                return Entity.fromJsonArray(elements, mEntityClass);
            } catch (JsonSyntaxException e) {
                Timber.e(e.getMessage());
                return null;
            }
        }

        return null;
    }

    public Entity getErrorEntity() {
        if (mIsSuccess) {
            return null;
        }

        try {
            return Entity.fromJson(mJsonResult, mEntityClass);
        } catch (JsonSyntaxException e) {
            Timber.e(e.getMessage());
            return null;
        }
    }

    @Override
    public String getError() {
        return mErrorMsg;
    }

    @Override
    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    @Override
    public JsonObject getJsonResult() {
        return mJsonResult;
    }

    @Override
    public int getStatusCode() {
        return mStatusCode;
    }

    @Override
    public boolean isSuccess() {
        return mIsSuccess;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("mIsSuccess: ").append(mIsSuccess ? "true" : "false").append("\n");
        buffer.append("mStatusCode: ").append(mStatusCode).append("\n");
        buffer.append("mEntityClass: ").append(mEntityClass.getSimpleName()).append("\n");
        buffer.append("mErrorMsg: ").append(mErrorMsg).append("\n");
        buffer.append("mHeaders: ").append(mHeaders == null ? "null" : mHeaders.toString()).append("\n");
        buffer.append("mJsonResult: ").append(mJsonResult == null ? "null" : mJsonResult.toString());
        return buffer.toString();
    }
}
