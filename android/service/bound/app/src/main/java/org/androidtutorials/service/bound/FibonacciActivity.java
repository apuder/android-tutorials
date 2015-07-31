package org.androidtutorials.service.bound;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * FibonacciActivity shows how to bind to a service. Once bound to a service, it
 * is possible to call public methods of the service. Binding to the service
 * happens in onCreate() and the unbinding in onDestroy(). Unbinding to the
 * service will also destroy it since no other clients will bind to it. Binding
 * to a service is an asynchronous operation and requires a ServiceConnection
 * object. After launching the application, enter an integer and click the
 * button. 5 seconds later the result will be shown on the main UI. If the
 * application is exited within the 5 seconds, the result is discarded.
 */
public class FibonacciActivity extends Activity implements FibonacciResultListener {

    private EditText         edtInput;
    private TextView         lblResult;
    private FibonacciService fibonacciService;
    private boolean          bound;
    private boolean          computationPending = false;
    private int              n                  = -1;

    /**
     * Class FibonacciServiceConnection derives from ServiceConnection and it is
     * used to bind to FibonacciService. Binding to a service is not
     * instantaneous but may take a short time. FibonacciServiceConnection has
     * callback methods that notify when a connection to the service is
     * established and when that connection is disconnected.
     */
    final private class FibonacciServiceConnection implements ServiceConnection {

        /**
         * onServiceConnected will be called whenever the connection to a
         * service has been established. The parameters are the class name of
         * the service and a reference to the IBinder interface. The latter can
         * be used to retrieve the FibonacciService instance.
         */
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            FibonacciService.LocalBinder binder = (FibonacciService.LocalBinder) service;
            /*
             * Retrieve the FibonacciService instance and save it. The reference
             * to this instance can be used to invoke methods on the service.
             */
            fibonacciService = binder.getService();
            bound = true;
            /*
             * Register the current activity as a callback that the service will
             * use to report back results when they are ready.
             */
            fibonacciService.registerResultListener(FibonacciActivity.this);
            if (computationPending) {
                /*
                 * If computationPending is true it means that the user clicked
                 * on the button to trigger the computation of a Fibonacci
                 * number before we bound to the FibonacciService. Since we are
                 * bound now, start the postponed computation now.
                 */
                computationPending = false;
                fibonacciService.startComputation(n);
            }
        }

        /**
         * Callback that notifies us that the connection to the service was
         * disconnected. This can happen for example when memory is low and
         * Android decides to kill the service.
         */
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            fibonacciService = null;
            bound = false;
        }
    }

    private ServiceConnection connection = new FibonacciServiceConnection();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        edtInput = (EditText) findViewById(R.id.edt_fib);
        lblResult = (TextView) findViewById(R.id.lbl_result);

        bindToFibonacciService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (bound) {
            /*
             * When the activity gets destroyed, we also unbind from the
             * service. Since this activity is the only client to bind to
             * FibonacciService, the service will be destroyed as well.
             */
            unbindService(connection);
            bound = false;
        }
    }

    /**
     * This method will be called by the service to report back results after
     * the Fibonacci value has been computed. Note that the callback happens in
     * the context of another thread (not the UI thread) so View.post() is used
     * to post the update to the UI thread.
     */
    public void resultAvailable(final int n, final int res) {
        lblResult.post(new Runnable() {

            @Override
            public void run() {
                lblResult.setText("fib(" + n + "): " + res);
            }
        });
    }

    /**
     * Method compute will be called whenever the user clicks on the button.
     */
    public void compute(View v) {
        try {
            /*
             * Read and parse the integer value.
             */
            n = Integer.parseInt(edtInput.getText().toString());

            if (bound) {
                /*
                 * We are already bound to the service. Simply call the public
                 * method startComputation() to trigger the computation of the
                 * Fibonacci value.
                 */
                fibonacciService.startComputation(n);
            } else {
                /*
                 * We are currently not bound to the service. Set the flag
                 * computationPending to true to ensure that the computation
                 * will be triggered once we are bound to the service. Then bind
                 * to the service.
                 */
                computationPending = true;
                bindToFibonacciService();
            }
        } catch (Exception exc) {
            lblResult.setText("Invalid input ...");
        }
    }

    private void bindToFibonacciService() {
        if (!bound) {
            /*
             * Binding to a service happens via an Intent. If the service isn't
             * already running, it will be created. Binding to a service happens
             * asynchronously and the 'connection' object is used to notify when
             * the binding has succeeded.
             */
            Intent intent = new Intent(this, FibonacciService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }
}