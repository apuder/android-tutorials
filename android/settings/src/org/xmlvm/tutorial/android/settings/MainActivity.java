package org.xmlvm.tutorial.android.settings;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

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
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void displaySettings() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        String prefStr = pref.getString("full_name", "n.a.");
        lblFullName.setText(prefStr);

        prefStr = pref.getString("gender", "n.a.");
        lblGender.setText(prefStr);

        boolean b = pref.getBoolean("like_android", false);
        lblLikeAndroid.setText(b ? "Sure - nothing more than that!"
                : "Maybe after attending this workshop ...");
    }
}