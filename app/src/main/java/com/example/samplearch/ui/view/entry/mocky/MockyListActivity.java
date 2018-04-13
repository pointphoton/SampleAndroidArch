package com.example.samplearch.ui.view.entry.mocky;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.samplearch.R;
import com.example.samplearch.ui.view.entry.mocky.search.MockyListFragment;
import com.example.samplearch.util.DebugLog;

import dagger.android.support.DaggerAppCompatActivity;

public class MockyListActivity extends DaggerAppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocky_list);
        DebugLog.write();
        if(savedInstanceState==null){
            MockyListFragment searchFragment = new MockyListFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mocky_list_fragment_container, searchFragment)
                    .commitAllowingStateLoss();

        }
    }




    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MockyListActivity.class);
    }
}
