package com.example.samplearch.di.module;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.samplearch.ui.view.entry.login.LoginViewModel;
import com.example.samplearch.ui.view.entry.mocky.contributor.ContributorFragment;
import com.example.samplearch.ui.view.entry.mocky.contributor.ContributorViewModel;
import com.example.samplearch.ui.view.entry.mocky.search.MockyListViewModel;
import com.example.samplearch.viewmodel.MockyViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MockyViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MockyListViewModel.class)
    abstract ViewModel bindMockyListViewModel(MockyListViewModel mockyListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ContributorViewModel.class)
    abstract ViewModel bindContributorViewModel(ContributorViewModel contributorViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindMockyViewModelFactory(MockyViewModelFactory mockyViewModelFactory);



}
