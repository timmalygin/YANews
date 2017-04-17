package yanews.malygin.tim.yanews.util;

import android.app.Activity;
import android.support.annotation.CheckResult;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by timofey.malygin on 05/04/2017.
 */
public class ViewUtils {

    @CheckResult
    public static <T extends View> T findById(@NonNull View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }

    @CheckResult
    public static <T extends View> T findById(@NonNull Activity activity, @IdRes int id) {
        return (T) activity.findViewById(id);
    }

    public static void setClickListener(@NonNull Activity activity,
                                        @IdRes int id,
                                        @Nullable View.OnClickListener l) {
        findById(activity, id).setOnClickListener(l);
    }
}
