package com.example.samplearch.ui.view.entry.mocky.contributor;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.samplearch.R;
import com.example.samplearch.databinding.ContributorItemBinding;
import com.example.samplearch.ui.common.DataBoundListAdapter;
import com.example.samplearch.util.DebugLog;
import com.example.samplearch.util.Objects;
import com.example.samplearch.vo.Contributor;

public class ContributorListAdapter extends DataBoundListAdapter<Contributor, ContributorItemBinding> {

    private final android.databinding.DataBindingComponent dataBindingComponent;
    private final ContributorClickCallback callback;

    public ContributorListAdapter(DataBindingComponent dataBindingComponent,
            ContributorClickCallback callback) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = callback;
    }

    @Override
    protected ContributorItemBinding createBinding(ViewGroup parent) {
        DebugLog.write();
        ContributorItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.contributor_item, parent, false,
                        dataBindingComponent);
        binding.getRoot().setOnClickListener(v -> {
            Contributor contributor = binding.getContributor();
            if (contributor != null && callback != null) {
                callback.onClick(contributor);
            }
        });
        return binding;
    }

    @Override
    protected void bind(ContributorItemBinding binding, Contributor item) {
        DebugLog.write();
        binding.setContributor(item);
    }

    @Override
    protected boolean areItemsTheSame(Contributor oldItem, Contributor newItem) {
        DebugLog.write();
        return Objects.equals(oldItem.getLogin(), newItem.getLogin());
    }

    @Override
    protected boolean areContentsTheSame(Contributor oldItem, Contributor newItem) {
        DebugLog.write();
        return Objects.equals(oldItem.getAvatarUrl(), newItem.getAvatarUrl())
                && oldItem.getContributions() == newItem.getContributions();
    }

    public interface ContributorClickCallback {
        void onClick(Contributor contributor);
    }
}
