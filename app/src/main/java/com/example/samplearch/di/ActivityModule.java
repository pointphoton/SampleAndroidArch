package com.example.samplearch.di;

import com.example.samplearch.ui.view.entry.login.LoginActivity;
import com.example.samplearch.ui.view.tab.MainTabActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityModule {


    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    //@ContributesAndroidInjector
    //abstract MainTabActivity bindMainTabActivity();

}
