package com.insider.utils.methods;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Utility class for cookie operations
 * Contains methods for managing cookies (get, add, delete)
 */
public class CookieUtils {
    private static final Logger logger = LoggerFactory.getLogger(CookieUtils.class);

    /**
     * Get all cookies
     */
    public static Set<Cookie> getAllCookies(WebDriver driver) {
        Set<Cookie> cookies = driver.manage().getCookies();
        logger.debug("Retrieved " + cookies.size() + " cookie(s)");
        return cookies;
    }

    /**
     * Get cookie by name
     */
    public static Cookie getCookieByName(WebDriver driver, String cookieName) {
        Cookie cookie = driver.manage().getCookieNamed(cookieName);
        if (cookie != null) {
            logger.debug("Retrieved cookie: " + cookieName);
        } else {
            logger.debug("Cookie not found: " + cookieName);
        }
        return cookie;
    }

    /**
     * Get cookie value by name
     */
    public static String getCookieValue(WebDriver driver, String cookieName) {
        Cookie cookie = getCookieByName(driver, cookieName);
        return cookie != null ? cookie.getValue() : null;
    }

    /**
     * Add cookie
     */
    public static void addCookie(WebDriver driver, Cookie cookie) {
        driver.manage().addCookie(cookie);
        logger.debug("Added cookie: " + cookie.getName());
    }

    /**
     * Add cookie by name and value
     */
    public static void addCookie(WebDriver driver, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        driver.manage().addCookie(cookie);
        logger.debug("Added cookie: " + name);
    }

    /**
     * Add cookie with domain
     */
    public static void addCookie(WebDriver driver, String name, String value, String domain) {
        Cookie cookie = new Cookie.Builder(name, value)
                .domain(domain)
                .build();
        driver.manage().addCookie(cookie);
        logger.debug("Added cookie with domain: " + name);
    }

    /**
     * Delete cookie by name
     */
    public static void deleteCookie(WebDriver driver, String cookieName) {
        driver.manage().deleteCookieNamed(cookieName);
        logger.debug("Deleted cookie: " + cookieName);
    }

    /**
     * Delete cookie
     */
    public static void deleteCookie(WebDriver driver, Cookie cookie) {
        driver.manage().deleteCookie(cookie);
        logger.debug("Deleted cookie: " + cookie.getName());
    }

    /**
     * Delete all cookies
     */
    public static void deleteAllCookies(WebDriver driver) {
        driver.manage().deleteAllCookies();
        logger.debug("Deleted all cookies");
    }

    /**
     * Check if cookie exists
     */
    public static boolean cookieExists(WebDriver driver, String cookieName) {
        return getCookieByName(driver, cookieName) != null;
    }
}

