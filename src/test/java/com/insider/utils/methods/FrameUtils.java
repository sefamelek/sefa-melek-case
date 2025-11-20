package com.insider.utils.methods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for frame operations
 * Contains methods for switching to frames, default content, and parent frames
 */
public class FrameUtils {
    private static final Logger logger = LoggerFactory.getLogger(FrameUtils.class);

    /**
     * Switch to frame by locator with dynamic wait
     */
    public static void switchToFrameWithDynamicWait(WebDriver driver, WebDriverWait wait, By frameLocator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
        logger.debug("Switched to frame: " + frameLocator);
    }

    /**
     * Switch to frame by index with dynamic wait
     */
    public static void switchToFrameByIndexWithDynamicWait(WebDriver driver, WebDriverWait wait, int frameIndex) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
        logger.debug("Switched to frame at index: " + frameIndex);
    }

    /**
     * Switch to frame by name or id with dynamic wait
     */
    public static void switchToFrameByNameWithDynamicWait(WebDriver driver, WebDriverWait wait, String frameNameOrId) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrId));
        logger.debug("Switched to frame by name/id: " + frameNameOrId);
    }

    /**
     * Switch to default content (main page)
     */
    public static void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
        logger.debug("Switched to default content");
    }

    /**
     * Switch to parent frame
     */
    public static void switchToParentFrame(WebDriver driver) {
        driver.switchTo().parentFrame();
        logger.debug("Switched to parent frame");
    }
}

