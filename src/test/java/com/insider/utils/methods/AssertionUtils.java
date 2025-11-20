package com.insider.utils.methods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

/**
 * Utility class for assertion operations
 * Contains both dynamic (WebDriverWait) and static (Thread.sleep) wait methods
 * Dynamic methods are preferred for most use cases
 */
public class AssertionUtils {
    private static final Logger logger = LoggerFactory.getLogger(AssertionUtils.class);
    private static final int DEFAULT_TIMEOUT_SECONDS = 10;

    // ==================== DYNAMIC WAIT METHODS (Preferred) ====================

    /**
     * Assert element is displayed with dynamic wait
     */
    public static void assertElementDisplayedWithDynamicWait(
            WebDriver driver, WebDriverWait wait, By locator, String message) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            Assert.assertTrue(element.isDisplayed(), message);
            logger.debug("Element assertion passed");
        } catch (Exception e) {
            logger.error("Element assertion failed: " + e.getMessage());
            Assert.fail(message + " - Element: " + locator, e);
        }
    }

    /**
     * Assert element is displayed with dynamic wait (without custom message)
     */
    public static void assertElementDisplayedWithDynamicWait(
            WebDriver driver, WebDriverWait wait, By locator) {
        assertElementDisplayedWithDynamicWait(driver, wait, locator, "Element should be displayed: " + locator);
    }

    /**
     * Assert element is not displayed with dynamic wait
     */
    public static void assertElementNotDisplayedWithDynamicWait(
            WebDriver driver, WebDriverWait wait, By locator, String message) {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
            logger.debug("Element not displayed assertion passed");
        } catch (Exception e) {
            logger.error("Element not displayed assertion failed: " + e.getMessage());
            Assert.fail(message + " - Element: " + locator, e);
        }
    }

    /**
     * Assert element is clickable with dynamic wait
     */
    public static void assertElementClickableWithDynamicWait(
            WebDriver driver, WebDriverWait wait, By locator, String message) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            logger.debug("Element clickable assertion passed");
        } catch (Exception e) {
            logger.error("Element clickable assertion failed: " + e.getMessage());
            Assert.fail(message + " - Element: " + locator, e);
        }
    }

    /**
     * Assert text in element matches expected with dynamic wait
     */
    public static void assertTextInElementWithDynamicWait(
            WebDriver driver, WebDriverWait wait, By locator, String expectedText, String message) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String actualText = element.getText();
            Assert.assertTrue(actualText.contains(expectedText), 
                    message + " - Expected: " + expectedText + ", Actual: " + actualText);
            logger.debug("Text assertion passed");
        } catch (Exception e) {
            logger.error("Text assertion failed: " + e.getMessage());
            Assert.fail(message + " - Element: " + locator, e);
        }
    }

    /**
     * Assert exact text in element matches expected with dynamic wait
     */
    public static void assertExactTextInElementWithDynamicWait(
            WebDriver driver, WebDriverWait wait, By locator, String expectedText, String message) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String actualText = element.getText().trim();
            Assert.assertEquals(actualText, expectedText, message);
            logger.debug("Exact text assertion passed");
        } catch (Exception e) {
            logger.error("Exact text assertion failed: " + e.getMessage());
            Assert.fail(message + " - Element: " + locator, e);
        }
    }

    /**
     * Assert element count matches expected with dynamic wait
     */
    public static void assertElementCountWithDynamicWait(
            WebDriver driver, WebDriverWait wait, By locator, int expectedCount, String message) {
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            List<WebElement> elements = driver.findElements(locator);
            int actualCount = elements.size();
            Assert.assertEquals(actualCount, expectedCount, 
                    message + " - Expected: " + expectedCount + ", Actual: " + actualCount);
            logger.debug("Element count assertion passed");
        } catch (Exception e) {
            logger.error("Element count assertion failed: " + e.getMessage());
            Assert.fail(message + " - Element: " + locator, e);
        }
    }

    /**
     * Assert URL contains expected text with dynamic wait
     */
    public static void assertUrlContainsWithDynamicWait(
            WebDriver driver, WebDriverWait wait, String expectedUrlPart, String message) {
        try {
            wait.until(ExpectedConditions.urlContains(expectedUrlPart));
            String actualUrl = driver.getCurrentUrl();
            Assert.assertTrue(actualUrl.contains(expectedUrlPart), 
                    message + " - Expected URL part: " + expectedUrlPart + ", Actual URL: " + actualUrl);
            logger.debug("URL assertion passed");
        } catch (Exception e) {
            logger.error("URL assertion failed: " + e.getMessage());
            Assert.fail(message, e);
        }
    }

    /**
     * Assert page title contains expected text with dynamic wait
     */
    public static void assertTitleContainsWithDynamicWait(
            WebDriver driver, WebDriverWait wait, String expectedTitlePart, String message) {
        try {
            wait.until(ExpectedConditions.titleContains(expectedTitlePart));
            String actualTitle = driver.getTitle();
            Assert.assertTrue(actualTitle.contains(expectedTitlePart), 
                    message + " - Expected title part: " + expectedTitlePart + ", Actual title: " + actualTitle);
            logger.debug("Title assertion passed");
        } catch (Exception e) {
            logger.error("Title assertion failed: " + e.getMessage());
            Assert.fail(message, e);
        }
    }

    // ==================== STATIC WAIT METHODS ====================

    /**
     * Assert element is displayed with static wait
     */
    public static void assertElementDisplayedWithStaticWait(
            WebDriver driver, By locator, long waitMilliseconds, String message) {
        try {
            Thread.sleep(waitMilliseconds);
            WebElement element = driver.findElement(locator);
            Assert.assertTrue(element.isDisplayed(), message);
            logger.debug("Element assertion passed with static wait");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted while waiting: " + e.getMessage());
            Assert.fail(message + " - Thread interrupted", e);
        } catch (Exception e) {
            logger.error("Element assertion failed: " + e.getMessage());
            Assert.fail(message + " - Element: " + locator, e);
        }
    }

    /**
     * Assert text in element matches expected with static wait
     */
    public static void assertTextInElementWithStaticWait(
            WebDriver driver, By locator, String expectedText, long waitMilliseconds, String message) {
        try {
            Thread.sleep(waitMilliseconds);
            WebElement element = driver.findElement(locator);
            String actualText = element.getText();
            Assert.assertTrue(actualText.contains(expectedText), 
                    message + " - Expected: " + expectedText + ", Actual: " + actualText);
            logger.debug("Text assertion passed with static wait");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted while waiting: " + e.getMessage());
            Assert.fail(message + " - Thread interrupted", e);
        } catch (Exception e) {
            logger.error("Text assertion failed: " + e.getMessage());
            Assert.fail(message + " - Element: " + locator, e);
        }
    }

    /**
     * Assert element count matches expected with static wait
     */
    public static void assertElementCountWithStaticWait(
            WebDriver driver, By locator, int expectedCount, long waitMilliseconds, String message) {
        try {
            Thread.sleep(waitMilliseconds);
            List<WebElement> elements = driver.findElements(locator);
            int actualCount = elements.size();
            Assert.assertEquals(actualCount, expectedCount, 
                    message + " - Expected: " + expectedCount + ", Actual: " + actualCount);
            logger.debug("Element count assertion passed with static wait");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted while waiting: " + e.getMessage());
            Assert.fail(message + " - Thread interrupted", e);
        } catch (Exception e) {
            logger.error("Element count assertion failed: " + e.getMessage());
            Assert.fail(message + " - Element: " + locator, e);
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

