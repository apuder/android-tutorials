package com.example.makkhay.rxjavademo.Model.Service;

import android.support.annotation.NonNull;

import com.example.makkhay.rxjavademo.Model.GithubRepos;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * In this class you will set up the base url which will be used to make the api call.
 * Here we will create a new object of Retrofit using builder and call the factory adapter where you point down
 * to the Rxjava
 *
 */

public class GitHubClient {

    private static final String GITHUB_BASE_URL = "https://api.github.com/";

    private static GitHubClient instance;
    private GithubService gitHubService;

    private GitHubClient() {
        final Gson gson =
                new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(GITHUB_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        gitHubService = retrofit.create(GithubService.class);
    }

    public static GitHubClient getInstance() {
        if (instance == null) {
            instance = new GitHubClient();
        }
        return instance;
    }

    /**
     * Here we are also using observable which actually point to the Interface GithubService
     * @param userName will be username that will be searched from github
     * @return starred repos of the particular user
     */

    public rx.Observable<List<GithubRepos>> getStarredRepos(@NonNull String userName) {
        return gitHubService.getStarredRepositories(userName);
    }
}