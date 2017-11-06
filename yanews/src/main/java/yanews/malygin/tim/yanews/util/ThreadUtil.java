package yanews.malygin.tim.yanews.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

public class ThreadUtil {

    public static void postOnMain(@NonNull Runnable run) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            run.run();
        } else {
            new Handler(Looper.getMainLooper()).post(run);
        }
    }

    public static void postOnMainDelayed(@NonNull Runnable run) {
        new Handler(Looper.getMainLooper()).postDelayed(run, 6000L);
    }
}
