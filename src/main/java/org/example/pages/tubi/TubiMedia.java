package org.example.pages.tubi;

import io.appium.java_client.android.AndroidDriver;
import org.example.utilities.InteractionUtils;
import org.example.utilities.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TubiMedia {
    private InteractionUtils interactionUtils;
    private AndroidDriver driver;
    private WaitUtils waitUtils;

    public TubiMedia(AndroidDriver givenDriver){
        this.driver = givenDriver;
        interactionUtils = new InteractionUtils(givenDriver);
        waitUtils = new WaitUtils(givenDriver);
    }

    private static final String tubiMediaNameId = "com.tubitv:id/head_info_title";
    private static final String tubiMediaInfoId = "com.tubitv:id/head_info_duration_and_tags";
    private static final String tubiMediaDescriptionId = "com.tubitv:id/description";
    private static final String tubiBackButtonId ="com.tubitv:id/top_bar_back_icon";

    public String getMediaName(){
        waitUtils.waitForElementDisplayed(WaitUtils.Locator.ID, tubiMediaNameId);
        return interactionUtils.getElementText(InteractionUtils.Locator.ID, tubiMediaNameId);
    }

    public String getMediaInfo(){
        waitUtils.waitForElementDisplayed(WaitUtils.Locator.ID, tubiMediaInfoId);
        return interactionUtils.getElementText(InteractionUtils.Locator.ID, tubiMediaInfoId);
    }

    public String getMediaDescription(){
        waitUtils.waitForElementDisplayed(WaitUtils.Locator.ID, tubiMediaDescriptionId);
        return interactionUtils.getElementText(InteractionUtils.Locator.ID, tubiMediaDescriptionId);
    }

    public void pressBackButton(){
        interactionUtils.clickOnElement(InteractionUtils.Locator.ID, tubiBackButtonId);
    }

}
