package uk.co.blackpepper.relish.selenide;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import uk.co.blackpepper.relish.core.Component;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static uk.co.blackpepper.relish.core.TestUtils.attempt;

/**
 * The type Input text.
 */
public class InputText extends InputWidget {
    /**
     * Instantiates a new Input text.
     *
     * @param selector the selector
     * @param parent   the parent
     */
    public InputText(By selector, Component parent) {
        super(selector, parent);
    }

    /**
     * Instantiates a new Input text.
     *
     * @param element the element
     * @param parent  the parent
     */
    public InputText(SelenideElement element, Component parent) {
        super(element, parent);
    }

    /**
     * Enter text.
     *
     * @param text the text
     */
    public void enterText(String text) {
        attempt(() -> {
            click();
            clear();
            sendKeys(text);
        }, 500, 2);
    }

    /**
     * Send keys.
     *
     * @param text the text
     */
    public void sendKeys(String text) {
        if ((text != null) && (text.length() > 0)) {
            if (SelenideWidget.isDemoMode()) {
                for (int i = 0; i < text.length(); i++) {
                    char c = text.charAt(i);
                    get().sendKeys("" + c);
                    try {
                        Thread.sleep((int)(Math.random() * 50));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                get().sendKeys(text);
            }
        }
    }

    @Override
    public void setStringValue(String value) {
        enterText(value);
    }

    /**
     * Clear.
     */
    public void clear() {
        this.get().clear();
        // Both call clear and then select all and delete, because sometimes clear does not appear to work...
        selectAll();
        this.sendKeys(Keys.chord(Keys.DELETE));
    }

    public void selectAll() {
        WebDriver webDriver = WebDriverRunner.getWebDriver();
        String driverName = webDriver.toString();
        boolean isMac = driverName.toUpperCase().contains("MAC");
        if (isMac) {
            this.get().sendKeys(Keys.chord(Keys.COMMAND,"a"));
        } else {
            this.get().sendKeys(Keys.chord(Keys.CONTROL,"a"));
        }
    }
}
