package yanews.malygin.tim.yanews.api.methods;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import yanews.malygin.tim.yanews.api.ApiResult;
import yanews.malygin.tim.yanews.idlingresorce.SimpleIdlingResource;
import yanews.malygin.tim.yanews.util.ThreadUtil;

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
    void startLoading() {
        super.startLoading();
        final LoginResult callback = getCallback();
        if (callback != null) {
            callback.showLoading();
        }
    }

    @Override
    public void send() {
        startLoading();
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (TextUtils.isEmpty(login)) {
                    auth.signInAnonymously().addOnCompleteListener(LoginMethod.this);
                } else {
                    auth.signInWithEmailAndPassword(login, pwd).addOnCompleteListener(LoginMethod.this);
                }
            }
        }.start();
    }

    @Override
    public synchronized void onComplete(@NonNull final Task<AuthResult> task) {
        ThreadUtil.postOnMainDelayed(new Runnable() {
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
        if (task.isSuccessful() && auth.getCurrentUser()!=null) {
            FirebaseUser user = auth.getCurrentUser();
            callback.onSuccess(user);
        } else {
            final Exception exception = task.getException();
            callback.onError(exception == null ? "unknown error" : exception.getMessage());
        }
    }

    public static abstract class LoginResult implements ApiResult {

        public abstract void showLoading();

        public void onSuccess(@NonNull FirebaseUser user) {
        }

        public void onError(String msg) {
        }
    }
}
