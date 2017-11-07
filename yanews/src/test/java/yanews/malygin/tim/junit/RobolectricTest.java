package yanews.malygin.tim.junit;

import android.text.TextUtils;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class RobolectricTest {

    @Test
    public void test() {
        Assert.assertTrue(TextUtils.isEmpty(null));
    }

}
