package com.example.samplearch.di.module;


import com.example.samplearch.ui.view.entry.mocky.contributor.ContributorFragment;
import com.example.samplearch.ui.view.entry.mocky.search.MockyListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class MockyFragmentModule {

    @ContributesAndroidInjector
    abstract MockyListFragment contributeMockyListFragment();

    @ContributesAndroidInjector
    abstract ContributorFragment contributeContributorFragment();
}
