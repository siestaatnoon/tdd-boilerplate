package com.oscarrrweb.tddboilerplate.data.network.retrofit;

import com.oscarrrweb.tddboilerplate.data.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestClient {

    private static final int TIMEOUT = 30; // seconds
    private static Retrofit sRetrofit;
    private static RestClient sInstance;

    private RestClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client);

        sRetrofit = builder.build();
    }

    public static RestClient getInstance() {
        if (sInstance == null) {
            sInstance = new RestClient();
        }

        return sInstance;
    }

    public <T> T getService(Class<T> service) {
        return sRetrofit.create(service);
    }
}
