package yanews.malygin.tim.yanews.util;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by timofey.malygin on 05/04/2017.
 */

public class L {

    public static void d(@NonNull Object tag, @NonNull String msg) {
        Log.d(tag.toString(), msg);
    }

    public static void e(@NonNull Throwable thr){
        Log.e(thr.getMessage(), thr.getLocalizedMessage(), thr);
    }
}
