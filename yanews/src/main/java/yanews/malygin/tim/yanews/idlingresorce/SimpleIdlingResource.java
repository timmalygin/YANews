package yanews.malygin.tim.yanews.idlingresorce;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by timofey.malygin on 09/04/2017.
 */
public class SimpleIdlingResource implements IdlingResource {
    @Nullable
    private volatile ResourceCallback callback;

    private final AtomicBoolean isIdleNow;

    @NonNull
    private final String name;

    public SimpleIdlingResource(boolean isIdling, @NonNull String name) {
        isIdleNow = new AtomicBoolean(isIdling);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    /**
     */
    public void waiting() {
        this.isIdleNow.set(false);
    }

    public void release() {
        this.isIdleNow.set(true);
        if(callback!=null) {
            callback.onTransitionToIdle();
        }
    }
}
