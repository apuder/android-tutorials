/** Copyright (c) 2002-2011 by XMLVM.org
 *
 * Project Info:  http://www.xmlvm.org
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 */

package org.androidtutorials.storage;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import android.app.Application;

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
