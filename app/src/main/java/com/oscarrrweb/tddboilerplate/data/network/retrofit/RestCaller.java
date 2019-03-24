package com.oscarrrweb.tddboilerplate.data.network.retrofit;

import android.support.annotation.NonNull;
import android.util.MalformedJsonException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oscarrrweb.tddboilerplate.data.Constants;
import com.oscarrrweb.tddboilerplate.data.entity.base.Entity;
import com.oscarrrweb.tddboilerplate.data.network.base.ApiCaller;
import com.oscarrrweb.tddboilerplate.data.network.base.ApiResponse;
import com.oscarrrweb.tddboilerplate.data.network.utils.NetworkUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import timber.log.Timber;

public class RestCaller implements ApiCaller {

    protected static final String CONTENT_TYPE_POST = "Content-Type: text/json; charset=" + Constants.CHAR_ENCODING;

    protected static final String API_QUERY_ROW = "row";
    protected static final String API_QUERY_ROWS = "query";
    protected static final String API_QUERY_DELETE = "delete";
    protected static final String API_QUERY_INSERT = "insert";
    protected static final String API_QUERY_UPDATE = "update";

    protected Map<String, String> mHeaders;
    protected String mApiKey;
    protected String mAuthToken;
    protected Map<String, String> mQueryParams;
    protected RestService mService;

    public RestCaller(String authToken, String apiKey) {
        RestClient client = RestClient.getInstance();
        mService = client.getService(RestService.class);
        mApiKey = apiKey;
        mAuthToken = authToken;
        mHeaders = new HashMap<>();
        mHeaders.put("Content-Type", "application/json; charset=" + Constants.CHAR_ENCODING);
    }

    @Override
    public Single<ApiResponse> delete(@NonNull Entity entity, Map<String, String> headers) {
        return setObservableRequest(API_QUERY_DELETE, entity, headers);
    }

    @Override
    public Single<ApiResponse> get(@NonNull Entity entity, Map<String, String> headers) {
        return setObservableRequest(API_QUERY_ROW, entity, headers);
    }

    @Override
    public Single<ApiResponse> query(@NonNull Entity entity, Map<String, String> params, Map<String, String> headers) {
        mQueryParams = params;
        return setObservableRequest(API_QUERY_ROWS, entity, headers);
    }

    @Override
    public Single<ApiResponse> post(@NonNull Entity entity, Map<String, String> headers) {
        return setObservableRequest(API_QUERY_INSERT, entity, headers);
    }

    @Override
    public Single<ApiResponse> put(@NonNull Entity entity, Map<String, String> headers) {
        return setObservableRequest(API_QUERY_UPDATE, entity, headers);
    }

    protected void setHeaders(byte[] requestBody, Map<String, String> addHeaders) {
        if (mApiKey != null && mAuthToken != null) {
            String tokenHash = NetworkUtils.getRequestHash(mAuthToken, requestBody);
            if (tokenHash == null) {
                tokenHash = "";
            }

            mHeaders.put(Constants.HEADER_TOKEN, mApiKey);
            mHeaders.put(Constants.HEADER_TOKEN_HASH, tokenHash);
        }

        if (addHeaders != null) {
            mHeaders.putAll(addHeaders);
        }
    }

    protected Single<ApiResponse> setObservableRequest(String method, final Entity entity, final Map<String, String> addHeaders) {
        Single<Response<String>> apiCall = null;
        String tableName = entity.getTableName();
        String body = method.equals(API_QUERY_ROW) || method.equals(API_QUERY_ROWS) || method.equals(API_QUERY_DELETE)
                ? "" :
                entity.toJson(false);
        byte[] bodyBytes = body.getBytes();
        setHeaders(bodyBytes, addHeaders);

        switch(method) {
            case API_QUERY_ROW:
                apiCall = mService.row(tableName, mHeaders, entity.getUuid());
                break;
            case API_QUERY_ROWS:
                apiCall = mService.query(tableName, mHeaders, mQueryParams);
                break;
            case API_QUERY_DELETE:
                apiCall = mService.delete(tableName, mHeaders, entity.getUuid());
                break;
            case API_QUERY_INSERT:
            case API_QUERY_UPDATE:
                RequestBody requestBody = RequestBody.create(MediaType.parse(CONTENT_TYPE_POST), body);
                apiCall = method.equals(API_QUERY_INSERT)
                        ? mService.insert(tableName, mHeaders, requestBody)
                        : mService.update(tableName, mHeaders, requestBody);
                break;
            default:
                Timber.e("Service [" + method + "] not supported");
                return null;
        }

        return getResponseSingle(apiCall, entity);
    }

    protected Single<ApiResponse> getResponseSingle(final Single<Response<String>> apiCall,
                                                    final Entity entity) {
        if (apiCall == null || entity == null) {
            return null;
        }

        return apiCall.flatMap(new Function<Response<String>, Single<ApiResponse>>() {
                    @Override
                    public Single<ApiResponse> apply(Response<String> stringResponse) throws Exception {
                        ApiResponse apiResponse = convertFromReactResponse(stringResponse, entity);
                        return Single.just(apiResponse);
                    }
                });
    }

    private ApiResponse convertFromReactResponse(Response<String> response, Entity entity) {
        if (response == null || entity == null) {
            return null;
        }

        boolean isSuccess = response.isSuccessful();

        String body = response.body();
        if (isSuccess) {
            body = response.body();
        } else {
            ResponseBody errorBody = response.errorBody();
            if (errorBody != null) {
                try {
                    body = errorBody.string();
                } catch (IOException e) {
                    // Ignore exception, error will catch in ApiResponse instantiation
                }
            }
        }

        JsonObject json = null;
        try {
            json = getJsonObject(body);
        } catch (MalformedJsonException e) {
            Timber.e(e);
        }

        Headers rawHeaders = response.raw().headers();
        Map<String, String> headers = new HashMap<>();
        Set<String> names = rawHeaders.names();
        for (String name : names) {
            String value = rawHeaders.get(name);
            headers.put(name, value == null ? "" : value);
        }

        return new RestResponse(
                response.code(),
                json,
                headers,
                entity.getClass()
        );
    }

    private JsonObject getJsonObject(String jsonStr) throws MalformedJsonException {
        if (jsonStr == null) {
            return null;
        }

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonStr);
        if (element.isJsonArray()) {
            // method only returns data objects, not arrays of any kind
            return null;
        }

        return element.getAsJsonObject();
    }
}
