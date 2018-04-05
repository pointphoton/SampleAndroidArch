package com.example.samplearch.di;

import android.app.Application;
import android.content.Context;

import com.example.samplearch.BuildConfig;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {

    @Binds
    abstract Context provideContext(Application application);


    @Provides
    @Singleton
    @Named("cacheDir")
    static File provideCacheDir(Context context) {
        return context.getCacheDir();
    }

    @Provides
    @Singleton
    @Named("isDebug")
    static Boolean provideIsDebug() {
        return BuildConfig.DEBUG;
    }
}
