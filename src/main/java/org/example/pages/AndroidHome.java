package org.example.pages;

import io.appium.java_client.android.AndroidDriver;
import org.example.utilities.InteractionUtils;
import org.openqa.selenium.WebDriver;

public class AndroidHome {
    private InteractionUtils interactionUtils;
    private AndroidDriver driver;

    public AndroidHome(AndroidDriver givenDriver){
        this.driver = givenDriver;
        interactionUtils = new InteractionUtils(this.driver);
    }

     private static final String appXpath = "(//android.widget.TextView[@content-desc='%s'])";

     public void openApp(String appName){
         System.out.println(String.format(appXpath, appName));
         interactionUtils.clickOnElement(InteractionUtils.Locator.XPATH,
                 String.format(appXpath, appName));
     }
}
