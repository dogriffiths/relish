package uk.co.blackpepper.relish.espresso;

import android.support.test.espresso.action.ViewActions;
import android.view.View;
import org.hamcrest.Matcher;
import uk.co.blackpepper.relish.core.Component;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;

/**
 * The type Input text.
 */
public class InputText extends Text {
    /**
     * Instantiates a new Input text.
     *
     * @param peer   the peer
     * @param parent the parent
     */
    public InputText(Matcher<View> peer, Component parent) {
        super(peer, parent);
    }

    @Override
    public void setStringValue(String s) {
        enterText(s);
    }

    /**
     * Enter text.
     *
     * @param text the text
     */
    public void enterText(String text) {
        click();
        clearText();
        perform(ViewActions.typeText(text));
    }

    private void clearText() {
        perform(ViewActions.clearText()).perform(closeSoftKeyboard());
    }
}
