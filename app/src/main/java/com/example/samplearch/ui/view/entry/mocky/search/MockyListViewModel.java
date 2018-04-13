package com.example.samplearch.ui.view.entry.mocky.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.example.samplearch.repository.MockyRepository;
import com.example.samplearch.util.AbsentLiveData;
import com.example.samplearch.util.DebugLog;
import com.example.samplearch.util.Objects;
import com.example.samplearch.vo.Repo;
import com.example.samplearch.vo.Resource;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class MockyListViewModel extends ViewModel {

    private final MutableLiveData<String> query = new MutableLiveData<>();

    private final LiveData<Resource<List<Repo>>> results;

    private final NextPageHandler nextPageHandler;

    @Inject
    public MockyListViewModel(MockyRepository repository) {
        DebugLog.write();
        nextPageHandler = new NextPageHandler(repository);
        results = Transformations.switchMap(query, search -> {
            if (search == null || search.trim().length() == 0) {
                DebugLog.write();
                return AbsentLiveData.create();
            } else {
                DebugLog.write();
                return repository.search(search);
            }
        });
    }


    @VisibleForTesting
    public LiveData<Resource<List<Repo>>> getResults() {
        DebugLog.write(MessageFormat.format("result -> {0}",results.getValue()));
        return results;
    }

    public void setQuery(@NonNull String originalInput) {
        DebugLog.write(MessageFormat.format("originalInput -> {0}",originalInput));
        String input = originalInput.toLowerCase(Locale.getDefault()).trim();
        DebugLog.write(MessageFormat.format("input : {0} MLiveData : {1}",input,query.getValue()));
        if (Objects.equals(input, query.getValue())) {
            return;
        }
        nextPageHandler.reset();
        query.setValue(input);
    }

    @VisibleForTesting
    public LiveData<NextPageHandler.LoadMoreState> getLoadMoreStatus() {
        DebugLog.write();
        return nextPageHandler.getLoadMoreState();
    }

    @VisibleForTesting
    public void loadNextPage() {

        String value = query.getValue();
        DebugLog.write(MessageFormat.format("value -> {0}",value));
        if (value == null || value.trim().length() == 0) {
            return;
        }
        nextPageHandler.queryNextPage(value);
    }

    public void refresh() {
        DebugLog.write();
        if (query.getValue() != null) {
            query.setValue(query.getValue());
        }
    }

    @VisibleForTesting
    static class NextPageHandler implements Observer<Resource<Boolean>> {
        @Nullable
        private LiveData<Resource<Boolean>> nextPageLiveData;
        private final MutableLiveData<LoadMoreState> loadMoreState = new MutableLiveData<>();
        private String queryStr;
        private final MockyRepository repository;
        @VisibleForTesting
        boolean hasMore;


        static class LoadMoreState {
            private final boolean running;
            private final String errorMessage;
            private boolean handledError = false;

            LoadMoreState(boolean running, String errorMessage) {
                DebugLog.write(MessageFormat.format("new -> running : {0} errorMessage : {1} ",running,errorMessage));
                this.running = running;
                this.errorMessage = errorMessage;
            }

            boolean isRunning() {
                return running;
            }

            String getErrorMessage() {
                return errorMessage;
            }

            String getErrorMessageIfNotHandled() {
                if (handledError) {
                    return null;
                }
                handledError = true;
                return errorMessage;
            }
        }

        @VisibleForTesting
        NextPageHandler(MockyRepository repository) {
            DebugLog.write();
            this.repository = repository;
            reset();
        }

        void queryNextPage(String query) {
            DebugLog.write(MessageFormat.format("query -> {0}",query));
            DebugLog.write(MessageFormat.format("this.queryStr -> {0}",this.queryStr==null?"null":this.queryStr));
            if (Objects.equals(this.queryStr, query)) {
                return;
            }
            unregister();
            this.queryStr = query;
            nextPageLiveData = repository.searchNextPage(query);
            loadMoreState.setValue(new LoadMoreState(true, null));
            //noinspection ConstantConditions
            nextPageLiveData.observeForever(this);
        }

        @Override
        public void onChanged(@Nullable Resource<Boolean> result) {
            DebugLog.write();
            if (result == null) {
                reset();
            } else {
                switch (result.status) {
                    case SUCCESS:
                        hasMore = Boolean.TRUE.equals(result.data);
                        unregister();
                        loadMoreState.setValue(new LoadMoreState(false, null));
                        break;
                    case ERROR:
                        hasMore = true;
                        unregister();
                        loadMoreState.setValue(new LoadMoreState(false,
                                result.message));
                        break;
                }
            }
        }

        private void unregister() {
            DebugLog.write(MessageFormat.format("hasMore -> {0}",hasMore));
            if (nextPageLiveData != null) {
                nextPageLiveData.removeObserver(this);
                nextPageLiveData = null;
                if (hasMore) {
                    queryStr = null;
                }
            }
        }

        private void reset() {
            DebugLog.write();
            unregister();
            hasMore = true;
            loadMoreState.setValue(new LoadMoreState(
                    false, null));
        }

        MutableLiveData<LoadMoreState> getLoadMoreState() {
            DebugLog.write();
            return loadMoreState;
        }
    }
}
