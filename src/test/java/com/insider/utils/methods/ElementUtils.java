package com.insider.utils.methods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Utility class for element state and attribute operations
 * Contains methods for checking element states and getting element properties
 */
public class ElementUtils {
    private static final Logger logger = LoggerFactory.getLogger(ElementUtils.class);

    /**
     * Check if element is displayed with dynamic wait
     */
    public static boolean isDisplayedWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.isDisplayed();
        } catch (Exception e) {
            logger.debug("Element is not displayed: " + locator);
            return false;
        }
    }

    /**
     * Check if element is enabled with dynamic wait
     */
    public static boolean isEnabledWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return element.isEnabled();
        } catch (Exception e) {
            logger.debug("Element is not enabled: " + locator);
            return false;
        }
    }

    /**
     * Check if element is selected with dynamic wait
     */
    public static boolean isSelectedWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return element.isSelected();
        } catch (Exception e) {
            logger.debug("Element is not selected: " + locator);
            return false;
        }
    }

    /**
     * Get attribute value from element with dynamic wait
     */
    public static String getAttributeWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator, String attributeName) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        String value = element.getAttribute(attributeName);
        logger.debug("Got attribute '" + attributeName + "' = '" + value + "' from element: " + locator);
        return value;
    }

    /**
     * Get attribute value from WebElement
     */
    public static String getAttributeFromElement(WebElement element, String attributeName) {
        return element.getAttribute(attributeName);
    }

    /**
     * Get CSS property value from element with dynamic wait
     */
    public static String getCssValueWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator, String cssProperty) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        String value = element.getCssValue(cssProperty);
        logger.debug("Got CSS property '" + cssProperty + "' = '" + value + "' from element: " + locator);
        return value;
    }

    /**
     * Get CSS property value from WebElement
     */
    public static String getCssValueFromElement(WebElement element, String cssProperty) {
        return element.getCssValue(cssProperty);
    }

    /**
     * Get tag name of element with dynamic wait
     */
    public static String getTagNameWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return element.getTagName();
    }

    /**
     * Get size of element with dynamic wait
     */
    public static org.openqa.selenium.Dimension getSizeWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return element.getSize();
    }

    /**
     * Get location of element with dynamic wait
     */
    public static org.openqa.selenium.Point getLocationWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        return element.getLocation();
    }

    /**
     * Get all element count with dynamic wait
     */
    public static int getElementCountWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        List<WebElement> elements = driver.findElements(locator);
        return elements.size();
    }

    /**
     * Check if element exists (present in DOM) with dynamic wait
     */
    public static boolean isElementPresentWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get all elements with dynamic wait
     */
    public static List<WebElement> getAllElementsWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return driver.findElements(locator);
    }
}

