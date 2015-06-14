

package org.androidtutorials.layout.linear;

import android.app.Activity;
import android.os.Bundle;

import org.androidtutorials.layout.linear.R;

/**
 * This example shows the use of a LinearLayout for both portrait and landscape
 * mode. The application displays six buttons that are arranged according to the
 * screen orientation. The layout resource is referenced via identifier
 * <code>R.layout.main</code>. Two different versions of this layout resource
 * exist and are picked according to the current screen orientation:
 * <code>res/layout/main.xml</code> is the layout resource used in portrait mode
 * and <code>res/layout-land/main.xml</code> is the layout resource used when
 * the device is in landscape mode.
 */
public class LayoutActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}