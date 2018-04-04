package com.example.samplearch.di;


import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.samplearch.di.MockyContants.CONTENT_TYPE_LABEL;
import static com.example.samplearch.di.MockyContants.CONTENT_TYPE_VALUE_JSON;

@Module
public class MockyNetworkModule {

    @Provides
    @Singleton
    public Converter.Factory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }


    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
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




}
