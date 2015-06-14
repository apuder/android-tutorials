
package org.androidtutorials.animation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import org.androidtutorials.animation.R;

/**
 * Simple example for the animation framework. A soccer ball bounces from the
 * top/left corner of the screen to the lower/right corner. This example uses an
 * ObjectAnimator to animate the x and y positions of an ImageView. It shows the
 * animation via an XML resource as well as an identical version using Java API.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Find the ImageView that shows the soccer ball.
         */
        ImageView ball = (ImageView) this.findViewById(R.id.ball);

        /**
         * Load the animation description in bounce.xml, set the ball as the
         * animation target and then start the animation.
         */
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.bounce);
        set.setTarget(ball);
        set.start();
    }

    /**
     * The following method performs exactly the same animation as defined in
     * bounce.xml. But it does so programmatically via an API. Note that using
     * an API could have given different implementation options. While the
     * 'valueTo' parameter needs to be hard-coded in bounce.xml (see comments in
     * MyImageView) it would be possible to animate properties 'x' and 'y' of an
     * ImageView and compute the width and height of the parents here in this
     * method.
     */
    private void animateViaAPI(ImageView ball) {
        ObjectAnimator animX = ObjectAnimator.ofFloat(ball, "xFraction", 0f, 1f).setDuration(5000);
        ObjectAnimator animY = ObjectAnimator.ofFloat(ball, "yFraction", 0f, 1f).setDuration(5000);
        animY.setInterpolator(new BounceInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.play(animX).with(animY);
        set.start();
    }
}
