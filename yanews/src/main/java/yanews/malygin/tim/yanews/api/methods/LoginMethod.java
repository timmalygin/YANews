package yanews.malygin.tim.yanews.api.methods;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import yanews.malygin.tim.yanews.api.ApiResult;
import yanews.malygin.tim.yanews.idlingresorce.SimpleIdlingResource;
import yanews.malygin.tim.yanews.util.ThreadUtil;

/**
 * Created by timofey.malygin on 23/04/2017.
 */

public class LoginMethod extends ApiMethod<LoginMethod.LoginResult> implements OnCompleteListener<AuthResult> {

    @Nullable
    private String login;
    @Nullable
    private String pwd;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public LoginMethod(SimpleIdlingResource idleResources) {
        super(idleResources);
    }

    public LoginMethod setLoginInformation(@Nullable String login, @Nullable String pwd) {
        this.login = login;
        this.pwd = pwd;
        return this;
    }

    @Override
    public void send() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                startLoading();
//                try {
//                    Thread.sleep(6_000l);
//                } catch (InterruptedException e) {
//                    Log.e("LOG", "I can\'t sleep", e);
//                }
                if (TextUtils.isEmpty(login)) {
                    auth.signInAnonymously();
                } else {
                    auth.signInWithEmailAndPassword(login, pwd).addOnCompleteListener(LoginMethod.this);
                }
            }
        }.start();
    }

    @Override
    public synchronized void onComplete(@NonNull final Task<AuthResult> task) {
        ThreadUtil.postOnMain(new Runnable() {
            @Override
            public void run() {
                sendToCallback(task);
                finishLoading();
            }
        });
    }

    private void sendToCallback(@NonNull Task<AuthResult> task) {
        if (isCanceled) {
            return;
        }
        final LoginResult callback = getCallback();
        if (callback == null) {
            return;
        }
        if (task.isSuccessful()) {
            FirebaseUser user = auth.getCurrentUser();
            callback.onSucces(user);
        } else {
            callback.onError(task.getException().getMessage());
        }
    }

    public static class LoginResult implements ApiResult {
        public void onSucces(@NonNull FirebaseUser user) {
        }

        public void onError(String msg) {
        }
    }
}
