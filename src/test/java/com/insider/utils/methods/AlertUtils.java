package com.insider.utils.methods;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for alert operations
 * Contains methods for accepting, dismissing, and getting text from alerts
 */
public class AlertUtils {
    private static final Logger logger = LoggerFactory.getLogger(AlertUtils.class);

    /**
     * Wait for alert to be present with dynamic wait
     */
    public static Alert waitForAlertWithDynamicWait(WebDriver driver, WebDriverWait wait) {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        logger.debug("Alert is present");
        return alert;
    }

    /**
     * Accept alert with dynamic wait
     */
    public static void acceptAlertWithDynamicWait(WebDriver driver, WebDriverWait wait) {
        Alert alert = waitForAlertWithDynamicWait(driver, wait);
        alert.accept();
        logger.debug("Alert accepted");
    }

    /**
     * Dismiss alert with dynamic wait
     */
    public static void dismissAlertWithDynamicWait(WebDriver driver, WebDriverWait wait) {
        Alert alert = waitForAlertWithDynamicWait(driver, wait);
        alert.dismiss();
        logger.debug("Alert dismissed");
    }

    /**
     * Get alert text with dynamic wait
     */
    public static String getAlertTextWithDynamicWait(WebDriver driver, WebDriverWait wait) {
        Alert alert = waitForAlertWithDynamicWait(driver, wait);
        String text = alert.getText();
        logger.debug("Alert text retrieved: " + text);
        return text;
    }

    /**
     * Send text to alert with dynamic wait
     */
    public static void sendTextToAlertWithDynamicWait(WebDriver driver, WebDriverWait wait, String text) {
        Alert alert = waitForAlertWithDynamicWait(driver, wait);
        alert.sendKeys(text);
        logger.debug("Text sent to alert: " + text);
    }

    /**
     * Accept alert if present (no exception if alert not found)
     */
    public static boolean acceptAlertIfPresent(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.accept();
            logger.debug("Alert accepted");
            return true;
        } catch (Exception e) {
            logger.debug("No alert present to accept");
            return false;
        }
    }

    /**
     * Dismiss alert if present (no exception if alert not found)
     */
    public static boolean dismissAlertIfPresent(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
            logger.debug("Alert dismissed");
            return true;
        } catch (Exception e) {
            logger.debug("No alert present to dismiss");
            return false;
        }
    }
}

