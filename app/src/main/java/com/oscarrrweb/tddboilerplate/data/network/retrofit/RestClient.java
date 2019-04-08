package com.oscarrrweb.tddboilerplate.data.network.retrofit;

import com.oscarrrweb.tddboilerplate.data.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * API client to connect to server resources utilizing a {@link Retrofit} client.
 *
 * @author Johnny Spence
 * @version 1.0.0
 */
public class RestClient {

    /**
     * Default seconds to timeout from server.
     */
    private static final int TIMEOUT = 30; // seconds

    /**
     * {@link Retrofit} client.
     */
    private static Retrofit sRetrofit;

    /**
     * Instance of this class used for singleton.
     */
    private static RestClient sInstance;

    /**
     * Constructor.
     */
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

    /**
     * Returns a singleton instance of this class.
     *
     * @return The RestClient instance
     */
    public static RestClient getInstance() {
        if (sInstance == null) {
            sInstance = new RestClient();
        }

        return sInstance;
    }

    /**
     * Returns the {@link Retrofit} service implementation for API calls.
     *
     * @param service   The Class of the  service interface mapping API endpoints to handler methods
     * @param <T>       Class type of the service class
     * @return          Implementation of the service class
     */
    public <T> T getService(Class<T> service) {
        return sRetrofit.create(service);
    }
}
