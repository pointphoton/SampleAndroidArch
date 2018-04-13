package com.example.samplearch.ui.view.entry.mocky.contributor;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import com.example.samplearch.util.DebugLog;
import com.example.samplearch.util.Objects;
import com.example.samplearch.vo.Contributor;
import com.example.samplearch.vo.Repo;
import com.example.samplearch.vo.Resource;

import java.text.MessageFormat;
import java.util.List;

import javax.inject.Inject;

public class ContributorViewModel extends ViewModel {


    @VisibleForTesting
    final MutableLiveData<RepoId> repoId;
    private final LiveData<Resource<Repo>> repo=null;
    private final LiveData<Resource<List<Contributor>>> contributors=null;

    @Inject
    public ContributorViewModel(){
        DebugLog.write();
        this.repoId=new MutableLiveData<>();
    }

    public LiveData<Resource<Repo>> getRepo() {
        DebugLog.write(MessageFormat.format("repo -> {0}",repo.getValue()));

        return repo;
    }

    public LiveData<Resource<List<Contributor>>> getContributors() {
        DebugLog.write(MessageFormat.format("contributors -> {0}",contributors.getValue()));
        return contributors;
    }


    public void retry() {
        DebugLog.write();
        RepoId current = repoId.getValue();
        if (current != null && !current.isEmpty()) {
            repoId.setValue(current);
        }
    }


    @VisibleForTesting
    public void setId(String owner, String name) {
        DebugLog.write(MessageFormat.format("owner : {0} - name : {1}",owner,name ) );
        RepoId update = new RepoId(owner, name);
        if (Objects.equals(repoId.getValue(), update)) {
            return;
        }
        repoId.setValue(update);
    }




    @VisibleForTesting
    static class RepoId {

        public final String owner;
        public final String name;

        RepoId(String owner, String name) {
            this.owner = owner == null ? null : owner.trim();
            this.name = name == null ? null : name.trim();
        }

        boolean isEmpty() {
            return owner == null || name == null || owner.length() == 0 || name.length() == 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            RepoId repoId = (RepoId) o;

            if (owner != null ? !owner.equals(repoId.owner) : repoId.owner != null) {
                return false;
            }
            return name != null ? name.equals(repoId.name) : repoId.name == null;
        }

        @Override
        public int hashCode() {
            int result = owner != null ? owner.hashCode() : 0;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            return result;
        }
    }
}
