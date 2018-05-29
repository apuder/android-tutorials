package org.androidtutorials.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";
    RecyclerView mRecyclerView;
    ArrayList<String> mDataSet = new ArrayList<>();
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataSet.add("Ice Cream Sandwich");
        mDataSet.add("JellyBean");
        mDataSet.add("Kitkat");
        myAdapter = new MyAdapter(mDataSet);
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        rxJavaTutorial();

    }

    protected void rxJavaTutorial(){
        //Say this is the data from storage/network
        Observable.fromArray("Lollipop","Marshmallow","Nougat", "Oreo")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "Subscribed");
                    }

                    @Override
                    public void onNext(String s) {
                        mDataSet.add(s);
                        myAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Added: "+ s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error");
                    }

                    @Override
                    public void onComplete() {
                        myAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Completed");
                    }
                });
    }
}


class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<String> mDataSet;


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.textMessage);
        }
    }

    MyAdapter(ArrayList<String> myDataSet) {
        mDataSet = myDataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();

    }
}