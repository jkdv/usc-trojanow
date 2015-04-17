package com.trojanow.gui.activities;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import com.trojanow.R;
import com.trojanow.gui.account.LoginActivity;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by heetae on 4/14/15.
 */
public class LogInActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {
    private LoginActivity mActivity;

    public LogInActivityTest() {
        super(LoginActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    public void testOnClickSignUp_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.buttonSignUp)).perform(click());
    }

    public void testOnClickSignIn_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.buttonSignIn)).perform(click());
    }
}
