package com.trojanow.gui.activities;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import com.trojanow.R;
import com.trojanow.gui.account.SignUpActivity;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by heetae on 4/13/15.
 */
public class SignUpActivityTest extends ActivityInstrumentationTestCase2<SignUpActivity> {
    private SignUpActivity mActivity;

    public SignUpActivityTest() {
        super(SignUpActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    public void testSignUp_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.editTextUsername)).perform(typeText("autotest"));
        onView(withId(R.id.editTextFirstname)).perform(typeText("first"));
        onView(withId(R.id.editTextLastname)).perform(typeText("last"));
        onView(withId(R.id.editTextPassword)).perform(typeText("123456"));
        onView(withId(R.id.editTextConfirm)).perform(typeText("123456"));
        onView(withId(R.id.editTextEmail)).perform(typeText("a@a.com"));
        onView(withId(R.id.editTextContact)).perform(typeText("(123) 123-1234"));
    }
}
