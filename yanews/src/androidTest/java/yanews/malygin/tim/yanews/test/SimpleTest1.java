package yanews.malygin.tim.yanews.test;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import yanews.malygin.tim.yanews.*;
import yanews.malygin.tim.yanews.ui.activity.LoginActivity;
import yanews.malygin.tim.yanews.ui.activity.RegistrationActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Created by timofey.malygin on 09/04/2017.
 */
@RunWith(AndroidJUnit4.class)
public class SimpleTest1 {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class, true, false);

    @Before
    public void init() {
        Intents.init();
        loginActivityRule.launchActivity(new Intent());
    }

    @Test
    @SmallTest
    public void openRegistrationActivity() {
        Intents.intended(IntentMatchers.hasComponent(LoginActivity.class.getName()));
        onView(ViewMatchers.withId(yanews.malygin.tim.yanews.R.id.registration))
                .check(matches(allOf(isDisplayed(), isEnabled(), isClickable())))
                .perform(click());
        Intents.intended(IntentMatchers.hasComponent(RegistrationActivity.class.getName()));

    }

    @Test
    @SmallTest
    public void writeLogin() {
        Intents.intended(IntentMatchers.hasComponent(LoginActivity.class.getName()));
        onView(ViewMatchers.withId(yanews.malygin.tim.yanews.R.id.login))
                .check(matches(not(isEnabled())));
        onView(ViewMatchers.withId(yanews.malygin.tim.yanews.R.id.login_text))
                .perform(typeText("small"));
        onView(ViewMatchers.withId(yanews.malygin.tim.yanews.R.id.login))
                .check(matches(not(isEnabled())));

    }

    @After
    public void release() {
        Intents.release();
    }

}
