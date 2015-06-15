package org.androidtutorials.service.started;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * FibonacciActivity is an example of a started service. Once the application is
 * launched, type in an integer into the input field and press the button.
 * FibonacciActivity will trigger an Intent that will start the service. The
 * application will then deliberately exit (via Activity.finish()) to show what
 * happens when the service finished the computation.
 */
public class FibonacciActivity extends Activity {

    private EditText edtInput;
    private TextView lblResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        edtInput = (EditText) findViewById(R.id.edt_fib);
        lblResult = (TextView) findViewById(R.id.lbl_result);

        /*
         * Retrieve the extra parameters that came with the Intent that was used
         * to launch this activity. If the user launched this app from the
         * homescreen, there won't be any additional parameters. If however the
         * application was launched because the user tapped on the notification,
         * the Intent will have additional parameters that denote the result of
         * the service. In this case retrieve the results and show them on the
         * UI.
         */
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
            /*
             * The service is started via an Intent that has the integer value
             * as an extra parameter.
             */
            Intent intent = new Intent(this, FibonacciService.class);
            intent.putExtra("n", n);
            startService(intent);
            /*
             * Explicitly exit the application. This is done to show the effect
             * of the application not running when the service posts the results
             * as a notification.
             */
            finish();
        } catch (Exception exc) {
            lblResult.setText("Invalid input ...");
        }
    }
}