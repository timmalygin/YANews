package yanews.malygin.tim.junit;

import android.text.TextUtils;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.easymock.EasyMock.expect;
import static org.mockito.ArgumentMatchers.any;
import static org.powermock.api.easymock.PowerMock.replay;

/**
 * Created by timofey.malygin on 06/11/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class MockitoTest {

    @Test
    public void testTextUtils() {
        PowerMock.mockStaticPartial(TextUtils.class, "isEmpty", CharSequence.class);
        expect(TextUtils.isEmpty(any(CharSequence.class))).andAnswer(new IAnswer<Boolean>() {
            @Override
            public Boolean answer() throws Throwable {
                CharSequence a = (CharSequence) EasyMock.getCurrentArguments()[0];
                return a == null || a.length() == 0;
            }
        });
        replay(TextUtils.class);
        Assert.assertTrue(TextUtils.isEmpty(null));
    }
}
