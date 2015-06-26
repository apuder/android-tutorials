

package org.androidtutorials.asynctask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.androidtutorials.asynctask.R;

/**
 * This application shows the usage of class AsyncTask that is a convenience
 * helper class for Java threads and Android handlers. AsyncTask should be used
 * for operations that run for several seconds in order to avoid blocking the UI
 * thread. This application demonstrates a common use case where first a spinner
 * is shown to indicate a longer running operation. After a little while the
 * spinner is replaced with the result of the longer running operation (in this
 * example the 'result' is a bitmap). AsyncTask provides callbacks that are
 * either called in the context of the UI thread or in the context of a
 * background thread that will be automatically created by AsyncTask.
 */
public class MainActivity extends Activity {

    final static private int   BITMAP_SIZE  = 400;
    final static private float RADIUS_SCALE = 0.2f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();

        /**
         * Create an AsyncTask instance that takes a String as a parameter and
         * returns Bitmap as a result. The parameter for progress updates is not
         * used in this example and is therefore Void.
         */
        new AsyncTask<String, Void, Bitmap>() {

            /**
             * The method doInBackground() will be called in the context of a
             * background thread and can run for several seconds to complete. In
             * this particular example, a String ("Hello World!") is rendered
             * onto a Bitmap in a semi-circle. To highlight the fact that
             * doInBackground() can run for several seconds, an artificial
             * sleep() of 5 seconds is done as well (this would not be
             * permissible in the UI thread). The return type of
             * doInBackground() must correspond with the third generic parameter
             * (Bitmap) and the input parameter with the first generic parameter
             * (String).
             */
            @Override
            protected Bitmap doInBackground(String... params) {
                /**
                 * We only get one string as a parameter ("Hello World!" in this
                 * example)
                 */
                String txt = params[0];

                /**
                 * Create a bitmap and use a canvas to draw the string that was
                 * passed as a parameter in a semi-circle onto the bitmap.
                 */
                Bitmap b = Bitmap.createBitmap(BITMAP_SIZE, BITMAP_SIZE, Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(b);
                c.drawColor(Color.TRANSPARENT);
                Path path = new Path();
                float radius = BITMAP_SIZE * RADIUS_SCALE;
                path.addCircle(BITMAP_SIZE / 2, BITMAP_SIZE / 2, radius, Path.Direction.CW);
                Paint p = new Paint();
                p.setAntiAlias(true);
                p.setColor(Color.DKGRAY);
                p.setTextSize(44);
                c.drawTextOnPath(txt, path, (float) (radius * Math.PI), 0, p);

                /**
                 * For purpose of the example, sleep for 5 seconds to highlight
                 * the fact that doInBackground() can run for several seconds.
                 */
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }

                /**
                 * Return the bitmap that was created.
                 */
                return b;
            }

            /**
             * Method onPostExecute() is run in the context of the UI thread. It
             * will be called *after* doInBackground() returns. The input
             * parameter will be the result of method doInBackground().
             */
            @Override
            protected void onPostExecute(Bitmap result) {
                /**
                 * Make the progress spinner gone.
                 */
                ProgressBar p = (ProgressBar) findViewById(R.id.progress);
                p.setVisibility(View.GONE);

                /**
                 * Set the bitmap in the ImageView and make it visible.
                 */
                ImageView img = (ImageView) findViewById(R.id.result);
                img.setImageBitmap(result);
                img.setVisibility(View.VISIBLE);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "Hello World!");
    }
}
