package yanews.malygin.tim.yanews.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

/**
 * Created by timofey.malygin on 05/04/2017.
 */
public class IntentUtil {

    public static void showActivity(@NonNull Context context,
                                    @NonNull Class<? extends Activity> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void showActivity(@NonNull Activity context,
                                    @NonNull Class<? extends Activity> cls,
                                    Pair<View, String>... views) {
        final ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(context, views);
        ActivityCompat.startActivity(context, new Intent(context, cls), options.toBundle());
    }

}
