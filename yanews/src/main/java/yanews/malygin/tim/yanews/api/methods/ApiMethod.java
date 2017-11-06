package yanews.malygin.tim.yanews.api.methods;

import android.support.annotation.Nullable;

import yanews.malygin.tim.yanews.api.ApiResult;
import yanews.malygin.tim.yanews.idlingresorce.SimpleIdlingResource;

/**
 * Created by timofey.malygin on 23/04/2017.
 */
public abstract class ApiMethod<T extends ApiResult> {

    protected final SimpleIdlingResource idleResources;

    protected ApiMethod(SimpleIdlingResource idleResources) {
        this.idleResources = idleResources;
    }

    protected void startLoading(){
        if(idleResources!=null) {
            idleResources.waiting();
        }
    }

    protected void finishLoading(){
        if(idleResources!=null) {
            idleResources.release();
        }
    }

    public abstract void send();

    protected volatile boolean isCanceled = false;
    @Nullable
    private T callback;

    public final ApiMethod setCallback(@Nullable T callback) {
        this.callback = callback;
        return this;
    }

    public synchronized final void cancel() {
        callback = null;
        isCanceled = true;
    }

    public synchronized T getCallback() {
        return callback;
    }

}
