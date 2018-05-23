package com.example.makkhay.rxjavademo;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.example.makkhay.rxjavademo.Adapter.GitHubRepoAdapter;
import com.example.makkhay.rxjavademo.Model.GithubRepos;
import com.example.makkhay.rxjavademo.Model.Service.GitHubClient;

import java.util.List;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * This is the main class where all the magic is going on.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GitHubRepoAdapter adapter;
    // Creating a subscription because you have to subscribe to a service when working with RXJava
    private Subscription subscription;
    private  ListView listView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creating a new object here
        adapter = new GitHubRepoAdapter();
        listView = findViewById(R.id.list_view_repos);
        listView.setAdapter(adapter);

        final EditText editTextUsername = findViewById(R.id.edit_text_username);
        final Button buttonSearch = findViewById(R.id.button_search);
        // Button onclick
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editTextUsername.getText().toString();
                if (!TextUtils.isEmpty(username)) {
                    getStarredRepos(username);
                }
            }
        });
    }

    /**
     * Good idea to unsubscribe here when your app is not running
     */
    @Override
    protected void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    /**
     * Here in this method we are passing username as a parameter
     * # we are also calling getInstance here to the subscription because we are listening
     * to subscription and you get the repo method from the service from the GitHubClient class
     * # We are also subscribing to the schedulers which are run concurrently on a UI thread ( RX java will make a thread for you)
     * # Finally it will subscribe on the list we are expecting from our model class
     *
     * @param username
     */
    private void getStarredRepos(String username) {
        subscription = GitHubClient.getInstance()
                .getStarredRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GithubRepos>>() {

                    // do something when onComplete
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "In onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "In onError()");
                    }

                    /**
                     * onNext is called when there's a success
                     * Here we will get the list of repos and setting the adapter with the value.
                     */
                    @Override
                    public void onNext(List<GithubRepos> gitHubRepos) {
                        Log.d(TAG, "In onNext()");
                        adapter.setGitHubRepos(gitHubRepos);
                    }
                });
    }
}