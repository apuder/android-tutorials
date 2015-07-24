package org.androidtutorials.immersive;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * A simple application demonstrating the so-called immersive mode where an
 * application can take over the complete screen real-estate. This mode is
 * usually of benefit for applications such as games or e-book readers. This
 * more was introduced in API level 19. This application shows the non-sticky
 * immersive mode. Consult the Android documentation for different immersion
 * modes.
 */
public class ImmersiveActivity extends Activity {

    private View decorView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        /*
         * The decor view contains the standard window frame/decorations and the
         * client's content inside of that.
         */
        decorView = getWindow().getDecorView();
        hideSystemUI(null);
    }

    /**
     * This method hides the system UI by setting the content to appear under
     * the system bars so that the content doesn't resize when the system bars
     * hide and show.
     */
    public void hideSystemUI(View view) {
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    /**
     * This snippet shows the system bars. It does this by removing all the
     * flags except for the ones that make the content appear under the system
     * bars.
     */
    public void showSystemUI(View view) {
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}