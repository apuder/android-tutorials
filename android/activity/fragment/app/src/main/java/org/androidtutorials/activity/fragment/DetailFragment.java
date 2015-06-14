

package org.androidtutorials.activity.fragment;

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
