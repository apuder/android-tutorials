package org.androidtutorials.settings;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;


/**
 * Settings are usually used to allow the user change the behavior of an app. In
 * Android, the settings dialog is a dedicated activity, whose UI is defined by
 * a special XML file that has to reside in the folder res/xml. In this example,
 * the description of the settings is stored in file res/xml/settings.xml. An
 * application-specific activity called SettingsActivity acts as a wrapper for
 * the settings activity. The settings can be retrieved via the
 * PreferenceManager class.
 */
public class MainActivity extends Activity {

    private TextView lblFullName;
    private TextView lblGender;
    private TextView lblLikeAndroid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        lblFullName = (TextView) findViewById(R.id.lbl_full_name);
        lblGender = (TextView) findViewById(R.id.lbl_gender);
        lblLikeAndroid = (TextView) findViewById(R.id.lbl_like_android);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displaySettings();
    }

    public void openSettings(View v) {
        /*
         * Opening the settings dialog is simply a matter of starting the
         * appropriate activity (SettingsActivity in this case).
         */
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void displaySettings() {
        /*
         * Individual settings can be retrieved via class PreferenceManager.
         */
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        /*
         * A specific setting is referenced via a key ('full_name') that has to
         * match the definition in res/xml/settings.xml.
         */
        String prefStr = pref.getString("full_name", "n.a.");
        lblFullName.setText(prefStr);

        prefStr = pref.getString("gender", "n.a.");
        lblGender.setText(prefStr);

        boolean b = pref.getBoolean("like_android", false);
        lblLikeAndroid.setText(b ? "Sure - nothing more than that!"
                : "Maybe after attending this class...");
    }
}