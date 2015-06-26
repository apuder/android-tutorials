

package org.androidtutorials.navigation.tabbar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * A helper activity to show which tab was selected. The selected tab will be
 * displayed via a TextView in the main view of this activity.
 */
public class DisplayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        TextView lblTab = (TextView) findViewById(R.id.lblTab);
        lblTab.setText(getIntent().getCharSequenceExtra("TAB"));
    }

}
