package org.androidtutorials.service.started;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * FibonacciService is an example of a started service. The service will
 * automatically be started whenever a client sends an intent and will also
 * automatically be terminated when the work item has been processed. Since the
 * application might have terminated while the service was still processing the
 * request, the results are posted as a notification. When the user taps on the
 * notification the corresponding application (FibonacciActivity in this case)
 * will automatically be launched.
 * 
 * FibonacciService is derived from IntentService which is a helper class to
 * implement a simple message queuing mechanism for Intents that will
 * automatically run in a background thread. IntentService is itself derived
 * from class Service.
 */
public class FibonacciService extends IntentService {

    public FibonacciService() {
        /*
         * The string parameter "FibonacciService" is used as the name of the
         * thread that will be created to process incoming intents.
         */
        super("FibonacciService");
    }

    /**
     * onHandleIntent() will be called by the base class IntentService whenever
     * there is an Intent to be processed. This method will be run in its own
     * thread (automatically created by IntentService) so it can safely run long
     * running operations. Intents are delivered one after another.
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        /*
         * Retrieve the parameter.
         */
        int n = intent.getExtras().getInt("n");
        /*
         * Perform the actual computation.
         */
        int res = fib(n);
        /*
         * Sleep for 5 seconds to mimic a long running operation.
         */
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        /*
         * Show the results of the computation via a status bar notification.
         */
        showNotification(n, res);
    }

    /**
     * showNotification() is a helper method that displays the result as a
     * status bar notification. If the user taps on the notification, the
     * corresponding application (FibonacciActivity) will automatically be
     * launched.
     */
    private void showNotification(int n, int res) {
        /*
         * Create a new notification with the help of the compatibility library.
         * Note that this requires the inclusion of android-support-v4.jar. Set
         * a title, icon and sound for the notification and make it cancellable
         * (i.e., the user can dismiss it from the status bar).
         */
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_status_calc).setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND).setContentTitle("Fibonacci")
                .setContentText("New result available");

        /*
         * Create a notificationIntent that will be used to re-launch
         * FibonacciActivity. Note that the intent contains the result of the
         * computation.
         */
        Intent resultIntent = new Intent(this, FibonacciActivity.class);
        resultIntent.putExtra("n", n);
        resultIntent.putExtra("res", res);

        /*
         * The stack builder object will contain an artificial back stack for
         * the started Activity. This ensures that navigating backward from the
         * FibonacciActivity leads out of the application to the Home screen.
         */
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        /*
         * Adds the back stack for the Intent (but not the Intent itself)
         */
        stackBuilder.addParentStack(FibonacciActivity.class);

        /*
         * Adds the Intent that starts the Activity to the top of the stack
         */
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    /**
     * Helper method to compute the Fibonacci value.
     */
    private int fib(int n) {
        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        return fib(n - 1) + fib(n - 2);
    }

}
