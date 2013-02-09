package org.xmlvm.tutorial.android.service.bound;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

public class FibonacciService extends Service {

    private ServiceHandler          serviceHandler;
    private IBinder                 binder = new LocalBinder();
    private FibonacciResultListener resultListener;
    private Message                 msg;

    public class LocalBinder extends Binder {

        public FibonacciService getService() {
            return FibonacciService.this;
        }
    }

    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            int n = msg.arg2;
            int res = fib(n);
            if (resultListener == null) {
                showNotification(n, res);
            } else {
                resultListener.resultAvailable(n, res);
            }

            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        serviceHandler = new ServiceHandler(thread.getLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.arg2 = intent.getIntExtra("n", -1);

        boolean start = intent.getBooleanExtra("start", false);
        if (start) {
            startComputation();
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return binder;
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

    public void startComputation() {
        serviceHandler.sendMessage(msg);
    }

    public void registerResultListener(FibonacciResultListener listener) {
        resultListener = listener;
    }

    public void unregisterResultLister() {
        resultListener = null;
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
