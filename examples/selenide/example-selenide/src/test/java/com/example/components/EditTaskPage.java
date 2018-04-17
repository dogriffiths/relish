package com.example.components;

import org.openqa.selenium.By;

import uk.co.blackpepper.relish.selenide.InputText;
import uk.co.blackpepper.relish.selenide.Page;
import uk.co.blackpepper.relish.selenide.SelenideWidget;

public class EditTaskPage extends Page {
    public EditTaskPage() {
        super("/edit.html");
    }

    public InputText name() {
        return new InputText(By.id("name"), this);
    }

    public SelenideWidget saveButton() {
        return new SelenideWidget(By.className("saveButton"), this);
    }
}
