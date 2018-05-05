package uk.co.blackpepper.relish.espresso;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import android.widget.TextView;
import org.hamcrest.Matcher;
import uk.co.blackpepper.relish.core.Component;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;

/**
 * The type Input text.
 */
public class Text extends EspressoWidget {
    /**
     * Instantiates a new Input text.
     *
     * @param peer   the peer
     * @param parent the parent
     */
    public Text(Matcher<View> peer, Component parent) {
        super(peer, parent);
    }

    @Override
    public String getStringValue() {
        CaptureTextAction captureText = new CaptureTextAction();
        perform(captureText);
        return captureText.getText();
    }
}

class CaptureTextAction implements ViewAction {
    private String text;

    @Override
    public Matcher<View> getConstraints() {
        return isAssignableFrom(TextView.class);
    }

    @Override
    public String getDescription() {
        return "getting text from a TextView";
    }

    @Override
    public void perform(UiController uiController, View view) {
        TextView tv = (TextView) view; //Save, because of check in getConstraints()
        text = tv.getText().toString();
    }

    public String getText() {
        return text;
    }
}
