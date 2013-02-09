package org.xmlvm.tutorial.android.service.bound;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

public class FibonacciService extends Service {

    private HandlerThread           thread;
    private ServiceHandler          serviceHandler;
    private IBinder                 binder = new LocalBinder();
    private FibonacciResultListener resultListener;

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
            int n = msg.arg1;
            int res = fib(n);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            if (resultListener != null) {
                resultListener.resultAvailable(n, res);
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        serviceHandler = new ServiceHandler(thread.getLooper());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.quit();
        resultListener = null;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return binder;
    }

    public void startComputation(int n) {
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = n;
        serviceHandler.sendMessage(msg);
    }

    public void registerResultListener(FibonacciResultListener listener) {
        resultListener = listener;
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
