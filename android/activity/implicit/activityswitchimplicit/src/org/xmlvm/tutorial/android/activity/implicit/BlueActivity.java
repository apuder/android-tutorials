package org.xmlvm.tutorial.android.activity.implicit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BlueActivity extends LoggingActivity {

    public static final String ACTION_COLOR = "org.xmlvm.tutorial.intent.action.ACTION_COLOR";
    private TextView           resultField;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blue);

        resultField = (TextView) findViewById(R.id.result);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            int calls = data.getExtras().getInt("CALLS");
            resultField.setText("Calls to recently called: " + calls);
        }
    }

    public void onClick(View v) {
        resultField.setText("");
        Intent intent = new Intent(ACTION_COLOR);
        startActivityForResult(intent, 0);
    }
}