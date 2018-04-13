package com.example.samplearch.repository;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.samplearch.api.ApiResponse;
import com.example.samplearch.api.GithubService;
import com.example.samplearch.api.RepoSearchResponse;
import com.example.samplearch.db.GithubDb;
import com.example.samplearch.db.RepoDao;
import com.example.samplearch.executor.AppExecutors;
import com.example.samplearch.util.AbsentLiveData;
import com.example.samplearch.util.DebugLog;
import com.example.samplearch.util.RateLimiter;
import com.example.samplearch.vo.Repo;
import com.example.samplearch.vo.RepoSearchResult;
import com.example.samplearch.vo.Resource;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MockyRepository {


    private final GithubDb db;

    private final RepoDao repoDao;

    private final GithubService githubService;

    private final AppExecutors appExecutors;

    private RateLimiter<String> repoListRateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);


    @Inject
    public MockyRepository(AppExecutors appExecutors, GithubDb db, RepoDao repoDao,
                          GithubService githubService) {
        DebugLog.write();
        this.db = db;
        this.repoDao = repoDao;
        this.githubService = githubService;
        this.appExecutors = appExecutors;
    }


    public LiveData<Resource<Boolean>> searchNextPage(String query) {
        DebugLog.write(MessageFormat.format("query -> {0}",query));
        FetchNextSearchPageTask fetchNextSearchPageTask = new FetchNextSearchPageTask(
                query, githubService, db);
        appExecutors.networkIO().execute(fetchNextSearchPageTask);
        return fetchNextSearchPageTask.getLiveData();
    }


    public LiveData<Resource<List<Repo>>> search(String query) {
        DebugLog.write(MessageFormat.format("query -> {0}",query));
        return new NetworkBoundResource<List<Repo>, RepoSearchResponse>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull RepoSearchResponse item) {
                DebugLog.write(MessageFormat.format("item -> {0}",item));
                List<Integer> repoIds = item.getRepoIds();
                RepoSearchResult repoSearchResult = new RepoSearchResult(
                        query, repoIds, item.getTotal(), item.getNextPage());
                DebugLog.write(MessageFormat.format("repoSearchResult -> {0}",repoSearchResult));
                db.beginTransaction();
                try {
                    DebugLog.write();
                    repoDao.insertRepos(item.getItems());
                    repoDao.insert(repoSearchResult);
                    db.setTransactionSuccessful();
                } finally {
                    DebugLog.write();
                    db.endTransaction();
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Repo> data) {
                DebugLog.write("data -> null => true");
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<List<Repo>> loadFromDb() {
                DebugLog.write();
                return Transformations.switchMap(repoDao.search(query), searchData -> {
                    if (searchData == null) {
                        DebugLog.write("searchData -> null");
                        return AbsentLiveData.create();
                    } else {
                        DebugLog.write(MessageFormat.format("searchData -> {0}",searchData.toString()));
                        return repoDao.loadOrdered(searchData.repoIds);
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RepoSearchResponse>> createCall() {
                DebugLog.write();
                return githubService.searchRepos(query);
            }

            @Override
            protected RepoSearchResponse processResponse(ApiResponse<RepoSearchResponse> response) {
                DebugLog.write();
                RepoSearchResponse body = response.body;

                if (body != null) {
                    body.setNextPage(response.getNextPage());
                }
                return body;
            }
        }.asLiveData();
    }
}
