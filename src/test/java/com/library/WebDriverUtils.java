/*
@author vipin yadav
*/

package com.library;

import com.Base.TestBaseClass;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebDriverUtils extends TestBaseClass {

    public void openURL(String url) {
        driver.get(url);
    }

    public  void waitForElementClickable(WebElement locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public  void waitForElementVisibility(WebElement locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.visibilityOf(locator));
    }
    public  void hoverOnElectronicsLnk(WebElement element){
        Actions actions=new Actions(driver);
        actions.moveToElement(element).build().perform();
    }

    public  void jsClick(WebElement element) {
        getJSExecutor().executeScript("arguments[0].click()", element);
    }

    public JavascriptExecutor getJSExecutor(){
        return ((JavascriptExecutor)driver);
    }
}