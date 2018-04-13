package com.example.samplearch.ui.binding;


import android.databinding.BindingAdapter;
import android.view.View;

import com.example.samplearch.util.DebugLog;

import java.text.MessageFormat;

/**
 * Data Binding adapters specific to the app.
 */
public class BindingAdapters {

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        DebugLog.write(MessageFormat.format("view -> {0} show -> {1}",view.getTag()==null?view.getClass().getSimpleName():view.getTag(),show));
        view.setVisibility(show ? View.VISIBLE : View.GONE);

    }
}
