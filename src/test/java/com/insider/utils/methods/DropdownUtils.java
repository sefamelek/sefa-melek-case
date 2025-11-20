package com.insider.utils.methods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Utility class for dropdown operations (Select2, standard select, etc.)
 * Contains both dynamic (WebDriverWait) and static (Thread.sleep) wait methods
 * Dynamic methods are preferred for most use cases
 */
public class DropdownUtils {
    private static final Logger logger = LoggerFactory.getLogger(DropdownUtils.class);
    private static final int DEFAULT_TIMEOUT_SECONDS = 10;

    // ==================== DYNAMIC WAIT METHODS (Preferred) ====================

    /**
     * Open dropdown by clicking the container with dynamic wait
     */
    public static void openDropdownWithDynamicWait(WebDriver driver, WebDriverWait wait, By dropdownContainer) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownContainer));
        wait.until(ExpectedConditions.elementToBeClickable(dropdownContainer));
        driver.findElement(dropdownContainer).click();
        logger.info("Opened dropdown container: " + dropdownContainer);
    }

    /**
     * Wait for dropdown results to be visible with dynamic wait
     */
    public static void waitForDropdownResultsWithDynamicWait(WebDriverWait wait, By dropdownResults) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownResults));
        logger.info("Dropdown results are visible");
    }

    /**
     * Select dropdown option by text with dynamic wait (for Select2 dropdowns)
     * Finds option by text content and clicks it
     */
    public static void selectDropdownOptionByTextWithDynamicWait(
            WebDriver driver, WebDriverWait wait, By dropdownResults, String optionText) {
        
        // Wait for dropdown to open
        waitForDropdownResultsWithDynamicWait(wait, dropdownResults);
        
        // Find all options
        By optionsLocator = By.xpath("//ul[@class='select2-results__options']//li[contains(@class, 'select2-results__option')]");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(optionsLocator));
        
        List<WebElement> options = driver.findElements(optionsLocator);
        WebElement targetOption = null;
        
        // Find option by text
        for (WebElement option : options) {
            String currentText = option.getText().trim();
            logger.debug("Found dropdown option: " + currentText);
            if (currentText.contains(optionText)) {
                targetOption = option;
                logger.info("Target option found: " + currentText);
                break;
            }
        }
        
        if (targetOption == null) {
            String errorMsg = "Dropdown option not found: " + optionText;
            logger.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        
        // Scroll to option and click
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", targetOption);
        wait.until(ExpectedConditions.elementToBeClickable(targetOption));
        
        // Try multiple click methods
        com.insider.utils.methods.ClickUtils.clickWithMultipleMethodsDynamicWait(driver, wait, targetOption);
        
        // Verify dropdown closed
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(dropdownResults));
            logger.info("Dropdown closed successfully, option selected: " + optionText);
        } catch (Exception e) {
            logger.warn("Dropdown did not close, but option may have been selected: " + optionText);
        }
    }

    /**
     * Select dropdown option by exact text match with dynamic wait
     */
    public static void selectDropdownOptionByExactTextWithDynamicWait(
            WebDriver driver, WebDriverWait wait, By dropdownResults, String exactText) {
        
        waitForDropdownResultsWithDynamicWait(wait, dropdownResults);
        
        By optionsLocator = By.xpath("//ul[@class='select2-results__options']//li[contains(@class, 'select2-results__option')]");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(optionsLocator));
        
        List<WebElement> options = driver.findElements(optionsLocator);
        WebElement targetOption = null;
        
        for (WebElement option : options) {
            String currentText = option.getText().trim();
            if (currentText.equals(exactText)) {
                targetOption = option;
                logger.info("Target option found (exact match): " + currentText);
                break;
            }
        }
        
        if (targetOption == null) {
            String errorMsg = "Dropdown option not found (exact match): " + exactText;
            logger.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", targetOption);
        wait.until(ExpectedConditions.elementToBeClickable(targetOption));
        com.insider.utils.methods.ClickUtils.clickWithMultipleMethodsDynamicWait(driver, wait, targetOption);
        
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(dropdownResults));
            logger.info("Dropdown closed successfully, option selected: " + exactText);
        } catch (Exception e) {
            logger.warn("Dropdown did not close, but option may have been selected: " + exactText);
        }
    }

    /**
     * Select dropdown option by index with dynamic wait
     */
    public static void selectDropdownOptionByIndexWithDynamicWait(
            WebDriver driver, WebDriverWait wait, By dropdownResults, int index) {
        
        waitForDropdownResultsWithDynamicWait(wait, dropdownResults);
        
        By optionsLocator = By.xpath("//ul[@class='select2-results__options']//li[contains(@class, 'select2-results__option')]");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(optionsLocator));
        
        List<WebElement> options = driver.findElements(optionsLocator);
        
        if (index < 0 || index >= options.size()) {
            String errorMsg = "Index " + index + " is out of bounds. Found " + options.size() + " options.";
            logger.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        
        WebElement targetOption = options.get(index);
        String optionText = targetOption.getText().trim();
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", targetOption);
        wait.until(ExpectedConditions.elementToBeClickable(targetOption));
        com.insider.utils.methods.ClickUtils.clickWithMultipleMethodsDynamicWait(driver, wait, targetOption);
        
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(dropdownResults));
            logger.info("Dropdown closed successfully, option selected at index " + index + ": " + optionText);
        } catch (Exception e) {
            logger.warn("Dropdown did not close, but option may have been selected: " + optionText);
        }
    }

    /**
     * Get all dropdown options text with dynamic wait
     */
    public static List<String> getAllDropdownOptionsWithDynamicWait(
            WebDriver driver, WebDriverWait wait, By dropdownResults) {
        
        waitForDropdownResultsWithDynamicWait(wait, dropdownResults);
        
        By optionsLocator = By.xpath("//ul[@class='select2-results__options']//li[contains(@class, 'select2-results__option')]");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(optionsLocator));
        
        List<WebElement> options = driver.findElements(optionsLocator);
        List<String> optionTexts = options.stream()
                .map(option -> option.getText().trim())
                .toList();
        
        logger.info("Found " + optionTexts.size() + " dropdown options");
        return optionTexts;
    }

    // ==================== STATIC WAIT METHODS ====================

    /**
     * Open dropdown by clicking the container with static wait
     */
    public static void openDropdownWithStaticWait(WebDriver driver, By dropdownContainer, long waitMilliseconds) {
        try {
            Thread.sleep(waitMilliseconds);
            WebElement element = driver.findElement(dropdownContainer);
            element.click();
            logger.info("Opened dropdown container with static wait (" + waitMilliseconds + "ms): " + dropdownContainer);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted while waiting: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Select dropdown option by text with static wait
     */
    public static void selectDropdownOptionByTextWithStaticWait(
            WebDriver driver, By dropdownResults, String optionText, long waitMilliseconds) {
        
        try {
            Thread.sleep(waitMilliseconds);
            
            By optionsLocator = By.xpath("//ul[@class='select2-results__options']//li[contains(@class, 'select2-results__option')]");
            List<WebElement> options = driver.findElements(optionsLocator);
            WebElement targetOption = null;
            
            for (WebElement option : options) {
                String currentText = option.getText().trim();
                if (currentText.contains(optionText)) {
                    targetOption = option;
                    logger.info("Target option found: " + currentText);
                    break;
                }
            }
            
            if (targetOption == null) {
                String errorMsg = "Dropdown option not found: " + optionText;
                logger.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }
            
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", targetOption);
            Thread.sleep(500); // Small wait after scroll
            targetOption.click();
            logger.info("Selected dropdown option with static wait (" + waitMilliseconds + "ms): " + optionText);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted while waiting: " + e.getMessage());
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

