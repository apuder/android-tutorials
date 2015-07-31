package org.androidtutorials.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.androidtutorials.handler.R;

/**
 * This application creates a Handler that is associated with the UI thread.
 * This Handler can then be used to post messages from a background thread. The
 * posting of messages can be done in any thread (not necessarily the UI
 * thread). This is an example on how to safely update the UI from a background
 * thread.
 */
public class FibonacciActivity extends Activity {

    /**
     * Class UIHandler overrides method handleMessage() that will be called by
     * the Looper whenever a message needs to be dispatched to UIHandler.
     */
    private final class UIHandler extends Handler {

        public void handleMessage(Message msg) {
            /*
             * Retrieve the contents of the message and then update the UI
             * (i.e., lblResult).
             */
            int n = msg.arg1;
            int res = msg.arg2;
            lblResult.setText("fib(" + n + "): " + res);
        }

    }

    private EditText      edtInput;
    private TextView      lblResult;

    /*
     * This is where UIHandler is created. Note that this happens as part of the
     * instantiation of FibonacciActivity just prior to calling of onCreate().
     * Since the instantiation of FibonacciActivity happens in the context of
     * the UI thread, this UIHandler instance will be associated with the Looper
     * of the UI thread. Also note that the Looper for the UI thread is
     * automatically created by Android.
     */
    final private Handler handler = new UIHandler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        edtInput = (EditText) findViewById(R.id.edt_fib);
        lblResult = (TextView) findViewById(R.id.lbl_result);
    }

    public void compute(View v) {
        try {
            /*
             * Read the integer value the user provided.
             */
            final int n = Integer.parseInt(edtInput.getText().toString());
            /*
             * Perform the long running background operation in a separate
             * thread so that the UI thread will not be blocked.
             */
            new Thread(new Runnable() {

                @Override
                public void run() {
                    /*
                     * Perform the long running operation. Since we are running
                     * in another thread (not the UI thread) it would be
                     * incorrect to update the UI (i.e., lblResult) right here.
                     */
                    int res = fib(n);
                    /*
                     * Sleep for 5 seconds to fake a long running operation.
                     * Since this is done in a background thread, it is not a
                     * problem.
                     */
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                    /*
                     * Use the handler to create a message.
                     */
                    Message msg = handler.obtainMessage();
                    /*
                     * Class Message can store two integer values that can be
                     * passed as parameters. If the data that needs to be passed
                     * is more complex, use Message.setData()
                     */
                    msg.arg1 = n;
                    msg.arg2 = res;
                    /*
                     * Finally send the message. This will queue the message
                     * with the looper associated with the handler (i.e., in
                     * this case the looper of the UI thread). The looper will
                     * eventually dispatch the message by calling
                     * UIHandler.handleMessage(). Note that sendMessage() is not
                     * a blocking operation.
                     */
                    handler.sendMessage(msg);
                }
            }).start();
        } catch (Exception exc) {
            lblResult.setText("Invalid input ...");
        }
    }

    /**
     * Compute the Fibonacci value of an integer. This method is meant as an
     * example of a long running operation.
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