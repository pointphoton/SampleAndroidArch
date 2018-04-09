package com.example.samplearch.ui.view.entry.mocky;

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
        DebugLog.write("result -> ",results.getValue());
        return results;
    }

    public void setQuery(@NonNull String originalInput) {
        DebugLog.write("originalInput -> "+originalInput);
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
        DebugLog.write("value -> ",value);
        if (value == null || value.trim().length() == 0) {
            return;
        }
        nextPageHandler.queryNextPage(value);
    }

    void refresh() {
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
        private String query;
        private final MockyRepository repository;
        @VisibleForTesting
        boolean hasMore;


        static class LoadMoreState {
            private final boolean running;
            private final String errorMessage;
            private boolean handledError = false;

            LoadMoreState(boolean running, String errorMessage) {
                DebugLog.write("new -> ", MessageFormat.format("running : {0} errorMessage : {1} ",running,errorMessage));
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
            DebugLog.write("query ->"+query);
            if (Objects.equals(this.query, query)) {
                return;
            }
            unregister();
            this.query = query;
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
            DebugLog.write("hasMore -> ",hasMore);
            if (nextPageLiveData != null) {
                nextPageLiveData.removeObserver(this);
                nextPageLiveData = null;
                if (hasMore) {
                    query = null;
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
