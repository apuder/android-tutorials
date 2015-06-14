

package org.androidtutorials.activity.explicit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * This class serves as the common base class for both BlueActivity and
 * YellowActity. Its only purpose is to override all Activity lifecycle methods.
 * When running the ActivitySwitch application, the invocation of the various
 * lifecycle methods will be logged on the debugging console. In particular note
 * the interleaving of methods from BlueActivity to YellowActivity as control is
 * passed back and forth between the two activities. Refer to this page for a
 * documentation of the various methods:
 * http://developer.android.com/reference/android/app/Activity.html
 */
public abstract class LoggingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getClass().getName(), "onCreate() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getName(), "onDestroy() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(getClass().getName(), "onPause() called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(getClass().getName(), "onRestart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(getClass().getName(), "onResume() called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(getClass().getName(), "onStart() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(getClass().getName(), "onStop() called");
    }

}
