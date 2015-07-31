package org.androidtutorials.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Programming for API 11 or higher, a PreferenceFragment should be used to display
 * settings/preferences for an android application. The fragment, for this application, is
 * created by the SettingsActivity via FragmentManager. When created,
 * the preferences of the application are populated. Note that a preference
 * fragment can be attached to any Activity just like any other fragment.
 * The preferences framework takes care of all the loading of preferences
 * and the writing of preferences when they are changed.
 * NOTE: PreferenceFragment does not have its down context. A context can be
 * obtained by calling getActivity(), but make sure to call getActivity() only
 * after the fragment is attached to an activity. Otherwise the function will
 * return null.
 */
public class PrefFragment extends PreferenceFragment {

    public void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);

        /* Load preferences from an XML resource */
        addPreferencesFromResource(R.xml.settings);
    }
}
