
package org.androidtutorials.storage;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Parse's cloud service requires an Android application object. Note that
 * StorageApplication is derived from base class Application and is referenced
 * from AndroidManifest.xml.
 */
public class StorageApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * The authentication key can be used for demo purposes. If you want to
         * use Parse's web interface to view the uploaded data online, you need
         * to register and use your own authentication key.
         */
        Parse.initialize(this, "74nJUmHmWvcR3YQNJMDFd14TqmaYzezuLQ22w8Z9",
                "jdhBBOhpcC19sQFUhdJdKddIu3usx99RxUxWK2W3");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }

}
