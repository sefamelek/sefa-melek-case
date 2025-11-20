package com.insider.utils.methods;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Utility class for window and tab operations
 * Contains methods for switching windows, managing tabs, and window properties
 */
public class WindowUtils {
    private static final Logger logger = LoggerFactory.getLogger(WindowUtils.class);

    /**
     * Get all window handles
     */
    public static Set<String> getAllWindowHandles(WebDriver driver) {
        Set<String> handles = driver.getWindowHandles();
        logger.debug("Found " + handles.size() + " window(s)");
        return handles;
    }

    /**
     * Get current window handle
     */
    public static String getCurrentWindowHandle(WebDriver driver) {
        return driver.getWindowHandle();
    }

    /**
     * Get total number of open windows/tabs
     */
    public static int getWindowCount(WebDriver driver) {
        return driver.getWindowHandles().size();
    }

    /**
     * Switch to window by handle
     */
    public static void switchToWindow(WebDriver driver, String windowHandle) {
        driver.switchTo().window(windowHandle);
        logger.debug("Switched to window: " + windowHandle);
    }

    /**
     * Switch to the first window/tab (by index)
     */
    public static void switchToFirstWindow(WebDriver driver) {
        Set<String> handles = driver.getWindowHandles();
        if (!handles.isEmpty()) {
            String firstHandle = handles.iterator().next();
            driver.switchTo().window(firstHandle);
            logger.debug("Switched to first window");
        }
    }

    /**
     * Switch to the last opened window/tab
     */
    public static void switchToLastWindow(WebDriver driver) {
        Set<String> handles = driver.getWindowHandles();
        String currentHandle = driver.getWindowHandle();
        String lastHandle = null;
        
        for (String handle : handles) {
            if (!handle.equals(currentHandle)) {
                lastHandle = handle;
            }
        }
        
        if (lastHandle != null) {
            driver.switchTo().window(lastHandle);
            logger.debug("Switched to last window");
        } else {
            logger.warn("No other window found to switch");
        }
    }

    /**
     * Switch to new window/tab (excludes current window)
     */
    public static void switchToNewWindow(WebDriver driver) {
        String currentHandle = driver.getWindowHandle();
        Set<String> handles = driver.getWindowHandles();
        
        for (String handle : handles) {
            if (!handle.equals(currentHandle)) {
                driver.switchTo().window(handle);
                logger.debug("Switched to new window");
                return;
            }
        }
        logger.warn("No new window found to switch");
    }

    /**
     * Switch back to the original window
     */
    public static void switchToOriginalWindow(WebDriver driver, String originalHandle) {
        driver.switchTo().window(originalHandle);
        logger.debug("Switched back to original window");
    }

    /**
     * Close current window and switch back to original window
     */
    public static void closeCurrentWindowAndSwitchTo(WebDriver driver, String targetHandle) {
        driver.close();
        driver.switchTo().window(targetHandle);
        logger.debug("Closed current window and switched to target window");
    }

    /**
     * Close all windows except the main window
     */
    public static void closeAllWindowsExceptMain(WebDriver driver, String mainWindowHandle) {
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(mainWindowHandle);
        logger.debug("Closed all windows except main window");
    }

    /**
     * Get window title
     */
    public static String getWindowTitle(WebDriver driver) {
        return driver.getTitle();
    }

    /**
     * Get current URL
     */
    public static String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    /**
     * Maximize window
     */
    public static void maximizeWindow(WebDriver driver) {
        driver.manage().window().maximize();
        logger.debug("Window maximized");
    }

    /**
     * Minimize window
     */
    public static void minimizeWindow(WebDriver driver) {
        driver.manage().window().minimize();
        logger.debug("Window minimized");
    }

    /**
     * Set window size
     */
    public static void setWindowSize(WebDriver driver, int width, int height) {
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(width, height));
        logger.debug("Window size set to: " + width + "x" + height);
    }

    /**
     * Set window position
     */
    public static void setWindowPosition(WebDriver driver, int x, int y) {
        driver.manage().window().setPosition(new org.openqa.selenium.Point(x, y));
        logger.debug("Window position set to: (" + x + ", " + y + ")");
    }
}

