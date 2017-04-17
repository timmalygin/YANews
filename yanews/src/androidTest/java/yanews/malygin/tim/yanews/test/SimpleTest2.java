package yanews.malygin.tim.yanews.test;

import android.app.Instrumentation;
import android.app.UiAutomation;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import yanews.malygin.tim.yanews.*;
import yanews.malygin.tim.yanews.ui.activity.LoginActivity;
import yanews.malygin.tim.yanews.util.L;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.fail;

/**
 * Created by timofey.malygin on 11/04/2017.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleTest2 {
    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);
    private File pictureFolder;
    private Instrumentation instrumentation;
    private UiAutomation uiAutomation;

    @Before
    public void init() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        uiAutomation = instrumentation.getUiAutomation();
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MMM_HHmmss/");
        final Context targetContext = getInstrumentation().getTargetContext();
        pictureFolder = new File(targetContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), sdf.format(new Date()));
        if (!pictureFolder.exists()) {
            pictureFolder.mkdirs();
        }
    }

    @After
    public void release(){
        rotateDevice(UiAutomation.ROTATION_FREEZE_0, null);
    }

    @Test
    public void a_rotate() {
        rotateDevice(UiAutomation.ROTATION_FREEZE_0, "0");
        rotateDevice(UiAutomation.ROTATION_FREEZE_90, "90");
        rotateDevice(UiAutomation.ROTATION_FREEZE_180, "180");
        rotateDevice(UiAutomation.ROTATION_FREEZE_270, "270");
    }

    @Test
    public void b_rotateAndCheck(){
        onView(ViewMatchers.withId(yanews.malygin.tim.yanews.R.id.login_text))
                .perform(typeText("loginForTest"));
        rotateDevice(UiAutomation.ROTATION_FREEZE_90, null);
        onView(ViewMatchers.withId(yanews.malygin.tim.yanews.R.id.login_text))
                .check(matches(withText("loginForTest")));
    }

    /**
     *
     * @param uiAutomation
     * @param rotationFreeze0
     * @param name - если передан null, то скринштон не будет сохраняться
     */
    private void rotateDevice(int rotationFreeze0,@Nullable String name) {
        if (!uiAutomation.setRotation(rotationFreeze0)) {
            fail();
        }
        try {
            // ждем поворота
            Thread.sleep(2000l);
            if(name!=null) {
                // получаем скриншот экрана
                final Bitmap bitmap = uiAutomation.takeScreenshot();
                // сохраняем его
                saveBitmap(bitmap, name);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveBitmap(Bitmap bitmap, String name) {
        FileOutputStream fos = null;
        try {
            File file = new File(pictureFolder, name + ".png");
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            L.e(e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    L.e(e);
                }
            }
        }
    }
}
