package org.example.pages.tubi;

import io.appium.java_client.android.AndroidDriver;
import org.example.utilities.InteractionUtils;
import org.example.utilities.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TubiHome {
    private InteractionUtils interactionUtils;
    private AndroidDriver driver;
    private WaitUtils waitUtils;

    public TubiHome(AndroidDriver givenDriver){
        this.driver = givenDriver;
        interactionUtils = new InteractionUtils(givenDriver);
        waitUtils = new WaitUtils(givenDriver);
    }

    private static final String tubiHomeOnboardingImageId = "com.tubitv:id/onboarding_image_view";
    private static final String tubiHomeAllItemsButtonId = "com.tubitv:id/chip_all";
    private static final String tubiHomeSkipLoginButtonXpath = "//android.widget.TextView[@resource-id='com.tubitv:id/top_skip_button_onboarding']";
    private static final String tubiHomeFilterButtonXpath = "//android.widget.RadioButton[@text='%s']";
    private static final String tubiHomeSectionHeaderXpath = "//android.widget.TextView[@resource-id='com.tubitv:id/container_name' and @text='%s']";

     public void waitForTubiToOpen(){
         //If we have not hit the skip button yet, we get an onboarding screen.
         //If we've hit the skip button previously, we get the normal home screen.
         //We try both.
         try{
             waitUtils.waitForElementDisplayed(WaitUtils.Locator.ID, tubiHomeOnboardingImageId);
             waitUtils.waitForElementEnabled(WaitUtils.Locator.ID, tubiHomeOnboardingImageId);
         } catch (TimeoutException te) {
             waitUtils.waitForElementDisplayed(WaitUtils.Locator.ID, tubiHomeAllItemsButtonId);
             waitUtils.waitForElementEnabled(WaitUtils.Locator.ID, tubiHomeAllItemsButtonId);
         }
     }

     //We check to see if the skip button exists. If it does, we click it and then wait for the home page to load.
     public void skipLoginIfNecessary(){
         List<WebElement> elements;
         elements = driver.findElements(By.xpath(tubiHomeSkipLoginButtonXpath));

         if (!elements.isEmpty()){
             interactionUtils.clickOnElement(InteractionUtils.Locator.XPATH, tubiHomeSkipLoginButtonXpath);
             waitUtils.waitForElementDisplayed(WaitUtils.Locator.ID, tubiHomeAllItemsButtonId);
             waitUtils.waitForElementEnabled(WaitUtils.Locator.ID, tubiHomeAllItemsButtonId);
         }
     }

     public void clickHomePageFilterButton(String buttonName){
         interactionUtils.clickOnElement(InteractionUtils.Locator.XPATH,
                 String.format(tubiHomeFilterButtonXpath, buttonName));
     }

     public void clickSection(String sectionName){
         interactionUtils.clickOnElement(InteractionUtils.Locator.XPATH,
                 String.format(tubiHomeSectionHeaderXpath, sectionName));
     }

     public void scrollToSection(String givenSection){
         //If we run the automation too many times, because we're not logged in, Tubi complains and
         //inserts a Continue Watching interruption.
         //We use findElements here (instead of findElement) because it returns a List.
         //If it fails to find the Continue Watching interruption, it returns an empty list and carries on.
         driver.findElements(interactionUtils.scrollToElement("Continue Watching"));

         //Reset the page in case of Continue Watching Interruption
         driver.findElement(interactionUtils.scrollToTopOfPage("15"));

         //We need to create a method of scrolling one screen at a time, and checking for the existence of
         //the element each scroll, because the Continue Watching Interruption conflicts with scrollIntoView.
         int maxScrolls = 0;
         List<WebElement> sectionName;
         sectionName = driver.findElements(By.xpath(String.format(tubiHomeSectionHeaderXpath, givenSection)));
         while (sectionName.isEmpty() && maxScrolls < 15) {
             interactionUtils.scrollForward();
             sectionName = driver.findElements(By.xpath(String.format(tubiHomeSectionHeaderXpath, givenSection)));
             maxScrolls++;
         }
     }
}
