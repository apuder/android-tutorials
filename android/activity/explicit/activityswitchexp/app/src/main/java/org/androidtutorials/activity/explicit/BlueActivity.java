
package org.androidtutorials.activity.explicit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * This application demonstrates the Activty lifecycle. An application typically
 * consists of several Activities that can call each other. It is even possible
 * to call activities from other applications (e.g., to pick a contact from the
 * address book). Calling an activity pushes this activity onto a stack to
 * become the active activity. Leaving the active activity (e.g., by pressing
 * the Back button on the Android device) pops that activity off the stack to
 * return to the previous activity. Each activity has its own lifecycle and
 * Android will call callbacks at the appropriate moments. So far only the
 * <code>onCreate()</code) callback has been used that is called by Android
 * whenever the Activity is created. The activity lifecycle is described here:
 * http://developer.android.com/reference/android/app/Activity.html
 * 
 * This application consists of two activities: BlueActivity and YellowActivity.
 * BlueActivity is the main activity that will be called when the application
 * launches. Upon pressing a button, YellowActivity will be called, pushing
 * BlueActivity into the background. Upon leaving YellowActivity, the system
 * returns to BlueActivity. This procedure can be repeated arbitrarily. In order
 * to demonstrate the activity lifecycle, a helper class LoggingActivity is
 * used. Its only purpose is to override all activity lifecycle methods to print
 * out appropriate messages. When running this application in the emulator,
 * check the logging output to understand when which activity lifecycle methods
 * are invoked by Android. YellowActivity is invoked from BlueActivity via an
 * explicit intent, meaning, the intent used to launch YellowActivity is making
 * direct/explicit reference to YellowActivity's class object.
 */
public class BlueActivity extends LoggingActivity {

    private TextView resultField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blue);

        resultField = (TextView) findViewById(R.id.result);
    }

    /*
     * This method will be called whenever YellowActivity terminates. It is
     * possible to return a result in form of an Intent.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            /*
             * If no error occurred, use the Intent to retrieve an integer
             * stored under ID "CALLS".
             */
            int calls = data.getExtras().getInt("CALLS");
            /*
             * Display the integer in the BlueActivity's TextView.
             */
            resultField.setText("Calls to YellowActivity: " + calls);
        }
    }

    /*
     * This method will be invoked whenever the button is clicked in the
     * BlueActivity. This will trigger the invocation of YellowActivity.
     */
    public void onClick(View v) {
        resultField.setText("");
        /*
         * Construct an Intent to launch YellowActivity. Note that
         * YellowActivity is referenced explicitly via its class object.
         */
        Intent intent = new Intent(this, YellowActivity.class);
        /*
         * Start YellowActivity and wait for the result.
         */
        startActivityForResult(intent, 0);
    }
}