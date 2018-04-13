package com.example.samplearch.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.samplearch.db.GithubTypeConverters;
import com.example.samplearch.util.DebugLog;

import java.text.MessageFormat;
import java.util.List;

import lombok.ToString;

@ToString
@Entity(primaryKeys = {"query"})
@TypeConverters(GithubTypeConverters.class)
public class RepoSearchResult {
    @NonNull
    public final String query;
    public final List<Integer> repoIds;
    public final int totalCount;
    @Nullable
    public final Integer next;

    public RepoSearchResult(@NonNull String query, List<Integer> repoIds, int totalCount,
                            @Nullable Integer next) {
        this.query = query;
        this.repoIds = repoIds;
        this.totalCount = totalCount;
        this.next = next;

    }
}
