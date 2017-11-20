package yanews.malygin.tim.yanews.test;

import android.app.UiAutomation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import yanews.malygin.tim.yanews.ui.activity.RegistrationActivity;

import static junit.framework.Assert.fail;

/**
 * Created by timofey.malygin on 20/11/2017.
 */
@RunWith(AndroidJUnit4.class)
public class CheckTest {

    @Rule
    public ActivityTestRule<RegistrationActivity> activity =
            new ActivityTestRule<>(RegistrationActivity.class);

    @Test
    @SmallTest
    public void test() {
        final String testStr = "login_for_demo";
        Espresso.onView(ViewMatchers.withId(
                yanews.malygin.tim.yanews.R.id.login))
                .perform(
                        ViewActions
                                .typeText(testStr));
        final UiAutomation uiAutomation =
                InstrumentationRegistry
                        .getInstrumentation()
                        .getUiAutomation();
        uiAutomation.setRotation(UiAutomation.ROTATION_FREEZE_90);
        try {
            Thread.sleep(2000l);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        Espresso.onView(ViewMatchers.withId(
                yanews.malygin.tim.yanews.R.id.login
        ))
                .check(ViewAssertions
                        .matches(ViewMatchers.withText(testStr)));
    }
}
