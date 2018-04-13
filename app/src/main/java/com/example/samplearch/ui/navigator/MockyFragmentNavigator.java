package com.example.samplearch.ui.navigator;

import android.support.v4.app.FragmentManager;

import com.example.samplearch.R;
import com.example.samplearch.ui.view.entry.mocky.MockyListActivity;
import com.example.samplearch.ui.view.entry.mocky.contributor.ContributorFragment;
import com.example.samplearch.util.DebugLog;

import javax.inject.Inject;


/**
 * A utility class that handles navigation in {@link MockyListActivity}.
 */
public class MockyFragmentNavigator {

    private final int containerId;
    private final FragmentManager fragmentManager;

    @Inject
    public MockyFragmentNavigator(MockyListActivity activity){
        this.containerId = R.id.mocky_list_fragment_container;
        this.fragmentManager = activity.getSupportFragmentManager();
    }

    public void navigateToContributor(String owner, String name){
        DebugLog.write();
        ContributorFragment fragment=ContributorFragment.create(owner,name);
        String tag= "repo"+"/"+owner+"/"+name;
        fragmentManager.beginTransaction()
                .replace(containerId,fragment,tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
