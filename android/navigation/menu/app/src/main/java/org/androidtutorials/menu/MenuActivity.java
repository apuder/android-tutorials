

package org.androidtutorials.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * This application demonstrates the use of Android's physical menu button that
 * each Android device possesses. The menu will show in the bottom half of the
 * screen when pressing the menu button. In this example, the menu consists of
 * three entries: Add, Delete, Search. Selecting a specific menu option will
 * display the selected option in a TextView on the main view.
 */
public class MenuActivity extends Activity {

    private TextView lblItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(org.androidtutorials.menu.R.layout.main);

        lblItem = (TextView) findViewById(org.androidtutorials.menu.R.id.lblItem);
    }

    /*
     * Initialize the contents of the Activity's standard options menu. You
     * should place your menu items in to 'menu'. The default implementation
     * populates the menu with standard system menu items.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
         * Retrieve the MenuInflater for this activity. It is used to
         * instantiate menu XML files into Menu objects.
         */
        MenuInflater inflater = getMenuInflater();
        /*
         * Load the file res/menu/options.xml
         */
        inflater.inflate(org.androidtutorials.menu.R.menu.options, menu);
        return true;
    }

    /*
     * This method will be called whenever the user selected an option from the
     * menu.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * Depending on which of the three menu options the user selected, set
         * an appropriate label in the main view's TextView.
         */
        switch (item.getItemId()) {
        case org.androidtutorials.menu.R.id.mnuAdd:
            lblItem.setText("Add");
            return true;
        case org.androidtutorials.menu.R.id.mnuDelete:
            lblItem.setText("Delete");
            return true;
        case org.androidtutorials.menu.R.id.mnuSearch:
            lblItem.setText("Search");
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

}