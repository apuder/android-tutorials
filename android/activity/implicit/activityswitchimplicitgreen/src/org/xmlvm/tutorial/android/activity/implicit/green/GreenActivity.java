package org.xmlvm.tutorial.android.activity.implicit.green;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GreenActivity extends Activity {

    private static int calls = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.green);
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
