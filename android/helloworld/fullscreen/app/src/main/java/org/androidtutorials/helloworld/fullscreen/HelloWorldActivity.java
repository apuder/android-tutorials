
package org.androidtutorials.helloworld.fullscreen;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import org.androidtutorials.helloworld.fullscreen.R;

/**
 * A version of Hello World that runs in fullscreen mode which can be enabled
 * via method <code>requestWindowFeature()</code>. Otherwise this version is
 * identical to the declarative version of Hello World.
 */
public class HelloWorldActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Hide the title bar.
         */
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.main);
    }
}