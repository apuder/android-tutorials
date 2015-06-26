
package org.androidtutorials.sensor.gyroscope;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * This application demonstrates access to the gyroscope of Android devices. The
 * rate of rotation along (x, y, z) axis are shown in TextViews on the main
 * screen of the activity. Note that GyroActivity implements the interface
 * android.hardware.SensorEventListener. Implementing this interface requires to
 * override methods onAccuracyChanged() and onSensorChanged(). Note that not all
 * devices support the Gyroscope sensor.
 * 
 */
public class GyroActivity extends Activity implements SensorEventListener {

    private TextView      xAngle;
    private TextView      yAngle;
    private TextView      zAngle;

    private SensorManager sensorManager;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        xAngle = (TextView) findViewById(R.id.lblX);
        yAngle = (TextView) findViewById(R.id.lblY);
        zAngle = (TextView) findViewById(R.id.lblZ);

        /*
         * Retrieve the SensorManager.
         */
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        /*
         * Retrieve the default Sensor for the gyroscope.
         */
        Sensor sensorGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        /*
         * Register this activity as the listener for gyroscope events.
         */
        sensorManager.registerListener(this, sensorGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /*
     * This method will be called whenever the accuracy of the sensor has
     * changed.
     */
    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // left empty
    }

    /*
     * This method will be called whenever there are sensor events
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        /*
         * The SensorEvent object contains an array of values which represent
         * the angular speed along x, y and z axis which is displayed in the
         * TextView.
         */
        xAngle.setText(Float.toString(event.values[0]));
        yAngle.setText(Float.toString(event.values[1]));
        zAngle.setText(Float.toString(event.values[2]));

    }

    /*
     * It is good practice to unregister the sensor when not in use
     */
    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }
}