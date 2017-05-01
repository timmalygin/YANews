package yanews.malygin.tim.yanews.util;


import android.app.Activity;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.fail;

/**
 * Created by tim on 01.05.17.
 */
public class Util {
    /**
     * @return Activity находящее в текщий момент на экране.
     */
    public static Activity getActivityInstance() {
        final FutureTask<Activity> result = new FutureTask<>(new Callable<Activity>() {
            @Override
            public Activity call() throws Exception {
                Collection<Activity> resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                if(!resumedActivity.isEmpty()){
                    return resumedActivity.iterator().next();
                }
                throw new RuntimeException("resumedActivity is empty!");
            }
        });

        getInstrumentation().runOnMainSync(result);

        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            fail(e.toString());
        }
        return null;
    }
}