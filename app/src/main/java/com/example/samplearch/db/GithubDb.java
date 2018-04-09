package com.example.samplearch.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.samplearch.vo.Repo;
import com.example.samplearch.vo.RepoSearchResult;


/**
 * Main database description.
 */
@Database(entities = {  Repo.class, RepoSearchResult.class}, version = 1, exportSchema = false)
public abstract class GithubDb extends RoomDatabase {

    abstract public RepoDao repoDao();
}
