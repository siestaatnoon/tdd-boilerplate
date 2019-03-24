package com.oscarrrweb.tddboilerplate.data.network.base;

import android.support.annotation.NonNull;

import com.oscarrrweb.tddboilerplate.data.entity.base.Entity;

import java.util.Map;

import io.reactivex.Single;

public interface ApiCaller {

    Single<ApiResponse> delete(@NonNull Entity entity, Map<String, String> headers);

    Single<ApiResponse> get(@NonNull Entity entity, Map<String, String> headers);

    Single<ApiResponse> query(@NonNull Entity entity, Map<String, String> params, Map<String, String> headers);

    Single<ApiResponse> post(@NonNull Entity entity, Map<String, String> headers);

    Single<ApiResponse> put(@NonNull Entity entity, Map<String, String> headers);
}
