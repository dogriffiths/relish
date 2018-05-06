package uk.co.blackpepper.relish.espresso;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * The type Confirm dialog.
 */
public class ConfirmDialog extends EspressoWidget {
    /**
     * Instantiates a new Espresso widget.
     *
     * @param parent the parent
     */
    public ConfirmDialog(Screen parent) {
        super(withId(android.R.id.button1), parent);
    }

    /**
     * Confirm button espresso widget.
     *
     * @return the espresso widget
     */
    public EspressoWidget confirmButton() {
        return new EspressoWidget(withId(android.R.id.button1), getParent());
    }

    /**
     * Decline button button.
     *
     * @return the button
     */
    public EspressoWidget declineButton() {
        return new EspressoWidget(withId(android.R.id.button2), getParent());
    }

    @Override
    public void assertVisible() {
        confirmButton().assertVisible();
    }

}