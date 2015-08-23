package org.androidtutorials.design.navigationdrawer;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * This tutorial demonstrates the use of the Navigation Drawer. The Navigation Drawer
 * can be opened by swiping from left to right starting from the left side of the screen.
 * It is also possible to open the drawer via the hamburger icon in the upper left corner of the
 * screen. This icon is added when adding an instance of ActionBarDrawerToggle to the
 * DrawerLayout.
 *
 * The layout of the Drawer is defined in activity_main.xml under the NavigationView widget.
 * A static list of menu items is defined in an XML file placed in res/menu. The header of the
 * Navigation Drawer ise defined in an XML file in res/layout. The header is very customizable.
 * A good example would be Google's GMail App.
 *
 * After the drawer is opened, either by tapping the hamburger button or swiping,
 * tapping on a menu item pops up a toast letting the user know which
 * item has been selected. After a tap the drawer is closed.
 */

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Instantiate Toolbar */
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        /*
         * Obtain out DrawerLayout */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerRoot);
        /*
         * Setup DrawerLayout toggle events. This is done via a ActionBarDrawerToggle object.
         * This will tie ActionBarToggle to NavigationDrawer,
         * this will add an icon with three lines in the top left hand corner of the App */
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                mToolBar,
                R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        NavigationView nv = (NavigationView) findViewById(R.id.navView);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                String txt;
                switch (menuItem.getItemId()) {
                    case R.id.item_1:
                        txt = "Item #1 Selected";
                        break;
                    case R.id.item_2:
                        txt = "Item #2 Selected";
                        break;
                    case R.id.item_3:
                        txt = "Item #3 Selected";
                        break;
                    case R.id.item_4:
                        txt = "Item #4 Selected";
                        break;
                    case R.id.item_5:
                        txt = "Item #5 Selected";
                        break;
                    case R.id.item_6:
                        txt = "Item #6 Selected";
                        break;
                    default:
                        txt = "Invalid Item Selected";
                }
                Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_LONG).show();
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
         * Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * Handle action bar item clicks here. The action bar will
         * automatically handle clicks on the Home/Up button, so long
         * as you specify a parent activity in AndroidManifest.xml.
         */
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Settings Selected", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * onPostCreate is called when activity-startup is completed. It is called after onStart
     * and onmRestoreInstanceState. In some Activity LifeCycle diagrams you will not see this function
     * call. This is because the function onPostCreate is generally not overridden.
     *
     * Call syncState from MainActivity's onPostCreate. This will sync the hamburger icon
     * with the drawer state (i.e opened or closed). This function call is important when
     * the navigation drawer does not cover the Toolbar. In this case, instead of a hamburger icon,
     * you will see and arrow. The arrow will point to the right when the drawer is closed. And to the
     * left when after it is opened.*/
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
}
