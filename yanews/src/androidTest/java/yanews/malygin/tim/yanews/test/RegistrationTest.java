package yanews.malygin.tim.yanews.test;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import yanews.malygin.tim.yanews.ui.activity.LoginActivity;

/**
 * Created by timofey.malygin on 02/05/2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegistrationTest {

    @Rule
    public ActivityTestRule<LoginActivity> startActivity
            = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void init() {

    }

    @After
    public void release() {

    }

    @Test
    public void testRegistration() {
        // checks
    }
}
