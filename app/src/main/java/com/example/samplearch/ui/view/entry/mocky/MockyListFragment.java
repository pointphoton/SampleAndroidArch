package com.example.samplearch.ui.view.entry.mocky;


import android.content.Context;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samplearch.R;
import com.example.samplearch.ui.common.AutoClearedValue;
import com.example.samplearch.ui.common.FragmentDataBindingComponent;
import com.example.samplearch.util.DebugLog;

import dagger.android.support.DaggerFragment;

public class MockyListFragment extends DaggerFragment {


    //DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    // AutoClearedValue<FragmentMockyListBinding> binding;

    public MockyListFragment() {
        DebugLog.write("setRetainInstance(true)");
        setRetainInstance(true);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DebugLog.write();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DebugLog.write();
    }

    /*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        DebugLog.write();
        final View fragmentView = inflater.inflate(R.layout.fragment_mocky_list, container, false);


        return fragmentView;
    }

    */


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DebugLog.write();

        DataBindingUtil.inflate(inflater, R.layout.fragment_mocky_list, container, false);
        // binding = new AutoClearedValue<>(this, dataBinding);
        // return dataBinding.getRoot();
        return null;


    }


}
