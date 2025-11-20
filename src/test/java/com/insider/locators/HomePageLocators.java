package com.insider.locators;

import org.openqa.selenium.By;

public interface HomePageLocators {
    // Priority 1: ID
    By ACCEPT_COOKIES_BUTTON = By.id("wt-cli-accept-all-btn");

    // Priority 4: CSS Selector
    By CAREERS_LINK = By.cssSelector("a.dropdown-sub[href*='careers']");

    // Priority 5: Relative XPath (for dynamic dropdown options)
    By COMPANY_MENU = By.xpath(".//a[contains(text(), 'Company')]");

}
