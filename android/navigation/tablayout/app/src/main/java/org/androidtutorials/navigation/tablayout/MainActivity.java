package org.androidtutorials.navigation.tablayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.androidtutorials.navigation.tablayout.R;


/**
 * This application shows the usage of the design library's TabLayout.
 * TabLayout should be used when an application needs to switch between
 * equally important views. This tutorial shows how to setup the TabLayout bar and
 * add tabs. Eight tabs are added to demonstrate scrolling and auto-scrolling of the
 * TabLayout bar. To detect when tabs are selected, unselected,
 * or reselected, an OnTabSelectedListener is attached to the TabLayout bar. When a tab is
 * selected a Toast is shown displaying which tab is selected. In addition to the Toast, Log.d
 * statements are executed to show which functions are called when selecting or re-selecting tabs.
 */
public class MainActivity extends AppCompatActivity {
    private TabLayout    mTabLayout;
    private Toolbar      mToolBar;
    private final String TAG = "TabLayout";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * Instantiate Toolbar 
         */
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        /*
         * Configure TabLayout. Set its current mode and gravity.
         */
        mTabLayout = (TabLayout) findViewById(R.id.bar_tabLayout);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        /*
         * Set colors for tab text, Black for selected Tab,
         * White for Unselected tabs.
         */
        mTabLayout.setTabTextColors(Color.WHITE, Color.BLACK);
        /*
         * Add tabs to TabLayout Bar. Added 8 tabs to demonstrate
         * scrolling through tabs and auto scrolling when selecting
         * certain tabs. 
         */
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab #1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab #2"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab #3"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab #4"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab #5"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab #6"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab #7"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Tab #8"));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
               /*
                * Called when a tab is reselected by the user
                */
                Log.d(TAG, "onTabReselected Called");
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                /*
                 * Called when a tab is selected by the user
                 */
                Log.d(TAG, "onTabSelected Called");
                Toast.makeText(getApplicationContext(), tab.getText() + " Selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                /*
                 * Called when a tab exits the selected state
                 */
                Log.d(TAG, "onTabUnselected Called");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Settings Selected", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
