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
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static org.hamcrest.Matchers.allOf;
import static uk.co.blackpepper.relish.core.TestUtils.attempt;
import static org.hamcrest.Matchers.not;

/**
 * The type Espresso widget.
 */
public class EspressoWidget extends Widget<ViewInteraction> {

    private final Matcher<View> matcher;

    /**
     * Instantiates a new Espresso widget.
     *
     * @param matcher   the peer
     * @param parent the parent
     */
    public EspressoWidget(Matcher<View> matcher, Component parent) {
        super(onView(matcher), parent);
        this.matcher = matcher;
    }

    @Override
    public ViewInteraction get() {
        Component parent = getParent();
        if (matcher != null) {
            if (parent != null) {
                if (parent instanceof EspressoWidget) {
                    return onView(allOf(matcher, isDescendantOfA(((EspressoWidget) parent).matcher)));
                }
            }
        }
        return super.get();
    }

    @Override
    public void click() {
        attempt(new Runnable() {
            @Override
            public void run() {
                get().perform(ViewActions.click());
            }
        }, 100, 50);
    }

    @Override
    public void assertInvisible() {
        try {
            get().check(ViewAssertions.matches(not(isDisplayed())));
        } catch(NoMatchingViewException e) {
            // Do nothing
        }
    }

    @Override
    public void assertDisabled() {
        get().check(ViewAssertions.matches(not(isEnabled())));
    }

    @Override
    public void assertEnabled() {
        get().check(ViewAssertions.matches(isEnabled()));
    }

    @Override
    public Widget<ViewInteraction> scrollTo() {
        get().perform(ViewActions.scrollTo());
        return this;
    }

    @Override
    public void assertVisible() {
        get().check(ViewAssertions.matches(isDisplayed()));
    }

    @Override
    public String getStringValue() {
        throw new IllegalStateException("Cannot get string value for " + this);
    }
}
