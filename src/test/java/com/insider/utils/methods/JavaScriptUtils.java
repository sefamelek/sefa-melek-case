package com.insider.utils.methods;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for JavaScript executor operations
 * Contains methods for executing JavaScript, scrolling, highlighting elements
 */
public class JavaScriptUtils {
    private static final Logger logger = LoggerFactory.getLogger(JavaScriptUtils.class);

    /**
     * Execute JavaScript and return result
     */
    public static Object executeScript(WebDriver driver, String script, Object... args) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object result = js.executeScript(script, args);
        logger.debug("JavaScript executed: " + script);
        return result;
    }

    /**
     * Execute JavaScript without return value
     */
    public static void executeScriptVoid(WebDriver driver, String script, Object... args) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script, args);
        logger.debug("JavaScript executed (void): " + script);
    }

    /**
     * Scroll element into view
     */
    public static void scrollIntoView(WebDriver driver, WebElement element) {
        executeScriptVoid(driver, "arguments[0].scrollIntoView(true);", element);
        logger.debug("Scrolled element into view");
    }

    /**
     * Scroll element into view with smooth behavior
     */
    public static void scrollIntoViewSmooth(WebDriver driver, WebElement element) {
        executeScriptVoid(driver, "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        logger.debug("Scrolled element into view (smooth)");
    }

    /**
     * Scroll to top of page
     */
    public static void scrollToTop(WebDriver driver) {
        executeScriptVoid(driver, "window.scrollTo(0, 0);");
        logger.debug("Scrolled to top of page");
    }

    /**
     * Scroll to bottom of page
     */
    public static void scrollToBottom(WebDriver driver) {
        executeScriptVoid(driver, "window.scrollTo(0, document.body.scrollHeight);");
        logger.debug("Scrolled to bottom of page");
    }

    /**
     * Scroll by pixels
     */
    public static void scrollBy(WebDriver driver, int x, int y) {
        executeScriptVoid(driver, "window.scrollBy(" + x + ", " + y + ");");
        logger.debug("Scrolled by (" + x + ", " + y + ")");
    }

    /**
     * Highlight element with border
     */
    public static void highlightElement(WebDriver driver, WebElement element) {
        String originalStyle = element.getAttribute("style");
        executeScriptVoid(driver, 
            "arguments[0].setAttribute('style', arguments[1]);", 
            element, 
            "border: 3px solid red; background-color: yellow;");
        logger.debug("Element highlighted");
        
        // Restore original style after 2 seconds
        try {
            Thread.sleep(2000);
            executeScriptVoid(driver, 
                "arguments[0].setAttribute('style', arguments[1]);", 
                element, 
                originalStyle != null ? originalStyle : "");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Remove element from DOM (make invisible)
     */
    public static void hideElement(WebDriver driver, WebElement element) {
        executeScriptVoid(driver, "arguments[0].style.display = 'none';", element);
        logger.debug("Element hidden");
    }

    /**
     * Show hidden element
     */
    public static void showElement(WebDriver driver, WebElement element) {
        executeScriptVoid(driver, "arguments[0].style.display = 'block';", element);
        logger.debug("Element shown");
    }

    /**
     * Get page title using JavaScript
     */
    public static String getPageTitle(WebDriver driver) {
        return (String) executeScript(driver, "return document.title;");
    }

    /**
     * Get page URL using JavaScript
     */
    public static String getPageUrl(WebDriver driver) {
        return (String) executeScript(driver, "return window.location.href;");
    }

    /**
     * Get page source length
     */
    public static Long getPageSourceLength(WebDriver driver) {
        return (Long) executeScript(driver, "return document.documentElement.outerHTML.length;");
    }

    /**
     * Check if page is ready (complete)
     */
    public static boolean isPageReady(WebDriver driver) {
        return (Boolean) executeScript(driver, "return document.readyState === 'complete';");
    }

    /**
     * Wait for page to load using JavaScript
     */
    public static void waitForPageLoad(WebDriver driver, int maxWaitSeconds) {
        long endTime = System.currentTimeMillis() + (maxWaitSeconds * 1000L);
        while (System.currentTimeMillis() < endTime) {
            if (isPageReady(driver)) {
                return;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    /**
     * Click element using JavaScript
     */
    public static void clickElement(WebDriver driver, WebElement element) {
        executeScriptVoid(driver, "arguments[0].click();", element);
        logger.debug("Element clicked via JavaScript");
    }

    /**
     * Set value of element using JavaScript
     */
    public static void setValue(WebDriver driver, WebElement element, String value) {
        executeScriptVoid(driver, "arguments[0].value = arguments[1];", element, value);
        logger.debug("Value set via JavaScript");
    }

    /**
     * Get value of element using JavaScript
     */
    public static String getValue(WebDriver driver, WebElement element) {
        return (String) executeScript(driver, "return arguments[0].value;", element);
    }

    /**
     * Check if element is fully visible in viewport
     */
    public static boolean isElementFullyVisibleInViewport(WebDriver driver, WebElement element) {
        String script = 
            "var rect = arguments[0].getBoundingClientRect();" +
            "var windowHeight = window.innerHeight || document.documentElement.clientHeight;" +
            "var windowWidth = window.innerWidth || document.documentElement.clientWidth;" +
            "return (rect.top >= 0 && rect.left >= 0 && " +
            "rect.bottom <= windowHeight && rect.right <= windowWidth && " +
            "rect.width > 0 && rect.height > 0);";
        
        Boolean result = (Boolean) executeScript(driver, script, element);
        logger.debug("Element fully visible in viewport: " + result);
        return result != null && result;
    }

    /**
     * Wait for element to be fully visible in viewport
     */
    public static void waitForElementFullyVisible(WebDriver driver, WebElement element, int maxWaitSeconds) {
        long endTime = System.currentTimeMillis() + (maxWaitSeconds * 1000L);
        while (System.currentTimeMillis() < endTime) {
            if (isElementFullyVisibleInViewport(driver, element)) {
                logger.debug("Element is fully visible in viewport");
                return;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        logger.warn("Element did not become fully visible within " + maxWaitSeconds + " seconds");
    }

    /**
     * Wait for scroll animation to complete by checking if element position is stable
     * Checks element's bounding rect position multiple times to ensure scroll has finished
     * Headless modda daha agresif kontrol yapar
     */
    public static void waitForScrollToComplete(WebDriver driver, WebElement element, int maxWaitSeconds) {
        long endTime = System.currentTimeMillis() + (maxWaitSeconds * 1000L);
        String lastPosition = null;
        int stableCount = 0;
        final int REQUIRED_STABLE_CHECKS = 5; // Element pozisyonu 5 kez aynı kalmalı (headless için artırıldı)
        boolean wasFullyVisible = false;
        int fullyVisibleCount = 0;
        final int REQUIRED_VISIBLE_CHECKS = 3; // Element 3 kez tamamen görünür olmalı
        
        while (System.currentTimeMillis() < endTime) {
            // Elementin pozisyonunu al
            String script = 
                "var rect = arguments[0].getBoundingClientRect();" +
                "var windowHeight = window.innerHeight || document.documentElement.clientHeight;" +
                "var windowWidth = window.innerWidth || document.documentElement.clientWidth;" +
                "var isFullyVisible = (rect.top >= 0 && rect.left >= 0 && " +
                "rect.bottom <= windowHeight && rect.right <= windowWidth && " +
                "rect.width > 0 && rect.height > 0);" +
                "return rect.top + ',' + rect.left + ',' + window.pageYOffset + ',' + window.pageXOffset + ',' + isFullyVisible;";
            
            String result = (String) executeScript(driver, script, element);
            
            if (result != null) {
                String[] parts = result.split(",");
                String currentPosition = parts[0] + "," + parts[1] + "," + parts[2] + "," + parts[3];
                boolean isFullyVisible = Boolean.parseBoolean(parts[4]);
                
                // Pozisyon stabilitesi kontrolü
                if (currentPosition.equals(lastPosition)) {
                    stableCount++;
                } else {
                    stableCount = 0; // Pozisyon değişti, sıfırla
                }
                
                // Element tamamen görünür mü kontrol et
                if (isFullyVisible) {
                    fullyVisibleCount++;
                    wasFullyVisible = true;
                } else {
                    fullyVisibleCount = 0; // Görünür değilse sıfırla
                }
                
                // Hem pozisyon stabil hem de element tamamen görünür olmalı
                if (stableCount >= REQUIRED_STABLE_CHECKS && fullyVisibleCount >= REQUIRED_VISIBLE_CHECKS) {
                    logger.debug("Scroll completed - element position is stable and fully visible");
                    return;
                }
                
                lastPosition = currentPosition;
            }
            
            try {
                Thread.sleep(150); // Headless modda biraz daha uzun bekle
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        
        // Timeout oldu ama element hiç görünür olmadıysa uyar
        if (!wasFullyVisible) {
            logger.warn("Scroll did not complete - element was never fully visible within " + maxWaitSeconds + " seconds");
        } else {
            logger.warn("Scroll did not complete - position not stable within " + maxWaitSeconds + " seconds");
        }
    }
}

