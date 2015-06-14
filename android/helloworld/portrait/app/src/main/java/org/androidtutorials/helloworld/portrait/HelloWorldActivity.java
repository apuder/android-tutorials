

package org.androidtutorials.helloworld.portrait;

import android.app.Activity;
import android.os.Bundle;

import org.androidtutorials.helloworld.portrait.R;

/**
 * This version of Hello World locks into portrait mode. In terms of
 * implementation it is identical to the declarative version. Locking portrait
 * orientation is done via an XML-attribute in <code>AndroidManifest.xml</code>.
 */
public class HelloWorldActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}