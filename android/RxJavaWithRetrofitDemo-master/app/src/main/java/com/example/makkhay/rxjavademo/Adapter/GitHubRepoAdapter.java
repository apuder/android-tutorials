package com.example.makkhay.rxjavademo.Adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.example.makkhay.rxjavademo.Model.GithubRepos;
import com.example.makkhay.rxjavademo.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * An adapter class that extends the baseadapter class. This class acts as a
 * bridge between the UI components and the data source that fill data into the UI Component
 *
 */

public class GitHubRepoAdapter extends BaseAdapter {

    private List<GithubRepos> gitHubRepos = new ArrayList<>();

    @Override public int getCount() {
        return gitHubRepos.size();
    }
    // Will get the item with the position
    @Override public GithubRepos getItem(int position) {
        if (position < 0 || position >= gitHubRepos.size()) {
            return null;
        } else {
            return gitHubRepos.get(position);
        }
    }

    @Override public long getItemId(int position) {
        return position;
    }


    @Override public View getView(int position, View convertView, ViewGroup parent) {
        final View view = (convertView != null ? convertView : createView(parent));
        final GitHubRepoViewHolder viewHolder = (GitHubRepoViewHolder) view.getTag();
        viewHolder.setGitHubRepo(getItem(position));
        return view;
    }
    // this method will populate the list when there is data
    public void setGitHubRepos(@Nullable List<GithubRepos> repos) {
        if (repos == null) {
            return;
            }
        gitHubRepos.clear();
        gitHubRepos.addAll(repos);
        notifyDataSetChanged();
    }

    private View createView(ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_github_repos, parent, false);
        final GitHubRepoViewHolder viewHolder = new GitHubRepoViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    /**
     *     add and notify that data has been changed.
     *     Very important to prevent crashes

     */
    public void add(GithubRepos gitHubRepo) {
        gitHubRepos.add(gitHubRepo);
        notifyDataSetChanged();
    }

    /**
     * This is a class to initialize all the views such as Textview
     */
    private static class GitHubRepoViewHolder {

        private TextView textRepoName;
        private TextView textRepoDescription;
        private TextView textLanguage;
        private TextView textStars;

        public GitHubRepoViewHolder(View view) {
            textRepoName = (TextView) view.findViewById(R.id.text_repo_name);
            textRepoDescription = (TextView) view.findViewById(R.id.text_repo_description);
            textLanguage = (TextView) view.findViewById(R.id.text_language);
            textStars = (TextView) view.findViewById(R.id.text_stars);
        }

        public void setGitHubRepo(GithubRepos gitHubRepo) {
            textRepoName.setText(gitHubRepo.name);
            textRepoDescription.setText(gitHubRepo.description);
            textLanguage.setText("Language: " + gitHubRepo.language);
            textStars.setText("Stars: " + gitHubRepo.stargazersCount);
        }
    }
}