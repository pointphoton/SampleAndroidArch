package com.example.samplearch;




import com.example.samplearch.di.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class SampleArchApp extends DaggerApplication {

    @SuppressWarnings("unchecked")
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
