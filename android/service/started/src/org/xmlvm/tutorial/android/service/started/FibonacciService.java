package org.xmlvm.tutorial.android.service.started;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class FibonacciService extends IntentService {

    public FibonacciService() {
        super("FibonacciService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int n = intent.getExtras().getInt("n");
        int res = fib(n);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        showNotification(n, res);
    }

    private void showNotification(int n, int res) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.ic_statuc_calc,
                "Fibonacci result available", System.currentTimeMillis());
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;

        Context context = getApplicationContext();
        CharSequence contentTitle = "Fibonacci";
        CharSequence contentText = "New result available";
        Intent notificationIntent = new Intent(this, FibonacciActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra("n", n);
        notificationIntent.putExtra("res", res);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

        notificationManager.notify(1, notification);
    }

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
