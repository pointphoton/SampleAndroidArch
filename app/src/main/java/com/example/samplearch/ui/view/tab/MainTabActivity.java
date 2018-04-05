package com.example.samplearch.ui.view.tab;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.samplearch.R;

public class MainTabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       DataBindingUtil.setContentView(this,R.layout.activity_tab);

    }


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MainTabActivity.class);
    }
}
