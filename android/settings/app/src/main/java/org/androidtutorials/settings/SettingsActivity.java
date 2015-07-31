package org.androidtutorials.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;


/**
 * SettingsActivity is derived from PreferenceActivity and acts as an
 * application-specific wrapper for the applications settings. The only thing
 * this SettingsActivity does is to set the title and to define the XML resource
 * (R.xml.settings) that contains the definition of the settings. The rendering
 * of the UI for the settings dialog as well as the loading and saving of the
 * current settings is taken care of by the base class PreferenceActivity.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PrefFragment())
                .commit();
        setTitle("Settings...");
    }

}
