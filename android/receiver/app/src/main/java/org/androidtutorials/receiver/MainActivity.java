package org.androidtutorials.receiver;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This application demonstrates the use of BroadcastReceiver to receive system generated
 * announcements. The app also demonstrates broadcasting and receiving a custom broadcast. In
 * particular the app registers a receiver for Wi-Fi and Bluetooth state changes. When user toggles
 * Wi-Fi or Bluetooth, the app updates the UI to indicate that the broadcast has been received.
 * For custom broadcast, a button is provided , when clicked the app broadcasts a custom message through
 * an intent object. The reception of this broadcast is also indicated on the UI.
 * This app also launches automatically when power is connected or disconnected. The corresponding
 * receivers are registered in AndroidManifest.xml.
 */
public class MainActivity extends Activity {

    /**
     * Entry point of the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateUIOnPower(getIntent());
    }

    /**
     * onNewIntent() is a callback method that is invoked when this activity (MainActivity) is
     * relaunched instead of creating a new instance of the existing activity by calling onCreate()
     * method. This method is invoked when "FLAG_ACTIVITY_SINGLE_TOP" flag is set on the intent while
     * starting the activity or if launchMode attribute is set to "singleTop" in AndroidManifest.xml.
     * Here we are updating the UI to let the user know that the activity was launched when a broadcast
     * was received due to power being connected or disconnected.
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        updateUIOnPower(intent);
    }

    /**
     * updateUIOnPower() method is a custom method invoked from onCreate() and onNewIntent() callback
     * methods. It is invoked from onCreate() method if power connects/disconnects when app is not
     * running  and invoked from onNewIntent() method if power connects/disconnects when app is
     * running or is in background.
     */
    public void updateUIOnPower(Intent intent) {

        if (intent.hasExtra("type")
                && intent.getExtras().getString("type").equals("PowerConnBroadcastReceiver")) {
            if (intent.getExtras().getString("status").equals(Intent.ACTION_POWER_CONNECTED)) {
                TextView textViewParent = (TextView) findViewById(R.id.textViewParent);
                textViewParent.setText("Power connected receiver");
                textViewParent.setTextColor(Color.GREEN);
            } else if (intent.getExtras().getString("status").equals(Intent.ACTION_POWER_DISCONNECTED)) {
                TextView textViewParent = (TextView) findViewById(R.id.textViewParent);
                textViewParent.setText("Power disconnected receiver");
                textViewParent.setTextColor(Color.RED);
            }


        }
    }

    /**
     * onResume() callback method is invoked when the activity is becoming visible to user.
     * Here we register all the receivers.
     */
    @Override
    protected void onResume() {
        super.onResume();

        /**
         * Create an intent filter with action set as ACTION_STATE_CHANGED to receive broadcast when
         * Bluetooth is toggled. Register the receiver using above intent filter.
         */
        IntentFilter intentFilterBT = new IntentFilter();
        intentFilterBT.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(broadcastReceiverBT, intentFilterBT);

        /**
         * Create an intent filter with action set as a custom defined string to receive broadcast that
         * has the exact same string in its intent object.
         * Register the receiver using above intent filter.
         */
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getPackageName() + ".uniqueIntentCustom");
        registerReceiver(broadcastReceiver, intentFilter);

