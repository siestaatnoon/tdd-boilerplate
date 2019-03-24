package com.oscarrrweb.tddboilerplate.data.network.retrofit;

import com.google.gson.JsonObject;
import com.oscarrrweb.tddboilerplate.data.network.base.ApiError;
import com.oscarrrweb.tddboilerplate.data.network.base.ApiResponse;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class RestError extends Exception implements ApiError {

    public int statusCode;
    public byte[] responseBody;
    public Map<String, String> headers;
    private boolean isAuthFailureError;
    private boolean isNetworkError;
    private boolean isNoConnectionError;
    private boolean isTimeoutError;

    public RestError(String message, Throwable cause) {
        super(message, cause);
        setDefaults();
    }

    public RestError(String message) {
        super(message);
        setDefaults();
    }

    public RestError(Throwable cause) {
        super(cause);
        setDefaults();

        if (cause instanceof HttpException) {
            HttpException exception = (HttpException) cause;
            Response raw = exception.response().raw();
            statusCode = raw.code();
            isAuthFailureError = statusCode == RestResponse.HTTP_ERROR_UNAUTHORIZED ||
                    statusCode == RestResponse.HTTP_ERROR_LOCKOUT;
            isNetworkError = ! isAuthFailureError;

            // Set response headers
            Headers rawHeaders = raw.headers();
            Set<String> names = rawHeaders.names();
            for (String name : names) {
                headers.put(name, rawHeaders.get(name));
            }


            // Set response body
            try {
                ResponseBody body = raw.body();
                if (body != null) {
                    String str = body.string();
                    responseBody = str.getBytes();
                }
            } catch (IOException e) {
                // Ignore exception and use defaults for values
            }
        } else if (cause instanceof SocketTimeoutException) {
            isTimeoutError = true;
        } else if (cause instanceof UnknownHostException) {
            isNoConnectionError = true;
        }
    }

    public RestError(ApiResponse response) {
        super(response.getError());
        setDefaults();

        JsonObject json = response.getJsonResult();
        String body = json == null ? "" : json.toString();
        responseBody = body.getBytes();
        statusCode = response.getStatusCode();
        headers = response.getHeaders();

        isAuthFailureError = statusCode == RestResponse.HTTP_ERROR_UNAUTHORIZED ||
                statusCode == RestResponse.HTTP_ERROR_LOCKOUT;
        isNetworkError = statusCode == RestResponse.HTTP_ERROR_SERVER;
    }

    @Override
    public boolean isAuthFailureError()  {
        return isAuthFailureError;
    }

    @Override
    public boolean isNetworkError() {
        return isNetworkError;
    }

    @Override
    public boolean isNoConnectionError() {
        return isNoConnectionError;
    }

    @Override
    public boolean isTimeoutError() {
        return isTimeoutError;
    }

    private void setDefaults() {
        statusCode = RestResponse.HTTP_ERROR_SERVER;
        responseBody = new byte[]{};
        headers = new HashMap<>();
    }
}

