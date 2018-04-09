package com.example.samplearch.ui.view.entry.mocky;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.example.samplearch.R;
import com.example.samplearch.databinding.FragmentMockyListBinding;
import com.example.samplearch.ui.common.AutoClearedValue;
import com.example.samplearch.ui.binding.FragmentDataBindingComponent;
import com.example.samplearch.util.DebugLog;

import java.text.MessageFormat;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class MockyListFragment extends DaggerFragment {


    @Inject
    ViewModelProvider.Factory viewModelFactory;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<FragmentMockyListBinding> binding;
    AutoClearedValue<MockyListAdapter> adapter;

    private MockyListViewModel mockyListViewModel;

    public MockyListFragment() {
        DebugLog.write("setRetainInstance(true)");
        setRetainInstance(true);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DebugLog.write();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DebugLog.write();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DebugLog.write();
     FragmentMockyListBinding dataBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_mocky_list, container, false,dataBindingComponent);
     binding = new AutoClearedValue<>(this, dataBinding);
     return dataBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DebugLog.write();
        mockyListViewModel= ViewModelProviders.of(this, viewModelFactory).get(MockyListViewModel.class);
        initRecyclerView();
        MockyListAdapter mlAdapter= new MockyListAdapter(dataBindingComponent,true,repo -> DebugLog.write("repo ->",repo.owner.login +" "+repo.name));
        binding.get().repoList.setAdapter(mlAdapter);
        adapter = new AutoClearedValue<>(this,mlAdapter);
        initSearchInputListener();
        binding.get().setCallback(() -> mockyListViewModel.refresh());
    }


    private void initRecyclerView() {
        DebugLog.write();
        binding.get().repoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager)
                        recyclerView.getLayoutManager();
                int lastPosition = layoutManager
                        .findLastVisibleItemPosition();
                DebugLog.write("lastPosition -> ",lastPosition);
                if (lastPosition == adapter.get().getItemCount() - 1) {
                    DebugLog.write(MessageFormat.format("lastPosition : {0} - ItemCount() : {1} ",lastPosition ,adapter.get().getItemCount()));
                    mockyListViewModel.loadNextPage();
                }
            }
        });
        mockyListViewModel.getResults().observe(this, result -> {

            DebugLog.write("result -> "+result.toString());
            binding.get().setSearchResource(result);
            binding.get().setResultCount((result == null || result.data == null)
                    ? 0 : result.data.size());
            adapter.get().replace(result == null ? null : result.data);
            binding.get().executePendingBindings();
        });

        mockyListViewModel.getLoadMoreStatus().observe(this, loadingMore -> {
            if (loadingMore == null) {
                binding.get().setLoadingMore(false);
            } else {
                binding.get().setLoadingMore(loadingMore.isRunning());
                String error = loadingMore.getErrorMessageIfNotHandled();
                if (error != null) {
                    Snackbar.make(binding.get().loadMoreBar, error, Snackbar.LENGTH_LONG).show();
                }
            }
            binding.get().executePendingBindings();
        });
    }
    private void initSearchInputListener() {
        DebugLog.write();
        binding.get().input.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                DebugLog.write();
                doSearch(v);
                return true;
            }
            return false;
        });
        binding.get().input.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN)
                    && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                DebugLog.write();
                doSearch(v);
                return true;
            }
            return false;
        });


    }

    private void doSearch(View v) {
        DebugLog.write();
        String query = binding.get().input.getText().toString();
        // Dismiss keyboard
        dismissKeyboard(v.getWindowToken());
        binding.get().setQuery(query);
        mockyListViewModel.setQuery(query);
    }
    private void dismissKeyboard(IBinder windowToken) {
        DebugLog.write();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(windowToken, 0);
        }
    }

}
