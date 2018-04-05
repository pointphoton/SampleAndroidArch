package com.example.samplearch.di;

import com.example.samplearch.SampleArchApp;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityModule.class,
        MockyViewModelModule.class,
        MockyNetworkModule.class
})
public interface AppComponent extends AndroidInjector<SampleArchApp>{

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<SampleArchApp>{}

}



