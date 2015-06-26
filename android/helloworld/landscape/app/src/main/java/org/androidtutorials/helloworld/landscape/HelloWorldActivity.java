

package org.androidtutorials.helloworld.landscape;

import android.app.Activity;
import android.os.Bundle;

import org.androidtutorials.helloworld.landscape.R;

/**
 * This version of Hello World locks into landscape mode. In terms of
 * implementation it is identical to the declarative version. Locking landscape
 * orientation is done via an XML-attribute in <code>AndroidManifest.xml</code>.
 */
public class HelloWorldActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}