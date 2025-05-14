package org.example;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.example.utilities.InteractionUtils;
import org.example.utilities.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class FirstTest {
    public AndroidDriver driver;
    InteractionUtils interactionUtils;
    WaitUtils waitUtils;

    @BeforeTest
    public void setup(){
        String appiumServerURL = "http://127.0.0.1:4723";

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability("platformName", "Android");
        dc.setCapability("appium:automationName", "uiautomator2");
        dc.setCapability("appium:deviceName", "Pixel 9 01");
        dc.setCapability("appium:noReset", false); // Reset app state
        dc.setCapability("appium:fastReset", true); // Reset the app

        try{
            driver = new AndroidDriver(new URL(appiumServerURL), dc);
            //Terminate the App in case another test crashed.
            driver.terminateApp("com.tubitv");
        } catch (MalformedURLException me){
            me.printStackTrace();
        }

    }

    @Test
    public void TubiTest(){
        WebElement element;
        interactionUtils = new InteractionUtils(driver);
        waitUtils = new WaitUtils(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        interactionUtils.clickOnElement(InteractionUtils.Locator.XPATH,
                "(//android.widget.TextView[@content-desc='Tubi'])");


        //Wait for the skip button to be visible. If it is not visible (pressed previously), we carry on.
        waitUtils.waitForElementDisplayed(WaitUtils.Locator.ID, "com.tubitv:id/chip_all");
        waitUtils.waitForElementEnabled(WaitUtils.Locator.ID, "com.tubitv:id/chip_all");
        List<WebElement> elements;
        elements = driver.findElements(By.id("com.tubitv:id/top_skip_button_onboarding"));

        if (!elements.isEmpty()){
            interactionUtils.clickOnElement(InteractionUtils.Locator.ID, "com.tubitv:id/top_skip_button_onboarding");
        }

        interactionUtils.clickOnElement(InteractionUtils.Locator.XPATH,
                "//android.widget.RadioButton[@text='Movies']");

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
        List<WebElement> familyMovies;
        familyMovies = driver.findElements(By.xpath("//android.widget.TextView[@resource-id='com.tubitv:id/container_name' and @text='Family Movies']"));
        while (familyMovies.isEmpty() && maxScrolls < 15) {
            interactionUtils.scrollForward();
            familyMovies = driver.findElements(By.xpath("//android.widget.TextView[@resource-id='com.tubitv:id/container_name' and @text='Family Movies']"));
            maxScrolls++;
        }

         interactionUtils.clickOnElement(InteractionUtils.Locator.XPATH,
                "//android.widget.TextView[@resource-id='com.tubitv:id/container_name' and @text='Family Movies']");

        waitUtils.waitForElementDisplayed(WaitUtils.Locator.XPATH,
                "//android.widget.TextView[@resource-id='com.tubitv:id/titlebar_title_text_view' and @text='Family Movies']");
        waitUtils.waitForElementEnabled(WaitUtils.Locator.XPATH,
                "//android.widget.TextView[@resource-id='com.tubitv:id/titlebar_title_text_view' and @text='Family Movies']");

        List<WebElement> movies;
        String expectedName = "Jurassic Planet";
        boolean movieFound = false;

        while(movieFound == false){
            movies = driver.findElements(By.xpath("(//android.widget.ImageView[@resource-id='com.tubitv:id/video_poster_image_view'])"));
            for (int i = 1; i <= movies.size(); i++){
                interactionUtils.clickOnElement(InteractionUtils.Locator.XPATH,
                        "(//android.widget.ImageView[@resource-id='com.tubitv:id/video_poster_image_view'])["+i+"]");
                waitUtils.waitForElementDisplayed(WaitUtils.Locator.ID,
                        "com.tubitv:id/head_info_title");

                WebElement movieName = driver.findElement(By.id("com.tubitv:id/head_info_title"));
                String nameOfMovie = movieName.getDomAttribute("text");
                if(nameOfMovie.equals(expectedName)){
                    System.out.println("Movie Name: " + nameOfMovie);

                    WebElement movieInfoElement = driver.findElement(By.id("com.tubitv:id/head_info_duration_and_tags"));
                    String movieInfoString = movieInfoElement.getDomAttribute("text");

                    System.out.println("Movie Info: " + movieInfoString);

                    WebElement movieDescriptionElement = driver.findElement(By.id("com.tubitv:id/description"));
                    String movieDescriptionString = movieDescriptionElement.getDomAttribute("text");

                    System.out.println("Movie Description: " + movieDescriptionString);
                    movieFound = true;
                    break;
                } else {
                    driver.findElement(By.id("com.tubitv:id/top_bar_back_icon")).click();
                    wait.until((ExpectedCondition<Boolean>) d ->
                            d.findElement(By.xpath("//android.widget.TextView[@resource-id='com.tubitv:id/titlebar_title_text_view' and @text='Family Movies']")).isDisplayed());
                }
            }

            driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"));
        }
    }

    @AfterTest
    public void close(){
        //Terminate the App to ensure our next test starts fresh.
        driver.terminateApp("com.tubitv");
        driver.quit();
    }


}
