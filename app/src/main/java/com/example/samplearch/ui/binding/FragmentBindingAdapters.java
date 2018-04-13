package com.example.samplearch.ui.binding;


import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.samplearch.util.DebugLog;

import java.text.MessageFormat;

import javax.inject.Inject;

/**
 * Binding adapters that work with a fragment instance.
 */
public class FragmentBindingAdapters {
    final Fragment fragment;

    @Inject
    public FragmentBindingAdapters(Fragment fragment) {
        this.fragment = fragment;
    }
    @BindingAdapter("imageUrl")
    public void bindImage(ImageView imageView, String url) {

        DebugLog.write(MessageFormat.format("imageView -> {0}",url));
        Glide.with(fragment).load(url).into(imageView);
    }
}
