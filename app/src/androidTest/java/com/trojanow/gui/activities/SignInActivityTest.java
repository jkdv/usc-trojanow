package com.trojanow.gui.activities;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import com.trojanow.R;
import com.trojanow.gui.account.SignInActivity;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by heetae on 4/13/15.
 */
public class SignInActivityTest extends ActivityInstrumentationTestCase2<SignInActivity> {
    private SignInActivity mActivity;

    public SignInActivityTest() {
        super(SignInActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    public void testSignIn_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.editTextUsername)) .perform(typeText("testuser1"));
        onView(withId(R.id.editTextPassword)) .perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.buttonSignIn)).perform(click());
    }
}
