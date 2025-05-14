package org.example.utilities;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private AndroidDriver driver;
    private WebDriverWait wait;

    public enum Locator{
        ID,
        XPATH,
        CLASS,
        TEXT
    }

    WaitUtils(AndroidDriver givenDriver){
        this.driver = givenDriver;
        //This could be turned into a factory where we have a variety of wait times depending on the page in question.
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(40L));
    }

    public void waitForElementDisplayed(Locator givenLocator, String givenIdentifier){
        switch (givenLocator){
            case XPATH:
                wait.until((ExpectedCondition<Boolean>) d ->
                        d.findElement(By.xpath(givenIdentifier)).isDisplayed());
                break;
            case ID:
                wait.until((ExpectedCondition<Boolean>) d ->
                        d.findElement(By.id(givenIdentifier)).isDisplayed());
                break;
        }
    }

    public void waitForElementEnabled(Locator givenLocator, String givenIdentifier) {
        switch (givenLocator) {
            case XPATH:
                wait.until((ExpectedCondition<Boolean>) d ->
                        d.findElement(By.xpath(givenIdentifier)).isEnabled());
                break;
            case ID:
                wait.until((ExpectedCondition<Boolean>) d ->
                        d.findElement(By.id(givenIdentifier)).isEnabled());
                break;
        }
    }
}
