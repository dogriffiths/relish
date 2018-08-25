package uk.co.blackpepper.relish.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.ElementShould;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import uk.co.blackpepper.relish.core.Component;
import uk.co.blackpepper.relish.core.Widget;

import java.awt.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static uk.co.blackpepper.relish.core.TestUtils.attempt;

/**
 * The type Selenide widget.
 */
public class SelenideWidget extends Widget<SelenideElement> {

    private By selector;
    private static boolean demoMode;

    /**
     * Instantiates a new Selenide widget.
     *
     * @param selector the selector
     * @param parent   the parent
     */
    public SelenideWidget(By selector, Component parent) {
        this($(selector), parent);
        this.selector = selector;
    }

    public static void setDemoMode(boolean dm) {
        SelenideWidget.demoMode = dm;
    }

    public static boolean isDemoMode() {
        return SelenideWidget.demoMode;
    }

    /**
     * Instantiates a new Selenide widget.
     *
     * @param peer   the peer
     * @param parent the parent
     */
    public SelenideWidget(SelenideElement peer, Component parent) {
        super(peer, parent);
    }

    @Override
    public SelenideElement get() {
        Component parent = getParent();
        if (selector != null) {
            if (parent != null) {
                if (parent instanceof SelenideWidget) {
                    return ((SelenideWidget) parent).get().$(selector);
                }
            }
        }
        return super.get();
    }

    @Override
    public void click() {
        if (demoMode) {
            moveMouseToComponent();
        }
        get().click();
    }

    /**
     * Click.
     *
     * @param x the x
     * @param y the y
     */
    public void click(int x, int y) {
        if (demoMode) {
            Point location = get().getLocation();
            moveMouseTo(new Point(location.getX() + x, location.getY() + y));
        }
        SelenideElement element = get();
        actions().moveToElement(element, x, y).click().perform();
    }

    /**
     * Data string.
     *
     * @param name the name
     * @return the string
     */
    public String data(String name) {
        return get().getAttribute("data-" + name);
    }

    @Override
    public void assertInvisible() {
        attempt(() -> {
            try {
                shouldBe(not(visible));
            } catch (ElementShould e) {
                // Do not throw state element exception because it will mean that it is invisible
                if (!(e.getCause() instanceof StaleElementReferenceException)) {
                    throw e;
                }
            }
        }, 500, 2);
    }

    @Override
    public void assertDisabled() {
        try {
            shouldBe(not(enabled));
        } catch (ElementShould e) {
            // Do not throw state element exception because it will mean that it is invisible
            if (!(e.getCause() instanceof StaleElementReferenceException)) {
                throw e;
            }
        }
    }

    /**
     * Should be.
     *
     * @param condition the condition
     */
    public void shouldBe(Condition condition) {
        try {
            get().shouldBe(condition);
        } catch (ElementNotFound e) {
            throw new RuntimeException("Cannot find " + this, e);
        }
    }

    @Override
    public void assertEnabled() {
        shouldBe(enabled);
    }

    @Override
    public SelenideWidget scrollTo() {
        get().scrollTo();
        return this;
    }

    @Override
    public void assertVisible() {
        shouldBe(visible);
    }

    @Override
    public String getStringValue() {
        return get().getText().trim();
    }

    /**
     * Actions actions.
     *
     * @return the actions
     */
    public Actions actions() {
        return new Actions(driver());
    }

    /**
     * Driver web driver.
     *
     * @return the web driver
     */
    public WebDriver driver() {
        return WebDriverRunner.getWebDriver();
    }

    @Override
    public String describe() {
        if (selector != null) {
            return selector.toString();
        }
        return super.describe();
    }

    public void moveMouseToComponent() {
        moveMouseTo(get().getLocation());
    }

    private void moveMouseTo(Point location) {
        try {
            Robot robot = new Robot();
            java.awt.Point currentPosition = MouseInfo.getPointerInfo().getLocation();
            int currentX = currentPosition.x;
            int currentY = currentPosition.y;
            Point browserPosition = WebDriverRunner.getWebDriver().manage().window().getPosition();
            int targetX = location.getX() + browserPosition.getX() + 30;
            int targetY = location.getY() + browserPosition.getY() + 100;
            int x = currentX;
            int y = currentY;
            int diffX = targetX - currentX;
            int diffY = targetY - currentY;
            for (int i = 0; i < 10; i++) {
                x += diffX / 10;
                y += diffY / 10;
                robot.mouseMove(x, y);
                Thread.sleep(50);
            }
            robot.mouseMove(targetX, targetY);
            Thread.sleep(500);
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
