package com.oscarrrweb.tddboilerplate.data.network.retrofit;

import java.util.Map;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RestService {

    @GET("/{table_name}/row/{uuid}")
    Single<Response<String>> row(@Path("table_name") String tableName, @HeaderMap Map<String, String> headers,
                                 @Path("uuid") String uuid);

    @GET("/{table_name}/query")
    Single<Response<String>> query(@Path("table_name") String tableName, @HeaderMap Map<String, String> headers,
                                   @QueryMap Map<String, String> params);

    @DELETE("/{table_name}/delete/{uuid}")
    Single<Response<String>> delete(@Path("table_name") String tableName, @HeaderMap Map<String, String> headers,
                                    @Path("uuid") String uuid);

    @POST("/{table_name}/insert")
    Single<Response<String>> insert(@Path("table_name") String tableName, @HeaderMap Map<String, String> headers,
                                    @Body RequestBody body);

    @PUT("/{table_name}/update")
    Single<Response<String>> update(@Path("table_name") String tableName, @HeaderMap Map<String, String> headers,
                                    @Body RequestBody body);
}
