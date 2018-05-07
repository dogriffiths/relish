package uk.co.blackpepper.relish.espresso;

import android.view.View;
import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.containsString;

public class Dialog extends EspressoWidget {

    public static final Matcher<View> DIALOG_TITLE = withClassName(containsString("DialogTitle"));

    /**
     * Instantiates a new Espresso widget.
     *
     * @param parent  the parent
     */
    public Dialog(Screen parent) {
        super(allOf(
                withClassName(containsString("DecorView")),
                hasDescendant(DIALOG_TITLE)
        ), parent);
    }

    public Text title() {
        return new Text(DIALOG_TITLE, this);
    }

    public EspressoWidget okButton() {
        return new EspressoWidget(withId(android.R.id.button1), this);
    }

    public EspressoWidget cancelButton() {
        return new EspressoWidget(withId(android.R.id.button2), this);
    }
}
