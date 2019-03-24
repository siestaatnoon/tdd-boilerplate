package com.oscarrrweb.tddboilerplate.data.network.base;

import com.google.gson.JsonObject;
import com.oscarrrweb.tddboilerplate.data.entity.base.Entity;

import java.util.List;
import java.util.Map;

public interface ApiResponse {

    int getCount();

    Entity getEntity();

    List<Entity> getEntityCollection();

    String getError();

    Map<String, String> getHeaders();

    JsonObject getJsonResult();

    int getStatusCode();

    Entity getErrorEntity();

    boolean isSuccess();
}
