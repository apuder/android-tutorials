package org.xmlvm.tutorial.android.service.bound;

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

public class FibonacciActivity extends Activity implements FibonacciResultListener {

    private EditText         edtInput;
    private TextView         lblResult;
    private FibonacciService fibonacciService;
    private boolean          bound;
    private boolean          computationPending = false;
    private int              n                  = -1;

    final private class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            FibonacciService.LocalBinder binder = (FibonacciService.LocalBinder) service;
            fibonacciService = binder.getService();
            bound = true;
            fibonacciService.registerResultListener(FibonacciActivity.this);
            if (computationPending) {
                computationPending = false;
                fibonacciService.startComputation(n);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            fibonacciService = null;
            bound = false;
        }
    };

    private ServiceConnection connection = new MyServiceConnection();

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
            unbindService(connection);
            bound = false;
        }
    }

    public void resultAvailable(final int n, final int res) {
        lblResult.post(new Runnable() {

            @Override
            public void run() {
                lblResult.setText("fib(" + n + "): " + res);
            }
        });
    }

    public void compute(View v) {
        try {
            n = Integer.parseInt(edtInput.getText().toString());

            if (bound) {
                fibonacciService.startComputation(n);
            } else {
                computationPending = true;
                bindToFibonacciService();
            }
        } catch (Exception exc) {
            lblResult.setText("Invalid input ...");
        }
    }

    private void bindToFibonacciService() {
        if (!bound) {
            Intent intent = new Intent(this, FibonacciService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }
}