package com.accesshq.tests;

import com.accesshq.ui.HomePage;
import com.accesshq.ui.WebDialog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class PlaygroundTestSuite {

    private ChromeDriver driver;

    @BeforeEach
    public void SetUp() {
        driver = new ChromeDriver();
        driver.get("https://d1iw6mb9di5l9r.cloudfront.net/");
        driver.manage().window().maximize();
        SetImplicitWait(driver);
    }

    @Test
    public void ExampleTest() {
        driver.findElement(By.id("forename")).sendKeys("Mark");
        driver.quit();
    }

    @Test
    public void ClickMeButtonTest() throws InterruptedException {
        By locator = By.cssSelector("[role=button]");
        WebElement movingButton = driver.findElement(locator);
        movingButton.click();
        new WebDriverWait(driver, 3).until(
                ExpectedConditions.textToBe(locator, "CLICK ME UP!"));
        Assertions.assertEquals("CLICK ME UP!", movingButton.getText());
    }

    @Test
    public void LoginButtonTest() {
        var homePage = new HomePage(driver);
        homePage.clickLoginButton();
        homePage.waitUntilAlertMsgVisible();
        Assertions.assertEquals("You clicked the login button", homePage.getAlertMessageText());
    }

    @Test
    public void LoginDialog_Login_ErrorMessagesTest() {
        //Arrange
        new HomePage(driver).clickProfileElement();

        //Act
        var webDialog = new WebDialog(driver);
        webDialog.clickLoginButton();
        var errorMessages = webDialog.getErrorMessages();

        //Assert
        for(var messageElement : errorMessages) {
            Assertions.assertEquals("Invalid user and password", messageElement.getText());
        }
    }

    @Test
    public void Form_Submit_ErrorMessagesTest() {
        //Arrange
        driver.findElement(By.cssSelector("[aria-label=forms]")).click();
        var form = driver.findElement(By.className("modern"));

        //Act
        clickButton("submit");

        DoWithoutImplicit(driver, () -> {
            //Assert
            Assertions.assertEquals("Your name is required", driver.findElement(By.id("name-err")).getText());
            Assertions.assertEquals("Your email is required", driver.findElement(By.id("email-err")).getText());
            Assertions.assertEquals("You must agree to continue", driver.findElement(By.id("agree-err")).getText());
        });
    }

    private void DoWithoutImplicit(ChromeDriver driver, Runnable o) {
        RemoveImplicitWait(driver);
        o.run();
        SetImplicitWait(driver);
    }

    private void SetImplicitWait(ChromeDriver driver) {
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
    }

    private void RemoveImplicitWait(ChromeDriver driver) {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    @Test
    public void Form_ValidInput_ErrorMessagesTest() {
        //Arrange
        driver.findElement(By.cssSelector("[aria-label=forms]")).click();

        clickButton("submit");

        //Act
        driver.findElement(By.id("name")).sendKeys("Mark");
        driver.findElement(By.id("email")).sendKeys("Mark@email.com");
        driver.findElement(By.cssSelector("[for=agree]")).click();

        RemoveImplicitWait(driver);
        Assertions.assertTrue(driver.findElements(By.id("name-err")).size() == 0);
        Assertions.assertTrue(driver.findElements(By.id("email-err")).size() == 0);
        Assertions.assertTrue(driver.findElements(By.id("agree-err")).size() == 0);
    }

    private void clickButton(String buttonName) {
        buttonName = buttonName.toLowerCase();
        WebElement form = driver.findElement(By.className("modern"));
        for (var button : form.findElements(By.tagName("button"))) {
            if (button.getText().toLowerCase().contains(buttonName)) {
                button.click();
                break;
            }
        }
    }

    @AfterEach
    public void CleanUp() {
        driver.quit();
    }
}
