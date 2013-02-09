package org.xmlvm.tutorial.android.service.started;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FibonacciActivity extends Activity {

    private EditText edtInput;
    private TextView lblResult;

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

    public void compute(View v) {
        try {
            int n = Integer.parseInt(edtInput.getText().toString());
            Intent intent = new Intent(this, FibonacciService.class);
            intent.putExtra("n", n);
            startService(intent);
            finish();
        } catch (Exception exc) {
            lblResult.setText("Invalid input ...");
        }
    }
}