package yanews.malygin.tim.yanews.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import yanews.malygin.tim.yanews.api.methods.LoginMethod;
import yanews.malygin.tim.yanews.api.methods.LogoutMethod;
import yanews.malygin.tim.yanews.test.R;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by timofey.malygin on 11/11/2017.
 */
@RunWith(AndroidJUnit4.class)
public final class AuthTest {

    private Context context;

    @Before
    public void init() {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        assertTrue(auth == null || auth.getCurrentUser() == null);
        context = InstrumentationRegistry.getContext();
    }

    @Test
    public void load() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Api.<LoginMethod>createMethod(ApiKeys.LOGIN)
                .setLoginInformation(context.getString(R.string.bot_1_login), context.getString(R.string.bot_1_pwd))
                .setCallback(new LoginMethod.LoginResult() {
                    @Override
                    public void showLoading() {

                    }

                    @Override
                    public void onSuccess(@NonNull FirebaseUser user) {
                        super.onSuccess(user);
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onError(String msg) {
                        super.onError(msg);
                        fail(msg);
                        countDownLatch.countDown();
                    }
                })
                .send();
        countDownLatch.await();

    }

    @After
    public void release() {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        final LogoutMethod logout = Api.createMethod(ApiKeys.LOGOUT);
        logout
                .setCallback(countDownLatch::countDown)
                .send();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            fail(e.getMessage());
        }
    }
}
