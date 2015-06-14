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

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Simple fragment that shows the details of what has been selected. This
 * particular example does not show how DetailFragment can communicate back with
 * its referencing activity (which should happen via a Java interface). For
 * details on how to do this, see:
 * http://developer.android.com/guide/components/fragments.html#CommunicatingWithActivity
 */
public class DetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * DetailFragment uses a custom layout referenced via R.layout.fragment_detail. The
         * layout is inflated in a container that can either be the content view defined
         * by DetailActivity (when the device is in portrait mode) or as part of
         * res/layout-land/activity_main.xml (when the device is in landscape mode).
         */
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        return view;
    }

    public void setDetails(String fruit) {
        TextView view = (TextView) getView().findViewById(R.id.fruit_details);
        view.setText("Details for fruit: " + fruit);
    }
}
