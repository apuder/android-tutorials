
package org.androidtutorials.layout.relative;

import android.app.Activity;
import android.os.Bundle;

import org.androidtutorials.layout.relative.R;

/**
 * This example shows the use of a RelativeLayout for both portrait and
 * landscape mode. The application displays five buttons that are stretched to
 * make use of the width and height of the display for both landscape and
 * portrait orientation. This application uses only one layout resource as
 * described in <code>res/layout/main.xml</code>.
 */
public class LayoutActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}