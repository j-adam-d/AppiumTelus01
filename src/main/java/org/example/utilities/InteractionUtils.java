package org.example.utilities;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

    public String getElementText(Locator givenLocator, String givenIdentifier){
        WebElement element;
        String textResult = "";
        switch (givenLocator){
            case XPATH :
                waitUtils.waitForElementDisplayed(WaitUtils.Locator.XPATH, givenIdentifier);
                waitUtils.waitForElementEnabled(WaitUtils.Locator.XPATH, givenIdentifier);
                element = driver.findElement(By.xpath(givenIdentifier));
                textResult = element.getDomAttribute("text");
                break;
            case ID :
                waitUtils.waitForElementDisplayed(WaitUtils.Locator.ID, givenIdentifier);
                waitUtils.waitForElementEnabled(WaitUtils.Locator.ID, givenIdentifier);
                element = driver.findElement(By.id(givenIdentifier));
                textResult = element.getDomAttribute("text");
                break;
        }
        return textResult;
    }

    public void scrollForward(){
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"));
    }

    public By scrollToTopOfPage(String numberOfScrolls){
        return AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollToBeginning(" + numberOfScrolls + ")");
    }

    public By scrollToElement(String givenIdentifier){
        return AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector()" +
                        ".textContains(\"" + givenIdentifier + "\"))");
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
                element = driver.findElement(By.id(givenIdentifier));
                element.click();
                break;
        }
    }
}
