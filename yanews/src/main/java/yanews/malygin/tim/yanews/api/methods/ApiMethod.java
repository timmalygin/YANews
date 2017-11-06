package yanews.malygin.tim.yanews.api.methods;

import android.support.annotation.Nullable;

import yanews.malygin.tim.yanews.api.ApiResult;
import yanews.malygin.tim.yanews.idlingresorce.SimpleIdlingResource;

public abstract class ApiMethod<T extends ApiResult> {

    private final SimpleIdlingResource idleResources;

    ApiMethod(SimpleIdlingResource idleResources) {
        this.idleResources = idleResources;
    }

    void startLoading(){
        if(idleResources!=null) {
            idleResources.waiting();
        }
    }

    void finishLoading(){
        if(idleResources!=null) {
            idleResources.release();
        }
    }

    public abstract void send();

    volatile boolean isCanceled = false;
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

    synchronized T getCallback() {
        return callback;
    }

}
