package com.insider.utils.methods;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for keyboard operations
 * Contains methods for key presses, key combinations, and keyboard shortcuts
 */
public class KeyboardUtils {
    private static final Logger logger = LoggerFactory.getLogger(KeyboardUtils.class);

    /**
     * Send keys to an element with dynamic wait
     */
    public static void sendKeysWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
        logger.debug("Sent keys to element: " + locator);
    }

    /**
     * Send keys to a WebElement
     */
    public static void sendKeysToElement(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
        logger.debug("Sent keys to WebElement");
    }

    /**
     * Send special key (ENTER, TAB, ESC, etc.) to an element with dynamic wait
     */
    public static void sendSpecialKeyWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator, Keys key) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.sendKeys(key);
        logger.debug("Sent special key '" + key.name() + "' to element: " + locator);
    }

    /**
     * Send special key to a WebElement
     */
    public static void sendSpecialKeyToElement(WebElement element, Keys key) {
        element.sendKeys(key);
        logger.debug("Sent special key '" + key.name() + "' to WebElement");
    }

    /**
     * Send key combination (e.g., Ctrl+A, Ctrl+C) using Actions
     */
    public static void sendKeyCombinationWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator, Keys modifier, Keys key) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        Actions actions = new Actions(driver);
        actions.keyDown(modifier)
               .sendKeys(key)
               .keyUp(modifier)
               .perform();
        logger.debug("Sent key combination '" + modifier.name() + "+" + key.name() + "' to element: " + locator);
    }

    /**
     * Send key combination with string key using Actions
     */
    public static void sendKeyCombinationStringWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator, Keys modifier, String key) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        Actions actions = new Actions(driver);
        actions.keyDown(modifier)
               .sendKeys(key)
               .keyUp(modifier)
               .perform();
        logger.debug("Sent key combination '" + modifier.name() + "+" + key + "' to element: " + locator);
    }

    /**
     * Press Enter key on element with dynamic wait
     */
    public static void pressEnterWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        sendSpecialKeyWithDynamicWait(driver, wait, locator, Keys.ENTER);
    }

    /**
     * Press Tab key on element with dynamic wait
     */
    public static void pressTabWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        sendSpecialKeyWithDynamicWait(driver, wait, locator, Keys.TAB);
    }

    /**
     * Press Escape key on element with dynamic wait
     */
    public static void pressEscapeWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        sendSpecialKeyWithDynamicWait(driver, wait, locator, Keys.ESCAPE);
    }

    /**
     * Select all text (Ctrl+A) in element with dynamic wait
     */
    public static void selectAllWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        sendKeyCombinationStringWithDynamicWait(driver, wait, locator, Keys.COMMAND, "a");
    }

    /**
     * Copy text (Ctrl+C) from element with dynamic wait
     */
    public static void copyTextWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        sendKeyCombinationStringWithDynamicWait(driver, wait, locator, Keys.COMMAND, "c");
    }

    /**
     * Paste text (Ctrl+V) to element with dynamic wait
     */
    public static void pasteTextWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        sendKeyCombinationStringWithDynamicWait(driver, wait, locator, Keys.COMMAND, "v");
    }

    /**
     * Clear element and type text with dynamic wait
     */
    public static void clearAndTypeWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
        logger.debug("Cleared and typed text to element: " + locator);
    }
}

