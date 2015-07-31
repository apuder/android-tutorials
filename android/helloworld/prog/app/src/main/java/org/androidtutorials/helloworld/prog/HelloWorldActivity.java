
package org.androidtutorials.helloworld.prog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

/**
 * This application is the simplest version of "Hello World" for Android. A
 * "Hello World" label is displayed in the center of the screen. This version
 * does not make use of layout files. The UI is constructed programmatically via
 * appropriate API. This application also introduces a basic Activity which is
 * the main Android abstraction for a user interaction. Each application needs
 * at least one activity. The implementation class needs to derive from base
 * class <code>Activity</code>. Note that the main activity needs to be
 * referenced in <code>AndroidManifest.xml</code>.
 */
public class HelloWorldActivity extends Activity {

    /*
     * The main entry point of an activity is method onCreate(). Android will
     * call this method whenever the activity is first created. The Bundle
     * parameter can be used to load application state that was made persistent
     * during a previous invocation of the same activity. Method onCreate() is
     * one of several callbacks that are called as part of the activities
     * lifecycle.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        /*
         * For each method part of the activity lifecycle it is necessary to
         * call the base class implementation.
         */
        super.onCreate(savedInstanceState);

        /*
         * Android features several layout managers. Each layout manager acts as
         * a container for several widgets that arranges its children according
         * to a specific policy. In this example a RelativeLayout is used. A
         * RelativeLayout aligns widgets relative to one another and the
         * enclosing container (i.e., the layout manager itself).
         */
        RelativeLayout layout = new RelativeLayout(this);

        /*
         * Create a TextView that represents the "Hello World" label.
         */
        TextView label = new TextView(this);
        label.setText("Hello World!");

        /*
         * Each layout manager has its own layout parameters that allow to
         * express rules how a widgets should be positioned. The WRAP_CONTENT
         * parameters specify that the widget should be sized to its natural
         * size (for both width and height).
         */
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        /*
         * Add a rule that specifies that the widget should be centered relative
         * to the enclosing container. It is instructional to pass different
         * layout parameters as defined in RelativeLayout (such as
         * RelativeLayout.ALIGN_PARENT_BOTTOM).
         */
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        /*
         * Add the label as a child to the RelativeLayout using the specific
         * layout parameters.
         */
        layout.addView(label, params);

        /*
         * Method setContentView() sets the main view of the activity. When the
         * activity becomes visible, this view will be displayed.
         */
        setContentView(layout);
    }
}