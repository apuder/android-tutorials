package org.androidtutorials.service.bound;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

/**
 * FibonacciService is an example of a bound service. The service will be
 * automatically created when the first client (i.e., FibonacciActivity)
 * connects and also automatically destroyed when the last client disconnects. A
 * bound service needs to implement onBind() and return a so-called binder that
 * the client can use to retrieve a reference to the service.
 */
public class FibonacciService extends Service {

    private HandlerThread           thread;
    private ServiceHandler          serviceHandler;
    private IBinder                 binder = new LocalBinder();
    private FibonacciResultListener resultListener;

    /**
     * This example uses a local binder. The purpose of the binder is to return
     * a reference to the service that the client can use to invoke methods on
     * the service. A local binder can only be used by activities that run in
     * the same process as the service.
     */
    public class LocalBinder extends Binder {

        public FibonacciService getService() {
            return FibonacciService.this;
        }
    }

    /**
     * The ServiceHandler performs the actual work (i.e., compute the Fibonacci
     * value). It is used by a Looper that is associated with a HandlerThread.
     * The Looper will dispatch messages to this ServiceHandler.
     */
    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        /**
         * The looper will call handleMessage() when it dispatches a new
         * message.
         */
        @Override
        public void handleMessage(Message msg) {
            int n = msg.arg1;
            /*
             * Do the actual Fibonacci computation. Then sleep for 5 seconds to
             * mimic a long running operation.
             */
            int res = fib(n);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            /*
             * If the resultListener is null it means that the activity that
             * triggered the computation of the Fibonacci value has been
             * destroyed in the meantime. In this case simply discard the
             * result.
             */
            if (resultListener != null) {
                /*
                 * Report the result back to the activity that triggered the
                 * computation. Note that this callback happens in the context
                 * of a HandlerThread and not the UI thread.
                 */
                resultListener.resultAvailable(n, res);
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /*
         * Create a new HandlerThread. A HandlerThread is simply a Java Thread
         * with an associated Looper.
         */
        thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        /*
         * Create the ServiceHandler and pass to it the Looper of the
         * HandlerThread.
         */
        serviceHandler = new ServiceHandler(thread.getLooper());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*
         * Calling quit() will cause the Looper to exit at the next possible
         * moment. This will also terminate the HanderThread.
         */
        thread.quit();
        resultListener = null;
    }

    /**
     * Implementing onBind() marks this service as a bound service that other
     * clients can bind to.
     */
    @Override
    public IBinder onBind(Intent arg0) {
        return binder;
    }

    /**
     * startComputation() is a public method that will be called by
     * FibonacciActivity to trigger the computation of a Fibonacci value. Note
     * that this method merely queues a new message with the ServiceHandler. The
     * actual computation happens later when the Looper dispatches the message.
     */
    public void startComputation(int n) {
        /*
         * Get a new message.
         */
        Message msg = serviceHandler.obtainMessage();
        /*
         * Since we only have a simply integer argument, we can use arg1 for
         * this purpose.
         */
        msg.arg1 = n;
        /*
         * Enqueue the message.
         */
        serviceHandler.sendMessage(msg);
    }

    public void registerResultListener(FibonacciResultListener listener) {
        resultListener = listener;
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
