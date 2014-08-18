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

package org.xmlvm.tutorial.android.activity.fragment;

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
