package com.trojanow.gui.activities;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import com.trojanow.gui.timeline.AllShownTimelineActivity;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by heetae on 4/13/15.
 */
public class AllShownTimelineActivityTest extends ActivityInstrumentationTestCase2<AllShownTimelineActivity> {
    private AllShownTimelineActivity mActivity;

    public AllShownTimelineActivityTest() {
        super(AllShownTimelineActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());

        Intent intent = new Intent(null, AllShownTimelineActivity.class);
        setActivityIntent(intent);
        mActivity = getActivity();
    }

    public void test_sameActivity() {
        // Type text and then press the button.
    }
}
