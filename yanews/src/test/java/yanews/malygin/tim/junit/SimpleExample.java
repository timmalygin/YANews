package yanews.malygin.tim.junit;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by timofey.malygin on 05/11/2017.
 */
public class SimpleExample {

    @Test
    public void example() throws Throwable {
        String world = "World";
        Assert.assertEquals("Hello World", "Hello " + world);
        Assert.assertNotSame("Hello World", "Hello " + world);
    }

    @Test
    public void example_2() {
        int a = 90;
        int min = 10;
        Assert.assertTrue(Math.min(a, min) == min);
    }
}
