package org.androidtutorials.conductor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bluelinelabs.conductor.ChangeHandlerFrameLayout;
import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;


/**
 * Simple test implementation of the Conductor framework.
 * In this test app, there are only two layouts: Layout 1 and Layout 2, each of which are inflated
 * by their own Controller classes (Layout1Controller and Layout2Controller).
 * The user will be able to navigate between the two layouts by pressing the two buttons.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Private variables to hold references to the View and ViewGroup objects in activity_main.xml
    private ChangeHandlerFrameLayout mContainer; // The container that will the views that we push to the Router.
    private Button mLayout1Button;
    private Button mLayout2Button;

    // The Router which handles the transactions and pushes new Controllers to the backstack.
    private Router mRouter;

    // An integer variable used in this exercise for tracking which layout # is currently pushed to
    // mRouter. This is important for us so that we don't unnecessarily add the same Controller to the
    // backstack twice in a row.
    private int mCurrentLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind our variables to the View and ViewGroup objects in activity_main.xml
        mContainer = (ChangeHandlerFrameLayout) findViewById(R.id.container);
        mLayout1Button = (Button) findViewById(R.id.layout_1_button);
        mLayout2Button = (Button) findViewById(R.id.layout_2_button);

        // Set up the listener with our buttons.
        mLayout1Button.setOnClickListener(this);
        mLayout2Button.setOnClickListener(this);

        // attachRouter() called here.
        // Initialize the router with mContainer, which is the ChangeHandlerFrameLayout ViewGroup
        // in activity_main.xml. Every time a new Controller is pushed to mRouter, the views inflated
        // from the new Controller will appear where our mContainer is.
        mRouter = Conductor.attachRouter(this, mContainer, savedInstanceState);

        // Set the root of our Router object with Layout 1, or in other words, push our first Controller
        // to the Router.
        mRouter.setRoot(RouterTransaction.with(new Layout1Controller()));
        // Set the current layout at 1.
        mCurrentLayout = 1;
    }



    // Callbacks for the OnClickListener
    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case (R.id.layout_1_button):
                // First check to make sure we are not already on Layout 1
                if (mCurrentLayout != 1) {
                    // pushController() will push our Layout1Controller to the backstack.
                    mRouter.pushController(RouterTransaction.with(new Layout1Controller()));
                    mCurrentLayout = 1;
                }
                break;
            case (R.id.layout_2_button):
                // First check to make sure we are not already on Layout 2
                if (mCurrentLayout != 2) {
                    // pushController() will push our Layout2Controller to the backstack.
                    mRouter.pushController(RouterTransaction.with(new Layout2Controller()));
                    mCurrentLayout = 2;
                }
                break;
            default:
                break;
        }
    }




    // Override the onBackPressed() callback method to use our Router's backstack handling instead
    // of the application's default backstack handling.
    @Override
    public void onBackPressed() {
        // If the Router will not handle the backstack, then use the app's default response.
        if (!mRouter.handleBack()) {
            super.onBackPressed();
        }
    }

}
