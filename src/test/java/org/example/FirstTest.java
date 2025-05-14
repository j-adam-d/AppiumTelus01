package org.example;
import io.appium.java_client.android.AndroidDriver;
import org.example.pages.AndroidHome;
import org.example.pages.tubi.TubiHome;
import org.example.pages.tubi.TubiMedia;
import org.example.pages.tubi.TubiSection;
import org.example.utilities.InteractionUtils;
import org.example.utilities.WaitUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class FirstTest {
    public AndroidDriver driver;
    InteractionUtils interactionUtils;
    WaitUtils waitUtils;
    AndroidHome androidHome;
    TubiHome tubiHome;
    TubiSection tubiSection;
    TubiMedia tubiMedia;

    @BeforeTest
    public void setup(){
        String appiumServerURL = "http://127.0.0.1:4723";

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability("platformName", "Android");
        dc.setCapability("appium:automationName", "uiautomator2");
        dc.setCapability("appium:deviceName", "Pixel 9 01");
        dc.setCapability("appium:noReset", false);
        dc.setCapability("appium:fastReset", true);

        try{
            driver = new AndroidDriver(new URL(appiumServerURL), dc);

            //Terminate the app to ensure a clean and valid test state.
            driver.terminateApp("com.tubitv");
        } catch (MalformedURLException me){
            me.printStackTrace();
        }
    }

    @Test
    public void TubiTest(){
        interactionUtils = new InteractionUtils(driver);
        waitUtils = new WaitUtils(driver);
        androidHome = new AndroidHome(driver);
        tubiHome = new TubiHome(driver);
        tubiSection = new TubiSection(driver);
        tubiMedia = new TubiMedia(driver);

        //Launch the Tubi App
        androidHome.openApp("Tubi");

        tubiHome.waitForTubiToOpen();

        tubiHome.skipLoginIfNecessary();

        tubiHome.clickHomePageFilterButton("Movies");

        tubiHome.scrollToSection("Family Movies");

        tubiHome.clickSection("Family Movies");

        tubiSection.waitForSectionToLoad("Family Movies");

        tubiSection.scrollToMovieAndOpen("Little Monsters");

        String movieName = tubiMedia.getMediaName();
        System.out.println("Movie Name: " + movieName);
        Assert.assertEquals(movieName, "Little Monsters", "Movie Name did not match.");

        String movieInfoString = tubiMedia.getMediaInfo();
        System.out.println("Movie Info: " + movieInfoString);
        Assert.assertEquals(movieInfoString, "1988 · 1 hr 41 min · Comedy, Kids & Family", "Movie Info did not match.");

        String movieDescriptionString = tubiMedia.getMediaDescription();
        System.out.println("Movie Description: " + movieDescriptionString);
        Assert.assertEquals(movieDescriptionString, "After young Brian befriends the monster under his bed, he learns that a monster world with no parents may be more than he bargained for.", "Movie Description did not match.");
    }

    @AfterTest
    public void close(){
        //Terminate the App to ensure our next test starts fresh.
        driver.terminateApp("com.tubitv");
        driver.quit();
    }


}
