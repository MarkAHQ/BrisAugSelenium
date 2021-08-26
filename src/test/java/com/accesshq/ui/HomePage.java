package com.accesshq.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;

public class HomePage {

    private final WebDriver driver;

    public HomePage (WebDriver driver) {
        this.driver = driver;
    }

    public void clickLoginButton() {
        var loginButtons = driver.findElements(By.id("loginButton"));

        for(var button : loginButtons) {
            if(button.isDisplayed()) {
                button.click();
                break;
            }
        }
    }

    public WebElement getAlertMsgElement() {
        return driver.findElement(By.className("alert-message"));
    }

    public String getAlertMessageText() {
        return getAlertMsgElement().getText();
    }

    public void clickProfileElement() {
        getUsersElement().click();
    }

    public void waitUntilAlertMsgVisible() {
        new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOf(getAlertMsgElement()));
    }

    private WebElement getUsersElement() {
        return driver.findElement(By.cssSelector("[aria-label=users]"));
    }

    public void clickPlanetButton() {
        driver.findElement(By.cssSelector("[aria-label=planets]")).click();
    }
}
