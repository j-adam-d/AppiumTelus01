package org.example.pages.tubi;

import io.appium.java_client.android.AndroidDriver;
import org.example.utilities.InteractionUtils;
import org.example.utilities.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TubiSection {
    private InteractionUtils interactionUtils;
    private AndroidDriver driver;
    private WaitUtils waitUtils;

    public TubiSection(AndroidDriver givenDriver){
        this.driver = givenDriver;
        interactionUtils = new InteractionUtils(givenDriver);
        waitUtils = new WaitUtils(givenDriver);
    }

    private static final String tubiSectionHeaderXpath = "//android.widget.TextView[@resource-id='com.tubitv:id/titlebar_title_text_view' and @text='%s']";
    private static final String tubiSectionMoviePosterXpath = "(//android.widget.ImageView[@resource-id='com.tubitv:id/video_poster_image_view'])";
    private static final String tubiSectionSpecificMoviePosterXpath = "(//android.widget.ImageView[@resource-id='com.tubitv:id/video_poster_image_view'])[%s]";
    private static final String tubiSectionHeaderNoNameXpath = "//android.widget.TextView[@resource-id='com.tubitv:id/titlebar_title_text_view']";

    public void waitForSectionToLoad(String sectionName){
        waitUtils.waitForElementDisplayed(WaitUtils.Locator.XPATH,
                String.format(tubiSectionHeaderXpath, sectionName));
        waitUtils.waitForElementEnabled(WaitUtils.Locator.XPATH,
                String.format(tubiSectionHeaderXpath, sectionName));
    }

    //This has a weakness.
    //A 'scroll' sometimes ends where movie poster tiles are halfway cut off.
    //When the List of movies is created, it sometimes "double-dips" and a movie is checked twice.
    //This slows down the test and could use refactoring.
    public void scrollToMovieAndOpen(String movieName){
        List<WebElement> movies;
        String expectedName = movieName;

        //We don't want the test to run forever if the movie has been
        //removed. We cap it at a certain number of scrolls.
        int maxScrolls = 25;
        int currentScrolls = 0;

        while(currentScrolls < maxScrolls){
            movies = driver.findElements(By.xpath(tubiSectionMoviePosterXpath));
            for (int i = 1; i <= movies.size(); i++){
                interactionUtils.clickOnElement(InteractionUtils.Locator.XPATH,
                        String.format(tubiSectionSpecificMoviePosterXpath, i));

                TubiMedia tubiMedia = new TubiMedia(this.driver);
                String nameOfMovie = tubiMedia.getMediaName();

                if(nameOfMovie.equals(expectedName)){
                    return;
                } else {
                    tubiMedia.pressBackButton();
                    waitUtils.waitForElementDisplayed(WaitUtils.Locator.XPATH, tubiSectionHeaderNoNameXpath);
                }
            }
            interactionUtils.scrollForward();
            currentScrolls++;
        }
    }

}
