package com.example.samplearch.di;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.samplearch.ui.view.entry.login.LoginViewModel;
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
    abstract ViewModelProvider.Factory bindMockyViewModelFactory(MockyViewModelFactory mockyViewModelFactory);



}
