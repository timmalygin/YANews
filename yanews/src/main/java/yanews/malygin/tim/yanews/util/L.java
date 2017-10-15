package yanews.malygin.tim.yanews.util;

import android.support.annotation.NonNull;
import android.util.Log;

import yanews.malygin.tim.yanews.BuildConfig;

public class L {

    public static void d(@NonNull Object tag, @NonNull String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag.toString(), msg);
        }
    }

    public static void e(@NonNull Throwable thr) {
        Log.e(thr.getMessage(), thr.getLocalizedMessage(), thr);
    }
}
