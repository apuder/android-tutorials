

package org.androidtutorials.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    /**
     * The handler will be used to trigger an operation on the UI thread after a
     * short delay of 5 seconds.
     */
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Clicking on the button will trigger a notification after a 5 seconds
         * timeout. Note the use of a Handler to execute a Runnable on the UI
         * thread after a delay.
         */
        Button button = (Button) this.findViewById(R.id.notify_button);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Notification will show in 5 seconds",
                        Toast.LENGTH_SHORT).show();
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        /**
                         * 5 seconds are up. Show the notification.
                         */
                        showNotification("Hello World!");
                    }
                }, 5000);
            }

        });

        /**
         * Retrieve the Intent that was used to launch this activity.
         */
        Intent intent = getIntent();

        /**
         * Try to get the string parameter called 'param'. This parameter is set
         * in showNotification().
         */
        String param = intent.getStringExtra("param");
        if (param != null) {

            /**
             * There was a string parameter called 'param'. This means that this
             * activity was launched in response to the user tapping on the
             * notification in the status bar. Show the parameter in a TextView
             * on the main screen.
             */
            TextView resultView = (TextView) this.findViewById(R.id.result);
            resultView.setText("Started via notification with param: " + param);
        }
    }

    /**
     * showNotification() is a helper method that displays a status bar
     * notification. If the user taps on the notification, this activity (i.e.,
     * MainActivity) will automatically be launched.
     */
    private void showNotification(String param) {

        /**
         * Create a new notification with the help of the compatibility library.
         * Note that this requires the inclusion of android-support-v4.jar in
         * the 'libs' folder. Set a title, icon and sound for the notification
         * and make it cancellable (i.e., the user can dismiss it from the
         * status bar).
         */
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_status_calc).setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND).setContentTitle("Notification Demo")
                .setContentText("New result available");

        /**
         * Create a notificationIntent that will be used to re-launch
         * MainActivity. Add a string parameter called 'param' to the Intent.
         */
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra("param", param);

        /**
         * The stack builder object will contain an artificial back stack for
         * the started activity. This ensures that navigating backward from the
         * MainActivity leads out of the application to the Home screen.
         */
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        /**
         * Adds the back stack for the Intent (but not the Intent itself)
         */
        stackBuilder.addParentStack(MainActivity.class);

        /**
         * Adds the Intent that starts the MainActivity to the top of the stack
         */
        stackBuilder.addNextIntent(resultIntent);

        /**
         * Generate a PendingIntent from the TaskStackBuilder. The PendingIntent
         * essentially is a wrapper for resultIntent that will be fired when the
         * user taps on the notification in the status bar.
         */
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        /**
         * Use the NotificationManager to show the Notification.
         */
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

}
