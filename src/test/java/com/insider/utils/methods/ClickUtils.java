package com.insider.utils.methods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Utility class for click operations
 * Contains both dynamic (WebDriverWait) and static (Thread.sleep) wait methods
 * Dynamic methods are preferred for most use cases
 */
public class ClickUtils {
    private static final Logger logger = LoggerFactory.getLogger(ClickUtils.class);
    private static final int DEFAULT_TIMEOUT_SECONDS = 10;

    // ==================== DYNAMIC WAIT METHODS (Preferred) ====================

    /**
     * Click on an element with dynamic wait (WebDriverWait)
     * Waits until element is clickable before clicking
     */
    public static void clickWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
        logger.info("Clicked element with dynamic wait: " + locator);
    }

    /**
     * Click on an element using JavaScript with dynamic wait
     * Useful for elements that are covered or not directly clickable
     */
    public static void clickWithJsDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        logger.info("Clicked element with JS and dynamic wait: " + locator);
    }

    /**
     * Click on a WebElement using JavaScript with dynamic wait
     */
    public static void clickElementWithJsDynamicWait(WebDriver driver, WebDriverWait wait, WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        logger.info("Clicked WebElement with JS and dynamic wait");
    }

    /**
     * Click using Actions class with dynamic wait
     * Moves to element and clicks (useful for hover menus)
     */
    public static void clickWithActionsDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        logger.info("Clicked element with Actions and dynamic wait: " + locator);
    }

    /**
     * Click using Actions class on WebElement with dynamic wait
     */
    public static void clickElementWithActionsDynamicWait(WebDriver driver, WebDriverWait wait, WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        logger.info("Clicked WebElement with Actions and dynamic wait");
    }

    /**
     * Try multiple click methods in sequence (normal -> Actions -> JS)
     * Useful for stubborn elements
     */
    public static void clickWithMultipleMethodsDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        clickWithMultipleMethodsDynamicWait(driver, wait, element);
    }

    /**
     * Try multiple click methods in sequence on WebElement (normal -> Actions -> JS)
     */
    public static void clickWithMultipleMethodsDynamicWait(WebDriver driver, WebDriverWait wait, WebElement element) {
        try {
            element.click();
            logger.info("Successfully clicked with normal click");
        } catch (Exception e1) {
            logger.warn("Normal click failed, trying Actions: " + e1.getMessage());
            try {
                Actions actions = new Actions(driver);
                actions.moveToElement(element).click().perform();
                logger.info("Successfully clicked with Actions");
            } catch (Exception e2) {
                logger.warn("Actions click failed, trying JavaScript: " + e2.getMessage());
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                logger.info("Successfully clicked with JavaScript");
            }
        }
    }

    /**
     * Click on element by index from a list with dynamic wait
     */
    public static void clickByIndexWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator, int index) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        java.util.List<WebElement> elements = driver.findElements(locator);
        if (index < elements.size()) {
            WebElement element = elements.get(index);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            logger.info("Clicked element at index " + index + " with dynamic wait: " + locator);
        } else {
            throw new RuntimeException("Index " + index + " is out of bounds. Found " + elements.size() + " elements.");
        }
    }

    /**
     * Click on element by index using JavaScript with dynamic wait
     */
    public static void clickByIndexWithJsDynamicWait(WebDriver driver, WebDriverWait wait, By locator, int index) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        java.util.List<WebElement> elements = driver.findElements(locator);
        if (index < elements.size()) {
            WebElement element = elements.get(index);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            logger.info("Clicked element at index " + index + " with JS and dynamic wait: " + locator);
        } else {
            throw new RuntimeException("Index " + index + " is out of bounds. Found " + elements.size() + " elements.");
        }
    }

    // ==================== STATIC WAIT METHODS ====================

    /**
     * Click on an element with static wait (Thread.sleep)
     * Waits fixed time before attempting to click
     */
    public static void clickWithStaticWait(WebDriver driver, By locator, long waitMilliseconds) {
        try {
            Thread.sleep(waitMilliseconds);
            WebElement element = driver.findElement(locator);
            element.click();
            logger.info("Clicked element with static wait (" + waitMilliseconds + "ms): " + locator);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted while waiting: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("Failed to click element with static wait: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Click on an element using JavaScript with static wait
     */
    public static void clickWithJsStaticWait(WebDriver driver, By locator, long waitMilliseconds) {
        try {
            Thread.sleep(waitMilliseconds);
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            logger.info("Clicked element with JS and static wait (" + waitMilliseconds + "ms): " + locator);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted while waiting: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("Failed to click element with JS and static wait: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Click using Actions class with static wait
     */
    public static void clickWithActionsStaticWait(WebDriver driver, By locator, long waitMilliseconds) {
        try {
            Thread.sleep(waitMilliseconds);
            WebElement element = driver.findElement(locator);
            Actions actions = new Actions(driver);
            actions.moveToElement(element).click().perform();
            logger.info("Clicked element with Actions and static wait (" + waitMilliseconds + "ms): " + locator);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted while waiting: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("Failed to click element with Actions and static wait: " + e.getMessage());
            throw new RuntimeException(e);
        }
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

