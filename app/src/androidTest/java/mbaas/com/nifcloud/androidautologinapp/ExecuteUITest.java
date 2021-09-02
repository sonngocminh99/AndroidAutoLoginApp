package mbaas.com.nifcloud.androidautologinapp;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.nifcloud.mbaas.core.NCMBException;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static mbaas.com.nifcloud.androidautologinapp.Utils.waitForText;

@RunWith(AndroidJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExecuteUITest {
    @Rule
    public final ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class, true);

    @Test
    public void signUpInBackground() {
        onView(withId(R.id.txtMessage)).check(matches(isDisplayed()));
        onView(withId(R.id.txtLogin)).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitForText("はじめまして", 50000));
        onView(isRoot()).perform(waitForText("１回目ログイン、ありがとうございます。", 50000));
    }

    @Test
    public void validateLoginInBackground() throws NCMBException {
        onView(withId(R.id.txtMessage)).check(matches(isDisplayed()));
        onView(withId(R.id.txtLogin)).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitForText("お帰りなさい！", 50000));
        onView(isRoot()).perform(waitForText("最終ログインは：", 50000));

        Utils.deleteUserIfExist(main.getActivity().getApplicationContext());
    }
}
