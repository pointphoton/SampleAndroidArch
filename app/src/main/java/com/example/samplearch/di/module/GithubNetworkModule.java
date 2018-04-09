package com.example.samplearch.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.samplearch.api.GithubService;
import com.example.samplearch.api.LiveDataCallAdapterFactory;
import com.example.samplearch.db.GithubDb;
import com.example.samplearch.db.RepoDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class GithubNetworkModule {


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
    GithubDb provideDb(Context context) {
        return Room.databaseBuilder(context, GithubDb.class,"github.db").build();
    }



    @Singleton @Provides
    RepoDao provideRepoDao(GithubDb db) {
        return db.repoDao();
    }


}
