package org.xmlvm.tutorial.android.alert;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AlertActivity extends Activity {

    private TextView lblAlertResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        lblAlertResult = (TextView) findViewById(R.id.lbl_alert_result);
    }

    public void showAlert(View view) {
        final TextView textView = lblAlertResult;

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

        dlg.show();
    }

    public void showToast(View view) {
        lblAlertResult.setText("");
        Toast.makeText(this, "This is Toast", Toast.LENGTH_LONG).show();
    }
}