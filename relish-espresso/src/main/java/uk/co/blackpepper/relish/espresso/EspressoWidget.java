package uk.co.blackpepper.relish.espresso;

import android.app.Activity;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.view.View;
import uk.co.blackpepper.relish.core.Component;
import uk.co.blackpepper.relish.core.Widget;
import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static uk.co.blackpepper.relish.core.TestUtils.attempt;
import static org.hamcrest.Matchers.not;

/**
 * The type Espresso widget.
 */
public class EspressoWidget extends Widget<Matcher<View>> {
    /**
     * Instantiates a new Espresso widget.
     *
     * @param peer   the peer
     * @param parent the parent
     */
    public EspressoWidget(Matcher<View> peer, Component parent) {
        super(peer, parent);
    }

    @Override
    public void click() {
        attempt(new Runnable() {
            @Override
            public void run() {
                onView(get()).perform(ViewActions.click());
            }
        }, 100, 50);
    }

    @Override
    public void assertInvisible() {
        try {
            onView(get()).check(ViewAssertions.matches(not(isDisplayed())));
        } catch(NoMatchingViewException e) {
            // Do nothing
        }
    }

    @Override
    public void assertDisabled() {
        onView(get()).check(ViewAssertions.matches(not(isEnabled())));
    }

    @Override
    public void assertEnabled() {
        onView(get()).check(ViewAssertions.matches(isEnabled()));
    }

    @Override
    public Widget<Matcher<View>> scrollTo() {
        onView(get()).perform(ViewActions.scrollTo());
        return this;
    }

    @Override
    public void assertVisible() {
        onView(get()).check(ViewAssertions.matches(isDisplayed()));
    }

    @Override
    public String getStringValue() {
        throw new IllegalStateException("Cannot get string value for " + this);
    }
}
