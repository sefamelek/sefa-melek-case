package com.insider.utils.methods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Utility class for wait operations
 * Contains both dynamic (WebDriverWait) and static (Thread.sleep) wait methods
 * Dynamic methods are preferred for most use cases
 */
public class WaitUtils {
    private static final Logger logger = LoggerFactory.getLogger(WaitUtils.class);
    private static final int DEFAULT_TIMEOUT_SECONDS = 10;

    // ==================== DYNAMIC WAIT METHODS (Preferred) ====================

    /**
     * Wait for element to be visible with dynamic wait
     * Returns the WebElement when visible
     */
    public static WebElement waitForVisibilityWithDynamicWait(WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        logger.debug("Element is visible: " + locator);
        return element;
    }

    /**
     * Wait for element to be visible with dynamic wait
     * Returns the WebElement when visible
     */
    public static WebElement waitForVisibilityWithDynamicWait(WebDriverWait wait, WebElement element) {
        WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
        logger.debug("Element is visible");
        return visibleElement;
    }

    /**
     * Wait for element to be clickable with dynamic wait
     * Returns the WebElement when clickable
     */
    public static WebElement waitForClickableWithDynamicWait(WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        logger.debug("Element is clickable: " + locator);
        return element;
    }

    /**
     * Wait for element to be clickable with dynamic wait
     * Returns the WebElement when clickable
     */
    public static WebElement waitForClickableWithDynamicWait(WebDriverWait wait, WebElement element) {
        WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(element));
        logger.debug("Element is clickable");
        return clickableElement;
    }

    /**
     * Wait for element to be present in DOM with dynamic wait
     * Returns the WebElement when present
     */
    public static WebElement waitForPresenceWithDynamicWait(WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        logger.debug("Element is present: " + locator);
        return element;
    }

    /**
     * Wait for all elements to be present in DOM with dynamic wait
     * Returns list of WebElements when all are present
     */
    public static List<WebElement> waitForAllElementsPresentWithDynamicWait(WebDriverWait wait, By locator) {
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        logger.debug("All elements are present: " + locator + " (count: " + elements.size() + ")");
        return elements;
    }

    /**
     * Wait for element to be invisible with dynamic wait
     */
    public static void waitForInvisibilityWithDynamicWait(WebDriverWait wait, By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        logger.debug("Element is invisible: " + locator);
    }

    /**
     * Wait for element to be invisible with dynamic wait
     */
    public static void waitForInvisibilityWithDynamicWait(WebDriverWait wait, WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
        logger.debug("Element is invisible");
    }

    /**
     * Wait for element to be selected with dynamic wait
     */
    public static void waitForSelectedWithDynamicWait(WebDriverWait wait, WebElement element) {
        wait.until(ExpectedConditions.elementToBeSelected(element));
        logger.debug("Element is selected");
    }

    /**
     * Wait for text to be present in element with dynamic wait
     */
    public static void waitForTextToBePresentWithDynamicWait(WebDriverWait wait, By locator, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        logger.debug("Text '" + text + "' is present in element: " + locator);
    }

    /**
     * Wait for text to be present in element with dynamic wait
     */
    public static void waitForTextToBePresentWithDynamicWait(WebDriverWait wait, WebElement element, String text) {
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        logger.debug("Text '" + text + "' is present in element");
    }

    /**
     * Wait for element to have specific attribute value with dynamic wait
     */
    public static void waitForAttributeToBeWithDynamicWait(WebDriverWait wait, By locator, String attribute, String value) {
        wait.until(ExpectedConditions.attributeToBe(locator, attribute, value));
        logger.debug("Attribute '" + attribute + "' has value '" + value + "' for element: " + locator);
    }

    /**
     * Wait for element to have specific attribute value with dynamic wait
     */
    public static void waitForAttributeToBeWithDynamicWait(WebDriverWait wait, WebElement element, String attribute, String value) {
        wait.until(ExpectedConditions.attributeToBe(element, attribute, value));
        logger.debug("Attribute '" + attribute + "' has value '" + value + "'");
    }

    /**
     * Wait for element count to be specific number with dynamic wait
     */
    public static void waitForElementCountToBeWithDynamicWait(WebDriverWait wait, By locator, int count) {
        wait.until(ExpectedConditions.numberOfElementsToBe(locator, count));
        logger.debug("Element count is " + count + " for locator: " + locator);
    }

    /**
     * Wait for element count to be less than specific number with dynamic wait
     */
    public static void waitForElementCountToBeLessThanWithDynamicWait(WebDriverWait wait, By locator, int count) {
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(locator, count));
        logger.debug("Element count is less than " + count + " for locator: " + locator);
    }

    /**
     * Wait for element count to be more than specific number with dynamic wait
     */
    public static void waitForElementCountToBeMoreThanWithDynamicWait(WebDriverWait wait, By locator, int count) {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, count));
        logger.debug("Element count is more than " + count + " for locator: " + locator);
    }

    /**
     * Wait for URL to contain specific text with dynamic wait
     */
    public static void waitForUrlToContainWithDynamicWait(WebDriverWait wait, String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));
        logger.debug("URL contains: " + urlPart);
    }

    /**
     * Wait for URL to be specific URL with dynamic wait
     */
    public static void waitForUrlToBeWithDynamicWait(WebDriverWait wait, String url) {
        wait.until(ExpectedConditions.urlToBe(url));
        logger.debug("URL is: " + url);
    }

    /**
     * Wait for title to contain specific text with dynamic wait
     */
    public static void waitForTitleContainsWithDynamicWait(WebDriverWait wait, String titlePart) {
        wait.until(ExpectedConditions.titleContains(titlePart));
        logger.debug("Title contains: " + titlePart);
    }

    /**
     * Wait for title to be specific title with dynamic wait
     */
    public static void waitForTitleToBeWithDynamicWait(WebDriverWait wait, String title) {
        wait.until(ExpectedConditions.titleIs(title));
        logger.debug("Title is: " + title);
    }

    /**
     * Wait for alert to be present with dynamic wait
     */
    public static void waitForAlertPresentWithDynamicWait(WebDriverWait wait) {
        wait.until(ExpectedConditions.alertIsPresent());
        logger.debug("Alert is present");
    }

    /**
     * Wait for frame to be available and switch to it with dynamic wait
     */
    public static void waitForFrameToBeAvailableWithDynamicWait(WebDriver driver, WebDriverWait wait, By frameLocator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
        logger.debug("Frame is available and switched: " + frameLocator);
    }

    /**
     * Wait for frame to be available and switch to it with dynamic wait (by index)
     */
    public static void waitForFrameToBeAvailableWithDynamicWait(WebDriver driver, WebDriverWait wait, int frameIndex) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
        logger.debug("Frame is available and switched (index: " + frameIndex + ")");
    }

    // ==================== STATIC WAIT METHODS ====================

    /**
     * Static wait using Thread.sleep
     */
    public static void waitWithStaticWait(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
            logger.debug("Static wait completed: " + milliseconds + "ms");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted during static wait: " + e.getMessage());
            throw new RuntimeException("Thread interrupted during static wait", e);
        }
    }

    /**
     * Static wait using Thread.sleep (seconds to milliseconds conversion)
     */
    public static void waitWithStaticWaitSeconds(long seconds) {
        waitWithStaticWait(seconds * 1000);
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

    /**
     * Create a WebDriverWait instance with custom timeout in milliseconds
     */
    public static WebDriverWait createWaitFromMillis(WebDriver driver, long timeoutMilliseconds) {
        return new WebDriverWait(driver, Duration.ofMillis(timeoutMilliseconds));
    }
}

