package uk.co.blackpepper.relish.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static uk.co.blackpepper.relish.core.TestUtils.attempt;

public class Clipboard {
    public void matches(String expected) {
        WebDriver webDriver = WebDriverRunner.getWebDriver();
        String driverName = webDriver.getClass().getName();
        if (driverName.indexOf("RemoteWebDriver") != -1) {
            String fieldId = "HGJHGJJH12343433";
            Selenide.executeJavaScript(
                    "{\n" +
                            "    var body = document.getElementsByTagName(\"BODY\")[0];\n" +
                            "    var textField = document.createElement(\"input\");\n" +
                            "    textField.setAttribute('id', '" + fieldId + "');\n" +
                            "    textField.setAttribute('style', 'opacity: 0; position: absolute; top: 0; left 0; z-index: 2000');\n" +
                            "    body.appendChild(textField);\n" +
                            "    textField.select();\n" +
                            "}\n"
            );
            WebDriver driver = WebDriverRunner.getWebDriver();
            WebElement element = driver.findElement(By.id(fieldId));
            if (driverName.toUpperCase().contains("MAC")) {
                element.sendKeys(Keys.chord(Keys.COMMAND, "v"));
            } else {
                element.sendKeys(Keys.chord(Keys.CONTROL, "v"));
            }
            try {
                Selenide.$(By.id(fieldId)).waitUntil(Condition.value(expected), 1000);
            } catch(AssertionError e) {
                fail("Wrong value on clipboard\nExpected:\n\t" + expected + "\nbut was:\n\t" + Selenide.$(By.id(fieldId)).getValue());
            } finally {
                Selenide.executeJavaScript(
                        "{\n" +
                                "    var element = document.getElementById('" + fieldId + "');\n" +
                                "    element.parentNode.removeChild(element);\n" +
                                "}"
                );
            }

        } else {
            attempt(() -> {
                String clipboardText = null;
                try {
                    clipboardText = (String) Toolkit.getDefaultToolkit()
                            .getSystemClipboard().getData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    throw new RuntimeException("Unable to get clipboard text", e);
                }
                assertEquals("Wrong value on clipboard", expected, clipboardText);
            }, 500, 4);
        }
    }
}
