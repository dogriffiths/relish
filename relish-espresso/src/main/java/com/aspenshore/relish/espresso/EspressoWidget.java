package com.aspenshore.relish.espresso;

import android.app.Activity;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.view.View;
import com.aspenshore.relish.core.Component;
import com.aspenshore.relish.core.Widget;
import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static com.aspenshore.relish.core.TestUtils.attempt;
import static org.hamcrest.Matchers.not;

public class EspressoWidget extends Widget<ViewInteraction> {
    public EspressoWidget(ViewInteraction peer, Component parent) {
        super(peer, parent);
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
