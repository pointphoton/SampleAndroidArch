package com.example.samplearch.ui.view.entry.login;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.samplearch.R;
import com.example.samplearch.databinding.ActivityLoginBinding;
import com.example.samplearch.ui.navigator.ActivityNavigator;
import com.example.samplearch.util.DebugLog;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class LoginActivity  extends DaggerAppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    @Inject
    ActivityNavigator navigator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DebugLog.write();
        ActivityLoginBinding binding =  DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.sampleText.setText(stringFromJNI());

       // binding.setLifecycleOwner(this);
        binding.setContext(this);

        //binding.button.setOnClickListener(this);

    }

    public void navigateToMainTab(View view) {
        DebugLog.write(view.getClass().getSimpleName());
        navigator.navigateToMainTab(this);
    }

    public void navigateToMainTab2(View view) {
        DebugLog.write(view.getClass().getSimpleName());
        navigator.navigateToMainTab(this);
    }






    public native String stringFromJNI();
}
