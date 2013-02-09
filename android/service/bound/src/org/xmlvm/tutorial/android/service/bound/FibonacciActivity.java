package org.xmlvm.tutorial.android.service.bound;

import android.app.Activity;
import android.content.ComponentName;
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

    final private class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            FibonacciService.LocalBinder binder = (FibonacciService.LocalBinder) service;
            fibonacciService = binder.getService();
            bound = true;
            fibonacciService.registerResultListener(FibonacciActivity.this);
            if (computationPending) {
                computationPending = false;
                fibonacciService.startComputation();
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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int n = extras.getInt("n", -1);
            int res = extras.getInt("res", -1);

            if (n != -1 && res != -1) {
                lblResult.setText("fib(" + n + "): " + res);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!bound) {
            Intent intent = new Intent(this, FibonacciService.class);
            bindService(intent, connection, 0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (bound) {
            fibonacciService.unregisterResultLister();
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
            int n = Integer.parseInt(edtInput.getText().toString());
            Intent intent = new Intent(this, FibonacciService.class);
            intent.putExtra("n", n);

            if (!bound) {
                startService(intent);
                computationPending = true;
                bindService(intent, connection, 0);
            } else {
                intent.putExtra("start", true);
                startService(intent);
            }
        } catch (Exception exc) {
            lblResult.setText("Invalid input ...");
        }
    }
}