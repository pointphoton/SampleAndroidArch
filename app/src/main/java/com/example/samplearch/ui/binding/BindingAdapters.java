package com.example.samplearch.ui.binding;


import android.databinding.BindingAdapter;
import android.view.View;

import com.example.samplearch.util.DebugLog;

/**
 * Data Binding adapters specific to the app.
 */
public class BindingAdapters {

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        DebugLog.write("show -> "+show);
        view.setVisibility(show ? View.VISIBLE : View.GONE);

    }
}
