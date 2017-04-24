package yanews.malygin.tim.yanews.test;

import android.app.Activity;
import android.content.Intent;
import android.os.ParcelFileDescriptor;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.util.SparseArray;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import yanews.malygin.tim.yanews.api.Api;
import yanews.malygin.tim.yanews.api.ApiKeys;
import yanews.malygin.tim.yanews.idlingresorce.SimpleIdlingResource;
import yanews.malygin.tim.yanews.ui.activity.LoginActivity;
import yanews.malygin.tim.yanews.ui.activity.MainActivity;
import yanews.malygin.tim.yanews.ui.activity.RegistrationActivity;
import yanews.malygin.tim.yanews.util.L;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.fail;

import yanews.malygin.tim.yanews.R;

/**
 * "login@test.ru", "password1"
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleTest3 {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule
            = new ActivityTestRule<LoginActivity>(LoginActivity.class, false, false);
    private Map<Integer, SimpleIdlingResource> idleResources;


    @Before
    public void init() {
        Intents.init();
        // создаем idlingResources для прослушивания авторизации
        idleResources = new HashMap<>();
        Api.idleResources = idleResources;
        idleResources.put(ApiKeys.LOGIN, new SimpleIdlingResource(true, "auth"));
        idleResources.put(ApiKeys.LOGOUT, new SimpleIdlingResource(true, "logout"));
        idleResources.put(ApiKeys.REGISTRATION, new SimpleIdlingResource(true, "registration"));
        idleResources.put(ApiKeys.DELETE_USER, new SimpleIdlingResource(true, "delete_user"));

        // регистрируем его для прослушивания
        for(SimpleIdlingResource idlingResource: idleResources.values()) {
            Espresso.registerIdlingResources(idlingResource);
        }

        loginActivityRule.launchActivity(new Intent());
    }

    @After
    public void release() {
        Intents.release();
        if(idleResources!=null) {
            for (SimpleIdlingResource idlingResource : idleResources.values()) {
                Espresso.unregisterIdlingResources(idlingResource);
            }
        }
    }

    @AfterClass
    public static void closeTest() {
        // очищаем все данные в приложение. ( Авторизация, сохраненые картинки, текст и т.д.)
        final ParcelFileDescriptor parcelFileDescriptor = getInstrumentation().getUiAutomation().executeShellCommand(
                "pm clear " + getTargetContext().getPackageName());
        try {
            parcelFileDescriptor.checkError();
        } catch (IOException e) {
            L.e(e);
        }
    }

    /**
     * Проверяем стек приложения
     */
    @Test
    public void b_checkActivityStack() {
        // проверяем что мы на форме логина
        Assert.assertEquals(getActivityInstance().getClass(), LoginActivity.class);
        // переходим на activity с регистрацией
        onView(ViewMatchers.withId(R.id.registration))
                .check(matches(isDisplayed()))
                .perform(click());
        // проверяем что мы перешли
        Assert.assertEquals(getActivityInstance().getClass(), RegistrationActivity.class);
        // возвращаем обратно
        pressBack();
        // проверяем что мы на первой форме с логином
        Assert.assertEquals(getActivityInstance().getClass(), LoginActivity.class);
    }

    @Test
    public void a_login() {
        // проверяем что мы на форме логина
        Assert.assertEquals(getActivityInstance().getClass(), LoginActivity.class);

        // вводим логин и пароль
        onView(ViewMatchers.withId(R.id.login_text))
                .perform(typeText("login@test.ru"));
        onView(ViewMatchers.withId(R.id.password_text))
                .perform(typeText("password1"));
        // пытаемся авторизоваться
        onView(ViewMatchers.withId(R.id.login))
                .check(matches(isEnabled()))
                .perform(click());
        // проверяем что мы авторизовались успешно
        Assert.assertEquals(getActivityInstance().getClass(), MainActivity.class);
        // сбрасываем авторизацию
        onView(ViewMatchers.withId(R.id.action_exit))
                .perform(click());
        // проверяем что после сброса мы снова на форме логина
        Assert.assertEquals(getActivityInstance().getClass(), LoginActivity.class);
    }

    /**
     * @return Activity находящее в текщий момент на экране.
     */
    private Activity getActivityInstance() {
        final FutureTask<Activity> result = new FutureTask(new Callable<Activity>() {
            @Override
            public Activity call() throws Exception {
                Collection<Activity> resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                for (Activity act : resumedActivity) {
                    return act;
                }
                throw new RuntimeException("acitivities stack is empty!");
            }
        });

        getInstrumentation().runOnMainSync(result);

        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            fail(e.toString());
        }
        return null;
    }
}
