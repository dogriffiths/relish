package uk.co.blackpepper.relish.selenide;

import org.openqa.selenium.By;
import uk.co.blackpepper.relish.core.Component;

public class PageTitle extends SelenideWidget {
    public PageTitle(Component parent) {
        super(By.cssSelector("title"), parent.getRoot());
    }

    @Override
    public String getStringValue() {
        return get().attr("text");
    }
}