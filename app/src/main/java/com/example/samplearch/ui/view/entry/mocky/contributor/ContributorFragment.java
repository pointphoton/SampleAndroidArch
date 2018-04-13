package com.example.samplearch.ui.view.entry.mocky.contributor;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.samplearch.R;
import com.example.samplearch.databinding.FragmentContributorBinding;
import com.example.samplearch.ui.binding.FragmentDataBindingComponent;
import com.example.samplearch.ui.common.AutoClearedValue;
import com.example.samplearch.ui.navigator.MockyFragmentNavigator;
import com.example.samplearch.util.DebugLog;
import com.example.samplearch.vo.Repo;
import com.example.samplearch.vo.Resource;

import java.text.MessageFormat;
import java.util.Collections;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ContributorFragment extends DaggerFragment {

    private static final String REPO_OWNER_KEY = "repo_owner";

    private static final String REPO_NAME_KEY = "repo_name";

      @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    MockyFragmentNavigator mockyFragmentNavigator;
    android.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    private ContributorViewModel contributorViewModel;
    AutoClearedValue<FragmentContributorBinding> binding;
    AutoClearedValue<ContributorListAdapter> adapter;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DebugLog.write();
        contributorViewModel = ViewModelProviders.of(this,viewModelFactory).get(ContributorViewModel.class);
        Bundle args = getArguments();
        if (args != null && args.containsKey(REPO_OWNER_KEY) &&
                args.containsKey(REPO_NAME_KEY)) {
            contributorViewModel.setId(args.getString(REPO_OWNER_KEY),
                    args.getString(REPO_NAME_KEY));
        } else {
            contributorViewModel.setId(null, null);
        }
        LiveData<Resource<Repo>> repo = contributorViewModel.getRepo();
        repo.observe(this, resource -> {
            binding.get().setRepo(resource == null ? null : resource.data);
            binding.get().setRepoResource(resource);
            binding.get().executePendingBindings();
        });

        ContributorListAdapter adapter = new ContributorListAdapter(dataBindingComponent,
                contributor -> DebugLog.write(MessageFormat.format("getLogin -> {0}",contributor.getLogin()) ));
        this.adapter = new AutoClearedValue<>(this, adapter);
        binding.get().contributorList.setAdapter(adapter);
        initContributorList(contributorViewModel);

    }

    private void initContributorList(ContributorViewModel viewModel) {
        DebugLog.write();
        viewModel.getContributors().observe(this, listResource -> {
            // we don't need any null checks here for the adapter since LiveData guarantees that
            // it won't call us if fragment is stopped or not started.
            if (listResource != null && listResource.data != null) {
                adapter.get().replace(listResource.data);
            } else {
                //noinspection ConstantConditions
                adapter.get().replace(Collections.emptyList());
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DebugLog.write();
        FragmentContributorBinding dataBinding=DataBindingUtil
                .inflate(inflater, R.layout.fragment_contributor, container, false);
        dataBinding.setRetryCallback(() -> contributorViewModel.retry());
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();


    }


    public static ContributorFragment create(String owner,String name){
        DebugLog.write();
        ContributorFragment contributorFragment=new ContributorFragment();
        Bundle args = new Bundle();
        args.putString(REPO_OWNER_KEY, owner);
        args.putString(REPO_NAME_KEY, name);
        contributorFragment.setArguments(args);
        return contributorFragment;

    }
}
