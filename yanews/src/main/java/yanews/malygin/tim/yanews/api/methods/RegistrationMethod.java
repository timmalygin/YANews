package yanews.malygin.tim.yanews.api.methods;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import yanews.malygin.tim.yanews.api.ApiResult;
import yanews.malygin.tim.yanews.idlingresorce.SimpleIdlingResource;
import yanews.malygin.tim.yanews.util.ThreadUtil;

public class RegistrationMethod extends ApiMethod<RegistrationMethod.RegistrationResult> implements OnCompleteListener<AuthResult> {

    private String login, pwd;
    private FirebaseAuth auth;

    public RegistrationMethod(SimpleIdlingResource idleResources) {
        super(idleResources);
        auth = FirebaseAuth.getInstance();
    }

    public RegistrationMethod setRegistrationData(@NonNull String login, @NonNull String pwd) {
        this.login = login;
        this.pwd = pwd;
        return this;
    }

    @Override
    public void  send() {
        startLoading();

        auth.createUserWithEmailAndPassword(login, pwd)
                .addOnCompleteListener(this);
    }

    @Override
    void startLoading() {
        super.startLoading();
        final RegistrationResult callback = getCallback();
        if (callback != null) {
            callback.loading();
        }
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        final RegistrationResult callback = getCallback();
        if (!isCanceled && callback != null && task.isSuccessful()) {
            ThreadUtil.postOnMainDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.success();
                }
            });
        }
        finishLoading();
    }

    public interface RegistrationResult extends ApiResult {
        void loading();
        void success();
    }
}
