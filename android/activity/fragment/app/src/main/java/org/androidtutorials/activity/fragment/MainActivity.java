

package org.androidtutorials.activity.fragment;

import android.app.Activity;
import android.os.Bundle;

/**
 * The MainActivity is the main entry point of the application. It sets
 * R.layout.activity_main as the main content view. Note that there are two
 * versions of that layout: one for portrait mode (stored in res/layout) that
 * will show the two fragments in separate activities and one for landscape mode
 * (stored in res/layout-land) that will show the two fragments side by side.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
