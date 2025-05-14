package org.example.utilities;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class InteractionUtils {
    public enum Locator{
        ID,
        XPATH,
        CLASS,
        TEXT
    }
    private WebDriver driver;
    private WaitUtils waitUtils;

    public InteractionUtils(AndroidDriver givenDriver){
        this.driver = givenDriver;
        this.waitUtils = new WaitUtils(givenDriver);
    }

    public void clickOnElement(Locator givenLocator, String givenIdentifier){
        WebElement element;
        switch (givenLocator){
            case XPATH :
                waitUtils.waitForElementDisplayed(WaitUtils.Locator.XPATH, givenIdentifier);
                waitUtils.waitForElementEnabled(WaitUtils.Locator.XPATH, givenIdentifier);
                element = driver.findElement(By.xpath(givenIdentifier));
                element.click();
                break;
            case ID :
                waitUtils.waitForElementDisplayed(WaitUtils.Locator.ID, givenIdentifier);
                waitUtils.waitForElementEnabled(WaitUtils.Locator.ID, givenIdentifier);
                element = driver.findElement(By.xpath(givenIdentifier));
                element.click();
                break;
        }
    }
}
