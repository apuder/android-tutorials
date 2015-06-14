package org.androidtutorials.activity.implicit.green;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.androidtutorials.activity.implicit.green.R;

/**
 * GreenActivity is the only activity of a separate Android application called
 * ActivitySwitchImplicitGreen. Note that there is no dependence to any of the
 * classes of AndroidSwitchImplicit. The only thing that links GreenActivity
 * with the other activities (YellowActivity, RedActivity) is the same intent
 * filter. This application can be installed separately.
 */
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
