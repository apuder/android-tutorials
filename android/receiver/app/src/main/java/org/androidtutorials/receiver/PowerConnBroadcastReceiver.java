package org.androidtutorials.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * PowerConnBroadcastReceiver extends the BroadcastReceiver class. This receiver is registered
 * statically in AndroidManifest.xml with intent filter ACTION_POWER_DISCONNECTED and
 * ACTION_POWER_CONNECTED. Due to this onReceive() method of this class is invoked on power
 * connected and disconnected events.
 */
public class PowerConnBroadcastReceiver extends BroadcastReceiver {

    /**
     * onReceive() method is invoked if its intent filter matches the intent in a broadcast.
     * This method invokes the main activity whenever broadcast is received. Since the intent filter
     * is registered in the AndroidManifest.xml this method invokes MainActivity even if the app is
     * not running.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentNewActivity = new Intent(context, MainActivity.class);
        /**
         * "FLAG_ACTIVITY_NEW_TASK" flag needs to be set on the intent when startActivity() method is
         * invoked from outside Activity context.
         * "FLAG_ACTIVITY_SINGLE_TOP" flag will prevent a new instance of activity from being launched
         * when an existing activity is present on top of back stack. This flag needs to be set to
         * invoke onNewIntent() callback method instead of onCreate() callback method when activity
         * is relaunched.
         */
        intentNewActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        /**
         * We are passing key-value pairs as Extras to the new activity. This is used
         * in MainActivity.updateUIOnPower() method to determine whether power was connected and
         * disconnected.
         */
        intentNewActivity.putExtra("type", "PowerConnBroadcastReceiver");
        intentNewActivity.putExtra("status", intent.getAction());
        context.startActivity(intentNewActivity);
    }
}
