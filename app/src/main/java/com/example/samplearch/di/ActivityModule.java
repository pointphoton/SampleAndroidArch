package com.example.samplearch.di;

import com.example.samplearch.ui.entry.login.LoginActivity;
import com.example.samplearch.ui.tab.MainTabActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {


    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    //@ContributesAndroidInjector
    //abstract MainTabActivity bindMainTabActivity();

}