        /**
         * Create an intent filter with action as WIFI_STATE_CHANGED_ACTION to receive broadcast when
         * Wi-Fi is toggled.
         * Register the receiver using above intent filter.
         */
        IntentFilter intentFilterWiFi = new IntentFilter();
        intentFilterWiFi.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(broadcastReceiverWiFi, intentFilterWiFi);
    }

    /**
     * Instantiate an object of a class that extends BroadcastReceiver Class.
     * The overridden onReceive() method is invoked when a broadcast with matching intent is received.
     * In this case, UI is updated when broadcast is received.
     */
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Toast.makeText(MainActivity.this, "Custom broadcast received: " + action, Toast.LENGTH_SHORT).show();
            TextView textViewCustom = (TextView) findViewById(R.id.textViewCustom);
            textViewCustom.setText("Yes!");
            textViewCustom.setTextColor(Color.GREEN);
        }
    };

    /**
     * Instantiate an object of a class that extends BroadcastReceiver Class.
     * The overridden onReceive() method is invoked when a broadcast with matching intent is received.
     * In this case, UI is updated when broadcast is received.
     */
    private final BroadcastReceiver broadcastReceiverBT = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            /**
             * The intent received with the broadcast contains key-value pairs. One such key is
             * EXTRA_STATE which indicates the current state of bluetooth.
             */
            int status = intent.getExtras().getInt(BluetoothAdapter.EXTRA_STATE);
            TextView textViewBT = (TextView) findViewById(R.id.textViewBT);

            if (status == BluetoothAdapter.STATE_ON) {
                Toast.makeText(MainActivity.this, "Bluetooth turned on.", Toast.LENGTH_SHORT).show();
                textViewBT.setText("Online");
                textViewBT.setTextColor(Color.GREEN);
            } else if (status == BluetoothAdapter.STATE_OFF) {
                Toast.makeText(MainActivity.this, "Bluetooth turned off.", Toast.LENGTH_SHORT).show();
                textViewBT.setText("Offline");
                textViewBT.setTextColor(Color.RED);
            }
        }
    };

    /**
     * Instantiate an object of a class that extends BroadcastReceiver Class.
     * The overridden onReceive() method is invoked when a broadcast with matching intent is received.
     * In this case, UI is updated when broadcast is received.
     */
    private final BroadcastReceiver broadcastReceiverWiFi = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            /**
             * The intent received with the broadcast contains key-value pairs. One such key is
             * EXTRA_WIFI_STATE which indicates the current state of Wi-Fi.
             */
            int status = intent.getExtras().getInt(WifiManager.EXTRA_WIFI_STATE);
            TextView textViewWiFi = (TextView) findViewById(R.id.textViewWiFi);
            if (status == WifiManager.WIFI_STATE_ENABLED) {
                Toast.makeText(MainActivity.this, " WiFi turned on.", Toast.LENGTH_SHORT).show();
                textViewWiFi.setText("Online");
                textViewWiFi.setTextColor(Color.GREEN);
            }else if (status == WifiManager.WIFI_STATE_DISABLED) {
                Toast.makeText(MainActivity.this, " WiFi turned off.", Toast.LENGTH_SHORT).show();
                textViewWiFi.setText("Offline");
                textViewWiFi.setTextColor(Color.RED);
            }
        }
    };

    /**
     * onPause() is callback method invoked when the activity is no longer in foreground. In this case
     * the receivers are unregistered to conserve resources. onPause() method should be kept light
     * weight to keep the UI transition smooth.
     */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(broadcastReceiverBT);
        unregisterReceiver(broadcastReceiverWiFi);
    }

    /**
     * This is a custom method invoked when "Send custom broadcast" button is clicked. This method
     * instantiates an intent object with action set as a unique string. The intent object is then
     * used to broadcast using sendBroadcast() method. If another application has registered for the
     * same custom broadcast, it would also be notified.
     */
    public void sendCustomBroadcast(View view) {
        Intent intent = new Intent();
        intent.setAction(getPackageName() + ".uniqueIntentCustom");
        sendBroadcast(intent);
    }

    /**
     * This is a callback method used to inflate menu layout.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * The clear option in overflow menu will set labels "Received custom broadcast?"  and
     * "Activity invoked by receiver?" to default of "No".
     * This provides an opportunity for user to trigger the actions again and observe.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear:
                clearFields();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Custom method invoked when clear option is clicked in the overflow menu.
     */
    public void clearFields() {
        TextView textViewCustom = ((TextView) findViewById(R.id.textViewCustom));
        textViewCustom.setText("No");
        textViewCustom.setTextColor(Color.RED);

        TextView textViewParent = ((TextView) findViewById(R.id.textViewParent));
        textViewParent.setText("Connect or disconnect power to update");
        textViewParent.setTextColor(Color.RED);
    }


}
