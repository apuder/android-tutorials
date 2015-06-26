
package org.androidtutorials.animation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * MyImageView is derived from ImageView and adds getters and setters for
 * properties xFraction and yFraction. These properties allow to position a
 * MyImageView instance within its parent based on a percentage (and not an
 * absolute pixel position). The setter setX() and setY() allow to position the
 * image within its parent based on absolute pixel coordinates. The reason the
 * xFraction and yFraction are introduced is to be independent of the actual
 * size of the parent. I.e., the animation specification in bounce.xml can only
 * contain hard-coded values for the 'valueTo' parameter. Since the actual width
 * of the parent is not known at compile time, 'valueTo' is interpreted as a
 * percentage (where 0 represents the left/top of the parent and 1 represents
 * the right/bottom of the parent). The setters for xFraction and yFraction
 * compute the actual pixel positions based on the current width and height of
 * the parent.
 */
public class MyImageView extends ImageView {

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * getXFraction() should return the current horizontal position of the image
     * relative to the width of the parent. Always returning 0 is a bit of a
     * kludge. The animation framework will call this method only once when the
     * image is initially positioned in the top-left corner of the parent (see
     * activity_main.xml), so the kludge it OK. Computing the correct value is
     * surprisingly difficult. The reason is that getXFraction() will be called
     * when the layout computation has not finished yet. I.e., calling
     * getParent().getWidth() will return 0 since the parent's geometry has not
     * yet been determined. A proper solution would require the use of a
     * LayoutListener which would make this example unnecessarily complex. Note
     * that by the time setXFraction() is called the parent has a proper width.
     */
    public float getXFraction() {
        return 0;
    }

    public float getYFraction() {
        return 0;
    }

    /**
     * setXFraction() positions the MyImageView instance horizontally within its
     * parent. The parameter 'x' denotes a percentage ([0..1]) where the image
     * should be positioned relative to the width of the parent. This is done by
     * determining the width of the parent and subtracting the width of the
     * image. For x == 0 the image is aligned to the left of the parent. For x
     * == 1 the image is aligned to the right of the parent.
     */
    public void setXFraction(float x) {
        View parent = (View) this.getParent();
        this.setX((parent.getWidth() - this.getWidth()) * x);
    }

    /**
     * setYFraction does the same for the vertical position as setXFraction does
     * for the horizontal position.
     */
    public void setYFraction(float y) {
        View parent = (View) this.getParent();
        this.setY((parent.getHeight() - this.getHeight()) * y);
    }
}
