package com.example.samplearch.repository;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.samplearch.api.ApiResponse;
import com.example.samplearch.api.GithubService;
import com.example.samplearch.api.RepoSearchResponse;
import com.example.samplearch.db.GithubDb;
import com.example.samplearch.util.DebugLog;
import com.example.samplearch.vo.RepoSearchResult;
import com.example.samplearch.vo.Resource;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * A task that reads the search result in the database and fetches the next page, if it has one.
 */
public class FetchNextSearchPageTask implements Runnable {
    private final MutableLiveData<Resource<Boolean>> liveData = new MutableLiveData<>();
    private final String query;
    private final GithubService githubService;
    private final GithubDb db;

    FetchNextSearchPageTask(String query, GithubService githubService, GithubDb db) {
        this.query = query;
        this.githubService = githubService;
        this.db = db;
    }

    @Override
    public void run() {
        DebugLog.write();
        RepoSearchResult current = db.repoDao().findSearchResult(query);
        if(current == null) {
            DebugLog.write("current ->","null");
            liveData.postValue(null);
            return;
        }
        final Integer nextPage = current.next;
        if (nextPage == null) {
            DebugLog.write("current.next ->","null");
            liveData.postValue(Resource.success(false));
            return;
        }
        try {
            DebugLog.write(MessageFormat.format("query {0} nextPage {1}",query,nextPage));
            Response<RepoSearchResponse> response = githubService
                    .searchRepos(query, nextPage).execute();
            DebugLog.write(response.body());
            ApiResponse<RepoSearchResponse> apiResponse = new ApiResponse<>(response);
            if (apiResponse.isSuccessful()) {
                DebugLog.write();
                // we merge all repo ids into 1 list so that it is easier to fetch the result list.
                List<Integer> ids = new ArrayList<>();
                ids.addAll(current.repoIds);
                StringBuilder b1 = new StringBuilder();
                for (Integer id:ids) {
                    b1.append(id);
                }
                DebugLog.write("current ids -> ", b1.toString());
                //noinspection ConstantConditions
                ids.addAll(apiResponse.body.getRepoIds());
                StringBuilder b2 = new StringBuilder();
                for (Integer id:ids) {
                    b2.append(id);
                }
                DebugLog.write("repo ids -> ", b2.toString());
                RepoSearchResult merged = new RepoSearchResult(query, ids,
                        apiResponse.body.getTotal(), apiResponse.getNextPage());
                DebugLog.write("merged -> ",merged.toString());
                DebugLog.write("items -> ",apiResponse.body.getItems());
                try {
                    db.beginTransaction();
                    db.repoDao().insert(merged);
                    db.repoDao().insertRepos(apiResponse.body.getItems());
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                DebugLog.write("apiResponse nextPage -> ",apiResponse.getNextPage());
                liveData.postValue(Resource.success(apiResponse.getNextPage() != null));
            } else {
                liveData.postValue(Resource.error(apiResponse.errorMessage, true));
            }
        } catch (IOException e) {
            liveData.postValue(Resource.error(e.getMessage(), true));
        }
    }

    LiveData<Resource<Boolean>> getLiveData() {
        DebugLog.write();
        return liveData;
    }
}
