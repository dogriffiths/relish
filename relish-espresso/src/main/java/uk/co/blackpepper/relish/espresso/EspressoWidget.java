package uk.co.blackpepper.relish.espresso;

import android.app.Activity;
import android.support.test.espresso.*;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.view.View;
import junit.framework.AssertionFailedError;
import uk.co.blackpepper.relish.core.Component;
import uk.co.blackpepper.relish.core.TestUtils;
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
                try {
                    perform(ViewActions.click());
                }catch(SecurityException se) {
                    // It's probably that annoying "Tap to search Google and more" popup
                    Espresso.closeSoftKeyboard();
                    perform(ViewActions.click());
                }
            }
        }, 100, 50);
    }

    public EspressoWidget check(ViewAssertion viewAssertion) {
        get().check(viewAssertion);
        return this;
    }

    public EspressoWidget perform(ViewAction viewAction) {
        assertVisible();
        scrollTo();
        get().perform(viewAction);
        return this;
    }

    @Override
    public void assertInvisible() {
        try {
            check(ViewAssertions.matches(not(isDisplayed())));
        } catch(NoMatchingViewException e) {
            // Do nothing
        }
    }

    @Override
    public void assertDisabled() {
        check(ViewAssertions.matches(not(isEnabled())));
    }

    @Override
    public void assertEnabled() {
        check(ViewAssertions.matches(isEnabled()));
    }

    @Override
    public Widget<ViewInteraction> scrollTo() {
        try {
            get().perform(ViewActions.scrollTo());
        } catch(RuntimeException e) {
            // Do nothing. It's probably not in a scroll widget.
        }
        return this;
    }

    @Override
    public void assertVisible() {
        try {
            check(ViewAssertions.matches(isDisplayed()));
        } catch(AssertionFailedError e) {
            scrollTo();
            check(ViewAssertions.matches(isDisplayed()));
        }
    }

    @Override
    public String getStringValue() {
        throw new IllegalStateException("Cannot get string value for " + this);
    }
}
