package com.example.samplearch.di.component;

import android.app.Application;

import com.example.samplearch.SampleArchApp;

import com.example.samplearch.di.module.ActivityModule;
import com.example.samplearch.di.module.AppModule;
import com.example.samplearch.di.module.GithubNetworkModule;
import com.example.samplearch.di.module.MockyNetworkModule;
import com.example.samplearch.di.module.MockyViewModelModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,

        ActivityModule.class
})
public interface AppComponent extends AndroidInjector<SampleArchApp>{

    @SuppressWarnings("unchecked")
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<SampleArchApp>{}


    //void inject(SampleArchApp application);
}



