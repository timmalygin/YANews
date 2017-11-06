package yanews.malygin.tim.yanews.api.methods;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import yanews.malygin.tim.yanews.api.ApiResult;
import yanews.malygin.tim.yanews.idlingresorce.SimpleIdlingResource;
import yanews.malygin.tim.yanews.util.ThreadUtil;

/**
 * Created by timofey.malygin on 24/04/2017.
 */

public class DeleteUserMethod extends ApiMethod<DeleteUserMethod.DeleteResult> implements OnCompleteListener<Void> {

    public DeleteUserMethod(SimpleIdlingResource idleResources) {
        super(idleResources);
    }

    @Override
    public void send() {
        startLoading();
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            callCallback();
        } else {
            currentUser.delete().addOnCompleteListener(this);
        }
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        ThreadUtil.postOnMain(new Runnable() {
            @Override
            public void run() {
                callCallback();
            }
        });
    }

    void callCallback() {
        final DeleteResult callback = getCallback();
        if (!isCanceled && callback != null) {
            callback.success();
        }
        finishLoading();
    }

    public interface DeleteResult extends ApiResult {
        void success();
    }
}
