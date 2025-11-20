package com.insider.utils.methods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Utility class for standard HTML Select dropdown operations
 * Contains methods for selecting options by visible text, value, index
 */
public class SelectUtils {
    private static final Logger logger = LoggerFactory.getLogger(SelectUtils.class);

    /**
     * Select option by visible text with dynamic wait
     */
    public static void selectByVisibleTextWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator, String visibleText) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
        logger.debug("Selected option by visible text '" + visibleText + "' from: " + locator);
    }

    /**
     * Select option by value with dynamic wait
     */
    public static void selectByValueWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        select.selectByValue(value);
        logger.debug("Selected option by value '" + value + "' from: " + locator);
    }

    /**
     * Select option by index with dynamic wait
     */
    public static void selectByIndexWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator, int index) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        select.selectByIndex(index);
        logger.debug("Selected option by index " + index + " from: " + locator);
    }

    /**
     * Deselect option by visible text with dynamic wait (for multi-select)
     */
    public static void deselectByVisibleTextWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator, String visibleText) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        select.deselectByVisibleText(visibleText);
        logger.debug("Deselected option by visible text '" + visibleText + "' from: " + locator);
    }

    /**
     * Deselect option by value with dynamic wait (for multi-select)
     */
    public static void deselectByValueWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        select.deselectByValue(value);
        logger.debug("Deselected option by value '" + value + "' from: " + locator);
    }

    /**
     * Deselect option by index with dynamic wait (for multi-select)
     */
    public static void deselectByIndexWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator, int index) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        select.deselectByIndex(index);
        logger.debug("Deselected option by index " + index + " from: " + locator);
    }

    /**
     * Deselect all options with dynamic wait (for multi-select)
     */
    public static void deselectAllWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        select.deselectAll();
        logger.debug("Deselected all options from: " + locator);
    }

    /**
     * Get first selected option text with dynamic wait
     */
    public static String getFirstSelectedOptionTextWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        String text = select.getFirstSelectedOption().getText();
        logger.debug("Got first selected option text: '" + text + "' from: " + locator);
        return text;
    }

    /**
     * Get first selected option value with dynamic wait
     */
    public static String getFirstSelectedOptionValueWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        return select.getFirstSelectedOption().getAttribute("value");
    }

    /**
     * Get all selected options text with dynamic wait
     */
    public static List<String> getAllSelectedOptionsTextWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        List<WebElement> selectedOptions = select.getAllSelectedOptions();
        List<String> texts = selectedOptions.stream()
                .map(WebElement::getText)
                .toList();
        logger.debug("Got " + texts.size() + " selected option(s) from: " + locator);
        return texts;
    }

    /**
     * Get all options text with dynamic wait
     */
    public static List<String> getAllOptionsTextWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        List<WebElement> options = select.getOptions();
        List<String> texts = options.stream()
                .map(WebElement::getText)
                .toList();
        logger.debug("Got " + texts.size() + " option(s) from: " + locator);
        return texts;
    }

    /**
     * Check if select is multiple with dynamic wait
     */
    public static boolean isMultipleWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        return select.isMultiple();
    }

    /**
     * Get option count with dynamic wait
     */
    public static int getOptionCountWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Select select = new Select(element);
        return select.getOptions().size();
    }
}

