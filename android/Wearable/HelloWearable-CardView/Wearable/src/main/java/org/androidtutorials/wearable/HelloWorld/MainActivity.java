
package org.androidtutorials.wearable.HelloWorld;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import com.example.android.wearable.wear.alwayson.R;

public class MainActivity extends WearableActivity {


    private TextView mTextView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // WatchViewStub handles wear screen type at runtime
        // Decides which layout to onflate accordingly
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {

                mTextView = (TextView) stub.findViewById(R.id.hello);

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}