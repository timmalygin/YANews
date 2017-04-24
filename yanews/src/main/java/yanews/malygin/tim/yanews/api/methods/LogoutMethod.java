package yanews.malygin.tim.yanews.api.methods;

import com.google.firebase.auth.FirebaseAuth;

import yanews.malygin.tim.yanews.api.ApiResult;
import yanews.malygin.tim.yanews.idlingresorce.SimpleIdlingResource;
import yanews.malygin.tim.yanews.util.ThreadUtil;

/**
 * Created by timofey.malygin on 23/04/2017.
 */

public class LogoutMethod extends ApiMethod<LogoutMethod.LogoutResult> {

    public LogoutMethod(SimpleIdlingResource idleResources) {
        super(idleResources);
    }

    @Override
    public void send() {
        startLoading();
        FirebaseAuth.getInstance().signOut();
        ThreadUtil.postOnMain(new Runnable() {
            @Override
            public void run() {
                sendCallback();
                finishLoading();
            }
        });
    }

    void sendCallback() {
        if (isCanceled) return;
        final LogoutResult callback = getCallback();
        if (callback == null) return;
        callback.succes();
    }

    public static interface LogoutResult extends ApiResult {
         void succes();
    }
}
