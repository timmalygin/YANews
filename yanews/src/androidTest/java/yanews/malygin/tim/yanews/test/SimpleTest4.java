package yanews.malygin.tim.yanews.test;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;

import yanews.malygin.tim.yanews.R;
import yanews.malygin.tim.yanews.api.Api;
import yanews.malygin.tim.yanews.api.ApiKeys;
import yanews.malygin.tim.yanews.data.News;
import yanews.malygin.tim.yanews.idlingresorce.SimpleIdlingResource;
import yanews.malygin.tim.yanews.ui.activity.LoginActivity;
import yanews.malygin.tim.yanews.ui.activity.MainActivity;
import yanews.malygin.tim.yanews.ui.adapter.NewsAdapter;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static yanews.malygin.tim.yanews.util.Util.getActivityInstance;
import static yanews.malygin.tim.yanews.util.ViewUtils.findById;

/**
 *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleTest4 {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule
            = new ActivityTestRule<>(LoginActivity.class, false, false);
    private Map<Integer, SimpleIdlingResource> idleResources;


    @Before
    public void init() {
        Intents.init();
        // создаем idlingResources для прослушивания авторизации
        idleResources = new HashMap<>();
        Api.idleResources = idleResources;
        idleResources.put(ApiKeys.LOGIN, new SimpleIdlingResource(true, "auth"));
        idleResources.put(ApiKeys.LOGOUT, new SimpleIdlingResource(true, "logout"));
        idleResources.put(ApiKeys.GET_NEWS, new SimpleIdlingResource(true, "loadNews"));

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

    /**
     * Проходимся по всем элементам новостей
     */
    @Test
    public void b_checkActivityStack() {
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
        // проходимся по всем новостям
        RecyclerView recycleView = findById(getActivityInstance(), R.id.news);
        NewsAdapter adapter = (NewsAdapter) recycleView.getAdapter();
        for( int i=0; i<adapter.getItemCount(); i++){
            News news = adapter.getItemPosition(i);
            Espresso.onView(ViewMatchers.withId(R.id.news))
                    .perform(RecyclerViewActions.scrollToHolder(findFriendHolderWithFriend(news)));
            if(getTargetContext().getResources().getBoolean(R.bool.hasDoublePanel)) {
                // Проверяем что есть заголовок
                Espresso.onView(allOf(
                        withParent(withTagValue(Matchers.<Object>is(news))), withText(news.title)))
                        .check(matches(isDisplayed()));
                // и тело новости
                Espresso.onView(allOf(
                        withParent(withTagValue(Matchers.<Object>is(news))), withText(news.body)))
                        .check(matches(isDisplayed()));
            }
        }
    }

    /**
     * Ищем холдер с определенной новостью
     *
     */
    private static Matcher<NewsAdapter.NewsHolder> findFriendHolderWithFriend(final News news) {
        return new TypeSafeMatcher<NewsAdapter.NewsHolder>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("new is "+news);
            }

            @Override
            protected boolean matchesSafely(NewsAdapter.NewsHolder holder) {
                News holderNews = (News) holder.itemView.getTag();
                return news.equals(holderNews);
            }

        };
    }
}
