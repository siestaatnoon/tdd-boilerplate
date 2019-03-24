package com.oscarrrweb.tddboilerplate.data.network.base;

public interface ApiError {

    boolean isAuthFailureError();

    boolean isNetworkError();

    boolean isNoConnectionError();

    boolean isTimeoutError();
}
