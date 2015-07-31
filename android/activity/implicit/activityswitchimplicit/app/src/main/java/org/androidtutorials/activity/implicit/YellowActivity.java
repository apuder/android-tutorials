package org.androidtutorials.activity.implicit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class YellowActivity extends LoggingActivity {

    private static int calls = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(org.androidtutorials.activity.implicit.R.layout.yellow);
    }

    @Override
    public void onBackPressed() {
        storeNumberOfCalls();
        super.onBackPressed();
    }

    public void onClick(View v) {
        storeNumberOfCalls();
        finish();
    }

    protected void storeNumberOfCalls() {
        Intent intent = new Intent();
        intent.putExtra("CALLS", ++calls);
        setResult(RESULT_OK, intent);
    }
}
