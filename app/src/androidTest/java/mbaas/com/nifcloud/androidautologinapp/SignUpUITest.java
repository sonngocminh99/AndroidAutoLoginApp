package mbaas.com.nifcloud.androidautologinapp;

import android.content.Context;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4ClassRunner.class)
public class SignUpUITest {
    @Rule
    public final ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class, true);

    @Before
    public void init() {
        // Specify a valid string.
    }

    @Test
    public void validateSignUpInBackground() {
        onView(withId(R.id.txtMessage)).check(matches(isDisplayed()));
        onView(withId(R.id.txtLogin)).check(matches(isDisplayed()));
        onView(isRoot()).perform(LoginUITest.waitForText ("はじめまして",50000));
        onView(isRoot()).perform(LoginUITest.waitForText ("１回目ログイン、ありがとうございます。",50000));
    }
}
