

package org.androidtutorials.sensor.nfcreader;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * NfcTagReader is a simple NFC tag reader. It is limited in the following ways:
 * (1) it can only handle NFC tags that are formatted with NDEF (NFC Data
 * Exchange Format). This application will not work with other NFC technologies.
 * (2) only tags that contain a URI can be handled. See AndroidManifest.xml for
 * the definition of the intent filter. Whenever an NDEF tag containing a Uri is
 * held close to the device, it will log its content. Note that this application
 * does not need to run in the foreground. Android will automatically launch
 * this activity if it should not be already running.
 */
public class NfcTagReader extends Activity {

    private TextView log = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_nfc, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        handleIntent(getIntent());
    }

    /**
     * When Android wants to dispatch an NDEF message to an activity that has
     * already been created, it will call the onNewIntent() callback. All that
     * happens here is that the intent is set for the current callback. Android
     * will call onResume() next which will trigger the actual parsing of the
     * NDEF message.
     */
    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    private void log(String msg) {
        if (log == null) {
            log = (TextView) this.findViewById(R.id.log);
        }
        log.setText(log.getText() + msg + "\n");
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        /*
         * Only handle intents whose action is ACTION_NDEF_DISCOVERED.
         */
        if (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {
            /*
             * Retrieve the raw message.
             */
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                /*
                 * Convert the raw messages to an array of NdefMessage.
                 */
                NdefMessage[] msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
                dumpTag(msgs);
            }
        }
    }

    private void dumpTag(NdefMessage[] msgs) {
        if (msgs == null || msgs.length == 0) {
            return;
        }
        for (NdefMessage msg : msgs) {
            log("NDEF message with " + msg.getRecords().length + " records");
            for (NdefRecord rec : msg.getRecords()) {
                /*
                 * Go through all records of the current NDEF message.
                 */
                short tnf = rec.getTnf();
                byte[] payload = rec.getPayload();
                log("Record TNF: " + tnf);
                if (tnf == NdefRecord.TNF_WELL_KNOWN) {
                    if (Arrays.equals(rec.getType(), NdefRecord.RTD_URI)) {
                        /*
                         * This demo application can only handle records of type
                         * TNF_WELL_KNOWN and RTD_URI. Note that TNF (Type Name
                         * Field) just gives a high-level type. The precise type
                         * is given by the RTD (Record Type Definition) field.
                         */
                        log("NdefRecord with TNF_WELL_KNOWN and RTD_URI");
                        /*
                         * For this particular record type, the payload directly
                         * translates to a URI.
                         */
                        String uri = new String(payload, Charset.forName("UTF-8"));
                        log("Payload: " + uri);
                    } else {
                        log("This RTD type is not handled");
                    }
                } else {
                    log("This TNF is not handled");
                }
            }
            log("");
        }
    }
}
