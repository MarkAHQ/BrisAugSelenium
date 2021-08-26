package com.accesshq.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WebDialog {

    private final WebElement thisElement;
    private final WebDriver driver;
    private final By messagesLocator = By.className("v-messages__message");

    public WebDialog(WebDriver driver) {
        this.driver = driver;
        thisElement = driver.findElement(By.className("v-dialog--active"));
    }

    public void clickLoginButton() {
        thisElement.findElement(By.id("loginButton")).click();
        new WebDriverWait(driver, 3).until(ExpectedConditions
                .visibilityOf(thisElement.findElement(messagesLocator)));
    }

    public List<WebElement> getErrorMessages() {
        return thisElement.findElements(messagesLocator);
    }
}
