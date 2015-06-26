
package org.androidtutorials.activity.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * FruitListFragment is the primary fragment that shows a list of options the
 * user can choose from. The details of what the user has selected is shown by a
 * different fragment (DetailFragment). When in portrait mode, this is the only
 * fragment shown to the user. This example uses ListFragment as its base class
 * which uses a ListView to show a list of options. It is possible to derive
 * directly from base class Fragment for any kind of layout. See DetailFragment
 * that shows a custom layout.
 */
public class FruitListFragment extends ListFragment {

    private String[] fruits = new String[] { "Apple", "Pear", "Plum", "Peach", "Orange", "Cherry" };

    /**
     * Base class ListFragment will call this method whenever the user tabs on
     * an item in the ListView.
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String fruit = fruits[position];
        /**
         * Try to find the DetailFragment in our layout. This will only succeed
         * when the device is in landscape mode where DetailFragment is shown
         * side-by-side with this fragment.
         */
        DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(
                R.id.fragment_detail);
        if (fragment != null && fragment.isInLayout()) {
            /**
             * We found DetailFragment. The device is therefore in landscape
             * mode and we currently show FragmentDetail as part of the UI.
             * Directly calls its public method to show the details of what has
             * been selected.
             */
            fragment.setDetails(fruit);
        } else {
            /**
             * The device is in portrait mode and DetailFragment is currently
             * not shown. Start activity DetailActivity via an explicit intent
             * to show the details of what the user selected on a separate
             * screen. The fruit that was selected is added as a parameter to
             * the intent.
             */
            Intent intent = new Intent(this.getActivity(), DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_FRUIT, fruit);
            startActivity(intent);
        }
    }

    /**
     * onCreateView() will initialize the view (a ListView in this case) with
     * some pre-defined fruits. As common for a ListView, its content is defined
     * via an ArrayAdapter.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(),
                android.R.layout.simple_list_item_1, fruits);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
