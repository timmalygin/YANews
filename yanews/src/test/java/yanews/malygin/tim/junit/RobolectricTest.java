package yanews.malygin.tim.junit;

import android.content.Context;
import android.text.TextUtils;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import yanews.malygin.tim.yanews.R;

/**
 * Created by timofey.malygin on 05/11/2017.
 */
@RunWith(RobolectricTestRunner.class)
public class RobolectricTest {

    @Test
    public void test() throws InterruptedException {
        Context context = RuntimeEnvironment.application;
        Assert.assertTrue(TextUtils.isEmpty(null));
        Assert.assertEquals(context.getString(R.string.app_name), "YANews");
    }

}
