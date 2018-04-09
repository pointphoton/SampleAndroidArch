package com.example.samplearch.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.samplearch.BuildConfig;
import com.example.samplearch.SampleArchApp;
import com.example.samplearch.api.GithubService;
import com.example.samplearch.api.LiveDataCallAdapterFactory;
import com.example.samplearch.db.GithubDb;
import com.example.samplearch.db.RepoDao;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes ={    MockyViewModelModule.class , GithubNetworkModule.class })
public abstract class AppModule {


    @Binds
    abstract Context provideContext(SampleArchApp application);


    /*
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
*/

    /*
    @Singleton
    @Provides
    GithubService provideGithubService() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(GithubService.class);
    }

    @Singleton @Provides
    GithubDb provideDb(Application application) {
        return Room.databaseBuilder(application, GithubDb.class,"github.db").build();
    }



    @Singleton @Provides
    RepoDao provideRepoDao(GithubDb db) {
        return db.repoDao();
    }

    */
}
