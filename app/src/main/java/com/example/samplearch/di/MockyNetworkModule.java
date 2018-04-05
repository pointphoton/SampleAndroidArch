package com.example.samplearch.di;


import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.samplearch.di.MockyContants.*;

@Module
public class MockyNetworkModule {



    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    public Converter.Factory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }


    @Provides
    @Singleton
    public CallAdapter.Factory provideRxJavaCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }


    @Singleton
    @Provides
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return logging;
    }

    @Singleton
    @Provides
    @Named("headerInterceptor")
    public Interceptor provideDefaultHeaderInterceptor(){
        return chain -> {
            Request request = chain.request();
            request.newBuilder()
                    .header(CONTENT_TYPE_LABEL,CONTENT_TYPE_VALUE_JSON)
                    .build();
            return chain.proceed(request);
        };
    }

    @Provides
    @Singleton
    public Cache provideCache(@Named("cacheDir") File cacheDir) {
        Cache cache = null;

        try {
            cache = new Cache(new File(cacheDir.getPath(), HTTP_CACHE_PATH), CACHE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cache;
    }


    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache, @Named
            ("isDebug") boolean isDebug , @Named("headerInterceptor") Interceptor headerInterceptor) {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        if (isDebug) {
            okHttpClient.addInterceptor(loggingInterceptor); //todo check for uploading
        }

        okHttpClient.addInterceptor(headerInterceptor)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT,TimeUnit.SECONDS)
                .cache(cache);
        return okHttpClient.build();
    }


    @Provides
    @Singleton
    public Retrofit provideRetrofit(Converter.Factory converterFactory, CallAdapter.Factory
            callAdapterFactory, OkHttpClient okHttpClient ) {
        return new Retrofit.Builder()
                .baseUrl(MOCKY_BASE_URL)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(okHttpClient)
                .build();
    }

}
