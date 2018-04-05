package com.example.samplearch.ui.navigator;

import android.content.Intent;

import com.example.samplearch.R;
import com.example.samplearch.ui.view.entry.login.LoginActivity;
import com.example.samplearch.ui.view.tab.MainTabActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ActivityNavigator {

    @Inject
    public ActivityNavigator() {
        //empty
    }


    /**
     * Goes to the {@link MainTabActivity} screen.
     *
     * @param activity A Activity needed to open with animation the destiny activity.
     */
    public void navigateToMainTab(LoginActivity activity) {
        if (activity != null) {
            Intent intentToLaunch = MainTabActivity.getCallingIntent(activity);
            intentToLaunch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            activity.startActivity(intentToLaunch);
            activity.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);

        }
    }
}
