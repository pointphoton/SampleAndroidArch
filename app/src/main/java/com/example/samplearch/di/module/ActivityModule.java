package com.example.samplearch.di.module;

import com.example.samplearch.ui.view.entry.login.LoginActivity;
import com.example.samplearch.ui.view.entry.mocky.MockyListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {


    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();


    @ContributesAndroidInjector(modules = MockyFragmentModule.class)
    abstract MockyListActivity bindMockyListActivity();



    //@ContributesAndroidInjector
    //abstract MainTabActivity bindMainTabActivity();

}
