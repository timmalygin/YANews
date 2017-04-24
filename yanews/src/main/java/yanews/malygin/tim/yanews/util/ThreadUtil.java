package yanews.malygin.tim.yanews.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

/**
 * Created by timofey.malygin on 23/04/2017.
 */

public class ThreadUtil {

    public static void postOnMain(@NonNull Runnable run) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            run.run();
        } else {
            new Handler(Looper.getMainLooper()).post(run);
        }
    }
}
