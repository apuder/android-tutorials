package org.androidtutorials.uitest;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.text.StringContains.containsString;

/*
    This is an Espresso UI test for SayHelloActivity of SayHello declarative tutorial.
    Uses AndroidJUnit4 as a test runner for running Espresso UI Tests. The test class (SayHelloActivity)
    extends class ActivityInstrumentationTestCase2 which allows us to run tests for specific activity.
    (here, it is SayHelloActivity which is passed as a genetic parameter). It also needs super constructor which
    Android Studio will take care of by adding it. In a perfect world of testing, tests are divided into
    three blocks: Before, Test and After. As shown below, in @Before block, we have setup the test and
    are invoking an activity so that we can perform tests on it. For performing UI tests, injection of
    Instrumentation is done via: "injectInstrumentation(InstrumentationRegistry.getInstrumentation());"
 */

@RunWith(AndroidJUnit4.class)
public class SayHelloActivityTest extends ActivityInstrumentationTestCase2<SayHelloActivity> {

    SayHelloActivity activity;

    public SayHelloActivityTest() {
        super(SayHelloActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        activity = getActivity();
    }

    /*
        In @Test block, onView() is a view matcher function that allows us to find a view in the current view hierarchy.
        'editText' is the resource id of the Edit Text box in which we write some text. 'perform()' allows us to perform
		 actions on a view and in this example, we are clicking on an editText in order to input a string "Hello, Espresso!".
		 On clicking of button, we check if the textView is set with the text that contains "Hello, Espresso!" as a substring
		 and the test is passed. There are a number of view actions and view matcher's that Espresso framework allows:
		 https://code.google.com/p/android-test-kit/wiki/EspressoV2CheatSheet
     */

    @Test
    public void testSayHello() throws InterruptedException {
        onView(withId(R.id.editText)).perform(click()).perform(typeText("Hello, Espresso!"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(anyOf(withText(containsString("Hello, Espresso!")))));
    }
}




