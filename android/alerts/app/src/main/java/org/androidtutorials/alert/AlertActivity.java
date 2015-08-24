package org.androidtutorials.alert;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This tutorial shows two different kinds of alerts: popup, modal dialogs and
 * so-called toasts. A modal dialog has to be dismissed explicitly by the user
 * by pressing a button. There can be more than one button (e.g., a positive
 * (OK) and a negative (cancel) button. The action to be performed when the user
 * clicks a button is defined by a click listener.
 * 
 * The second type of alert is a so-called toast. A toast is a short visual
 * appearance of a text that is displayed in the lower half of the screen. A
 * toast does not have a button and it is automatically removed from the screen
 * after a short timeout.
 *
 * The third type an alert is a Snackbar. Similar to a Toast, Snackbars show a small message about
 * an operation but also have the ability to have actions attached to them. Just Like Toasts,
 * Snackbars will appear at the bottom of the screen. A Snackbar can appear for a short amount of
 * time and will be removed automatically. If however, Snackbar.LENGTH_INDEFINITE is passed into
 * the make function, the the Snackbar will not disappear unless dismissed via an Action.
 */
public class AlertActivity extends AppCompatActivity {

    private TextView lblAlertResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        lblAlertResult = (TextView) findViewById(R.id.lbl_alert_result);
    }

    public void showAlert(View view) {
        final TextView textView = lblAlertResult;

        /*
         * An AlertDialog builder is used to build up the details of the modal
         * dialog such as title, a message and an icon. It is possible to add
         * one or more buttons to the modal dialog.
         */
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("Sample Alert")
                .setMessage("This is a sample message. Proceed?")
                .setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog dlg = builder.create();
        dlg.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                textView.setText("Ok button pressed");

            }
        });

        dlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText("Cancel button pressed");
                    }
                });

        /*
         * Show the modal dialog. Once the user has clicked on a button, the
         * dialog is automatically removed.
         */
        dlg.show();
    }

    public void showToast(View view) {
        lblAlertResult.setText("");
        /*
         * This one-line statement is sufficient for rendering a toast. The
         * toast will automatically be removed from the screen after a short
         * timeout.
         */
        Toast.makeText(this, "This is Toast", Toast.LENGTH_LONG).show();
    }

    public void showSnackbar(View v){
        lblAlertResult.setText("");
        /*
         * Similar to a Toast, a Snackbars provides a message about an operation. But also
         * can have an action attached to it. A action can be added to the Snackbar by
         * passing the name of the action and a View.OnClickerListener to setAction.
         * For this example, a toast is shown when the Snackbar is dismissed.
         */
        final Snackbar sn = Snackbar.make(v, "This is a Snackbar", Snackbar.LENGTH_INDEFINITE);
        sn.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sn.dismiss();
                Toast.makeText(getApplicationContext(), "Snackbar Dismissed",
                        Toast.LENGTH_SHORT).show();
            }
        });

        sn.show();
    }
}