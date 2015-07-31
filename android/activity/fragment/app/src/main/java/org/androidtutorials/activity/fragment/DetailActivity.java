

package org.androidtutorials.activity.fragment;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * DetailActivity uses the R.layout.activity_detail layout as its main content
 * view. The latter references DetailFragment to show the details of a
 * particular fruit. This activity is only used in portrait mode when the
 * details are shown in its own activity. The parameters are added to the intent
 * that started this activity.
 */
public class DetailActivity extends Activity {

    public static final String EXTRA_FRUIT = "FRUIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * If DetailActivity is shown while in portrait mode when switching
         * orientation, Android will destroy and reconstruct it. When
         * subsequently DetailActivity gets reconstructed in landscape mode, we
         * finish immediately to give control back to MainActivity. The latter
         * will then use the side-by-side layout of the two fragments.
         */
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        setContentView(R.layout.activity_detail);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            /**
             * Retrieve the parameter (the fruit) that was passed with the
             * intent that started this activity.
             */
            String fruit = extras.getString(EXTRA_FRUIT);
            DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentById(
                    R.id.fragment_detail);
            detailFragment.setDetails(fruit);
        }
    }
}
