package org.xmlvm.tutorial.android.service.started;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

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
         * Create a new notification with the help of the notification manager.
         * Set a title, icon and sound for the notification and make it
         * cancellable (i.e., the user can dismiss it from the status bar).
         */
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.ic_statuc_calc,
                "Fibonacci result available", System.currentTimeMillis());
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;

        /*
         * Create a notificationIntent that will be used to re-launch
         * FibonacciActivity. Note that the intent contains the result of the
         * computation.
         */
        Context context = getApplicationContext();
        CharSequence contentTitle = "Fibonacci";
        CharSequence contentText = "New result available";
        Intent notificationIntent = new Intent(this, FibonacciActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra("n", n);
        notificationIntent.putExtra("res", res);
        /*
         * The notificationIntent is wrapped by a contentIntent. The latter is
         * used to create the notification that will be displayed in the status
         * bar. When the user clicks on the notification, Android will send
         * notificationIntent that in turn will re-launch the application.
         */
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

        notificationManager.notify(1, notification);
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
