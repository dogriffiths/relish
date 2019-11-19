package uk.co.blackpepper.relish.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import uk.co.blackpepper.relish.core.Component;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static uk.co.blackpepper.relish.core.TestUtils.attempt;

/**
 * The type Radio buttons.
 */
public class RadioButtons extends InputWidget {

    private final By selector;

    /**
     * Instantiates a new Radio buttons.
     *
     * @param selector the selector
     * @param parent   the parent
     */
    public RadioButtons(By selector, Component parent) {
        super(selector, parent);
        this.selector = selector;
    }

    @Override
    public void setStringValue(String option)
    {
        attempt(() ->
        {
            if (radioExistsFor(option)) {
                if (SelenideWidget.isDemoMode()) {
                    moveMouseToComponent();
                }
                get().selectRadio(option);
            } else {
                $(By.xpath(String.format("//label[contains(text(),'%s')]", option))).click();
            }
        }, 1000, 10);
    }

    @Override
    public String getStringValue() {
        return $$(selector).filter(Condition.selected).first().getValue();
    }

    private boolean radioExistsFor(String option)
    {
        ElementsCollection allRadios = $$(selector);
        boolean exists = false;
        for (SelenideElement radio : allRadios) {
            String value = radio.getAttribute("value");
            if (value.equals(option)) {
                exists = true;
                break;
            }
        }
        return exists;
    }
}
