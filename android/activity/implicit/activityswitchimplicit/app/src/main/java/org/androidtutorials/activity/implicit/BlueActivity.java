package org.androidtutorials.activity.implicit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


/**
 * This tutorial is a variation of the explicit activity switch demo. There are
 * a total of four activities involved in this tutorial: BlueActivity,
 * RedActivity, YellowActivity and GreenActivity. Note that the latter
 * (GreenActivity) is actually part of a separate Android project. The way an
 * activity is launched in this tutorial is via an implicit intent. In contrast
 * to an explicit intent, where the class object of the activity to be launch is
 * referenced, an implicit intent merely defines a string that references an
 * action (see member ACTION_COLOR). Refer to AndroidManifest.xml to see that
 * RedActivity, YellowActivity as well as GreenActivity all have an intent
 * filter for precisely this action. If the user triggers an intent with this
 * action, there are several possible activities that can respond to this
 * intent. In this case Android will show a so-called activity chooser dialog
 * where the user can specify which activity should be used to respond to the
 * intent. First run this demo without installing the
 * ActivitySwitchImplicitGreen application. In this case there are two
 * activities that filter on ACTION_COLOR and the activity chooser dialog will
 * show those two activities. Next install ActivitySwitchImplicitGreen and
 * re-run ActivitySwitchImplicit. Now the activity chooser dialog will show
 * three possible activities that can respond to ACTION_COLOR. This example
 * shows how an activity from a different application can be referenced. In
 * essence, this demonstrates the late binding mechanism of Android.
 */
public class BlueActivity extends LoggingActivity {

    /**
     * ACTION_COLOR is of type string and defines an action that is used to
     * identify an activity that will respond to this action. Note that the very
     * same string is used in AndroidManifest.xml to define an intent filter.
     */
    public static final String ACTION_COLOR = "org.androidtutorials.intent.action.ACTION_COLOR";
    private TextView           resultField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(org.androidtutorials.activity.implicit.R.layout.blue);

        resultField = (TextView) findViewById(org.androidtutorials.activity.implicit.R.id.result);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            int calls = data.getExtras().getInt("CALLS");
            resultField.setText("Calls to recently called: " + calls);
        }
    }

    public void onClick(View v) {
        resultField.setText("");
        /*
         * Start an activity by specifying an action. Note that in contrast to
         * an implicit intent, the class object is *not* used to identify the
         * responding activity.
         */
        Intent intent = new Intent(ACTION_COLOR);
        startActivityForResult(intent, 0);
    }
}