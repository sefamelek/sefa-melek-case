package com.insider.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Set;

/**
 * Utility class for common WebDriver operations
 * Contains reusable methods for element interactions, waits, and navigation
 */
public class WebDriverUtils {
    private static final Logger logger = LoggerFactory.getLogger(WebDriverUtils.class);
    private static final int DEFAULT_TIMEOUT_SECONDS = 10;

    /**
     * Navigate to a specific URL
     */
    public static void navigateTo(WebDriver driver, String url) {
        driver.get(url);
        logger.info("Navigated to: " + url);
    }

    /**
     * Click on an element with explicit wait
     */
    public static void click(WebDriver driver, WebDriverWait wait, By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        logger.info("Clicked element: " + locator);
    }

    /**
     * Click on an element using JavaScript
     */
    public static void clickWithJs(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        logger.info("Clicked element with JS: " + locator);
    }

    /**
     * Send keys to an element with clear before typing
     */
    public static void sendKeys(WebDriver driver, WebDriverWait wait, By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
        logger.info("Sent keys '" + text + "' to element: " + locator);
    }

    /**
     * Get text from an element
     */
    public static String getText(WebDriver driver, WebDriverWait wait, By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    /**
     * Find a child element within a parent WebElement
     */
    public static WebElement findElement(WebElement parentElement, By locator) {
        return parentElement.findElement(locator);
    }

    /**
     * Get text from a WebElement (trimmed)
     */
    public static String getTextFromElement(WebElement element) {
        return element.getText().trim();
    }

    /**
     * Get text from a child element within a parent WebElement (trimmed)
     */
    public static String getTextFromChildElement(WebElement parentElement, By locator) {
        return findElement(parentElement, locator).getText().trim();
    }

    /**
     * Check if an element is displayed
     */
    public static boolean isDisplayed(WebDriver driver, WebDriverWait wait, By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            logger.warn("Element not displayed: " + locator);
            return false;
        }
    }

    /**
     * Scroll to an element with smooth behavior
     */
    public static void scrollToElement(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        // Small wait to ensure scroll animation completes
        wait.until(ExpectedConditions.visibilityOf(element));
        logger.info("Scrolled to element: " + locator);
    }

    /**
     * Scroll to a WebElement with smooth behavior
     */
    public static void scrollToWebElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Switch to a new browser tab/window
     */
    public static void switchToNewTab(WebDriver driver) {
        String currentHandle = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(currentHandle)) {
                driver.switchTo().window(handle);
                logger.info("Switched to new tab: " + handle);
                return;
            }
        }
        logger.warn("No new tab found to switch to");
    }

    /**
     * Wait for an element to be visible
     */
    public static WebElement waitForVisibility(WebDriver driver, WebDriverWait wait, By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for an element to be clickable
     */
    public static WebElement waitForClickable(WebDriver driver, WebDriverWait wait, By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for all elements to be present
     */
    public static void waitForAllElementsPresent(WebDriverWait wait, By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    /**
     * Create a WebDriverWait instance with default timeout
     */
    public static WebDriverWait createWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS));
    }

    /**
     * Create a WebDriverWait instance with custom timeout
     */
    public static WebDriverWait createWait(WebDriver driver, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }
}
