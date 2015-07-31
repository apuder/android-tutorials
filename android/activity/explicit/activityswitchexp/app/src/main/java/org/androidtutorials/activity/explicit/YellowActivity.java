
package org.androidtutorials.activity.explicit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


/**
 * YellowActivity will be called from BlueActivity. It maintains a counter how
 * many times it has been invoked. Pressing the button in the YellowActivity
 * will return to BlueActivity.
 */
public class YellowActivity extends LoggingActivity {

    private static int calls = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yellow);
    }

    /*
     * You need to handle the BACK button explicitly. Pressing the BACK button
     * finishes the Activity and results have to be set BEFORE finishing the
     * Activity. So using lifecycle methods like onPause or onStop will not
     * work.
     */
    @Override
    public void onBackPressed() {
        storeNumberOfCalls();
        super.onBackPressed();
    }

    /*
     * Method onClick() will be called whenever the user presses the button in
     * the YellowActivity.
     */
    public void onClick(View v) {
        storeNumberOfCalls();
        /*
         * A call to finish() ends the calling activity and returns to the
         * previous one on the activity stack (in this case, BlueActivity).
         * Calling finish will trigger the calling of onActivityResult() in
         * BlueActivity returning the result that was set with setResult().
         */
        finish();
    }

    protected void storeNumberOfCalls() {
        /*
         * Use an Intent to store the result to be returned to the calling
         * activity.
         */
        Intent intent = new Intent();
        /*
         * The result (number of times YellowActivity has been called) will be
         * stored as "CALLS".
         */
        intent.putExtra("CALLS", ++calls);
        /*
         * A call to setResult() will not yet terminate YellowActivity nor call
         * BlueActivity.onActivityResult(). This only happens when calling
         * finish().
         */
        setResult(RESULT_OK, intent);
    }
}
