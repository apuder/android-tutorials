
package org.androidtutorials.i18n;

import android.app.Activity;
import android.os.Bundle;

/**
 * This variation of Hello World is localized for different languages (for now
 * English and German). The implementation is identical to the declarative
 * version of Hello World, i.e., the main view is defined by a layout resource
 * via <code>R.layout.main</code>. The TextView in the layout resource
 * references a string that can be internationalized. In this particular
 * example, the English version of the string is in file
 * <code>res/values/strings.xml</code> whereas the German translation of the
 * string is located in file <code>res/values-de/strings.xml</code> ('de' stands
 * for 'deutsch', i.e., German). Depending on the settings of a device either
 * one of the string resources will be used. Technically, the file
 * <code>res/values/strings.xml</code> is the default version for all devices.
 * If a requested string is not found in the corresponding localized resource
 * file Android searches this default file for the string. Resources which do
 * not need to be localized should be declared in this file instead of declaring
 * the same string in all localized resource files.
 */
public class HelloWorldActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}