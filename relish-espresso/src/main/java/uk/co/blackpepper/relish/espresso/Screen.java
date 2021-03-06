package uk.co.blackpepper.relish.espresso;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import org.junit.Assert;
import uk.co.blackpepper.relish.core.Component;

import static uk.co.blackpepper.relish.core.TestUtils.attempt;
import static uk.co.blackpepper.relish.espresso.AndroidUtils.getCurrentActivity;

/**
 * The type Screen.
 */
public class Screen extends Component {
    private Class<? extends Activity> activity;

    /**
     * Instantiates a new Screen.
     *
     * @param activity the activity
     */
    public Screen(Class<? extends Activity> activity) {
        super(null);
        this.activity = activity;
    }

    @Override
    public void assertVisible() {
        checkCurrentActivity(activity);
    }

    public void assertInvisible() {
        checkCurrentActivityNot(activity);
    }

    @Override
    public String getStringValue() {
        throw new IllegalStateException("Cannot get string value for " + this);
    }

    /**
     * Press back.
     */
    public void pressBack() {
        Espresso.pressBack();
    }


    private void checkCurrentActivity(final Class<? extends Activity> activityClass) {
        attempt(new Runnable() {
            @Override
            public void run() {
                Class<? extends Activity> aClass = getCurrentActivity().getClass();
                Assert.assertEquals((Class) activityClass, aClass);
            }
        }, 50, 200);
    }

    public void checkCurrentActivityNot(final Class<? extends Activity> activityClass) {
        attempt(new Runnable() {
            @Override
            public void run() {
                Class<? extends Activity> aClass = getCurrentActivity().getClass();
                junit.framework.Assert.assertNotSame(aClass, (Class) activityClass);
            }
        }, 50, 200);
    }

}
