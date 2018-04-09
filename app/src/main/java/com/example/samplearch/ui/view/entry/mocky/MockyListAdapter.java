package com.example.samplearch.ui.view.entry.mocky;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.samplearch.R;
import com.example.samplearch.databinding.RepoItemBinding;
import com.example.samplearch.ui.common.DataBoundListAdapter;
import com.example.samplearch.util.DebugLog;
import com.example.samplearch.util.Objects;
import com.example.samplearch.vo.Repo;

/**
 * A RecyclerView adapter for {@link Repo} class.
 */
public class MockyListAdapter  extends DataBoundListAdapter<Repo, RepoItemBinding> {
private final android.databinding.DataBindingComponent dataBindingComponent;
      private final RepoClickCallback repoClickCallback;
      private final boolean showFullName;

    public MockyListAdapter(DataBindingComponent dataBindingComponent, boolean showFullName,
            RepoClickCallback repoClickCallback) {
        DebugLog.write();
        this.dataBindingComponent = dataBindingComponent;
        this.repoClickCallback = repoClickCallback;
        this.showFullName = showFullName;

    }


    @Override
    protected RepoItemBinding createBinding(ViewGroup parent) {
        DebugLog.write();
        RepoItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.repo_item,
                        parent, false, dataBindingComponent);
        binding.setShowFullName(showFullName);
        binding.getRoot().setOnClickListener(v -> {
            Repo repo = binding.getRepo();
            if (repo != null && repoClickCallback != null) {
                repoClickCallback.onClick(repo);
            }
        });
        return binding;
    }

    @Override
    protected void bind(RepoItemBinding binding, Repo item) {
        DebugLog.write();

        binding.setRepo(item);
    }


    @Override
    protected boolean areItemsTheSame(Repo oldItem, Repo newItem) {
        DebugLog.write();
        return Objects.equals(oldItem.owner, newItem.owner) &&
                Objects.equals(oldItem.name, newItem.name);
    }

    @Override
    protected boolean areContentsTheSame(Repo oldItem, Repo newItem) {
        DebugLog.write();
        return Objects.equals(oldItem.description, newItem.description) &&
                oldItem.stars == newItem.stars;
    }

    public interface RepoClickCallback {
        void onClick(Repo repo);
    }
}
