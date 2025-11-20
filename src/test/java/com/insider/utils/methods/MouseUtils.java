package com.insider.utils.methods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for mouse operations
 * Contains methods for hover, double click, right click, drag and drop
 */
public class MouseUtils {
    private static final Logger logger = LoggerFactory.getLogger(MouseUtils.class);

    /**
     * Hover over an element with dynamic wait
     */
    public static void hoverWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        logger.debug("Hovered over element: " + locator);
    }

    /**
     * Hover over a WebElement
     */
    public static void hoverOverElement(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
        logger.debug("Hovered over WebElement");
    }

    /**
     * Double click on an element with dynamic wait
     */
    public static void doubleClickWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Actions actions = new Actions(driver);
        actions.doubleClick(element).perform();
        logger.debug("Double clicked element: " + locator);
    }

    /**
     * Double click on a WebElement
     */
    public static void doubleClickElement(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.doubleClick(element).perform();
        logger.debug("Double clicked WebElement");
    }

    /**
     * Right click (context click) on an element with dynamic wait
     */
    public static void rightClickWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Actions actions = new Actions(driver);
        actions.contextClick(element).perform();
        logger.debug("Right clicked element: " + locator);
    }

    /**
     * Right click on a WebElement
     */
    public static void rightClickElement(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.contextClick(element).perform();
        logger.debug("Right clicked WebElement");
    }

    /**
     * Drag and drop from source to target with dynamic wait
     */
    public static void dragAndDropWithDynamicWait(WebDriver driver, WebDriverWait wait, By sourceLocator, By targetLocator) {
        WebElement source = wait.until(ExpectedConditions.elementToBeClickable(sourceLocator));
        WebElement target = wait.until(ExpectedConditions.elementToBeClickable(targetLocator));
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, target).perform();
        logger.debug("Dragged element " + sourceLocator + " to " + targetLocator);
    }

    /**
     * Drag and drop WebElements
     */
    public static void dragAndDropElements(WebDriver driver, WebElement source, WebElement target) {
        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, target).perform();
        logger.debug("Dragged WebElement to another WebElement");
    }

    /**
     * Drag and drop by offset with dynamic wait
     */
    public static void dragAndDropByOffsetWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator, int xOffset, int yOffset) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(element, xOffset, yOffset).perform();
        logger.debug("Dragged element " + locator + " by offset (" + xOffset + ", " + yOffset + ")");
    }

    /**
     * Move to element and click with dynamic wait
     */
    public static void moveToElementAndClickWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        logger.debug("Moved to element and clicked: " + locator);
    }

    /**
     * Click and hold on an element with dynamic wait
     */
    public static void clickAndHoldWithDynamicWait(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Actions actions = new Actions(driver);
        actions.clickAndHold(element).perform();
        logger.debug("Clicked and held element: " + locator);
    }

    /**
     * Release mouse button
     */
    public static void releaseMouse(WebDriver driver) {
        Actions actions = new Actions(driver);
        actions.release().perform();
        logger.debug("Released mouse button");
    }
}

