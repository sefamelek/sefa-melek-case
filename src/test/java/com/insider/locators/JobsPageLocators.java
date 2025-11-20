package com.insider.locators;

import org.openqa.selenium.By;

public interface JobsPageLocators {
    // Priority 1: ID
    By FILTER_LOCATION_CONTAINER = By.id("select2-filter-by-location-container");
    By FILTER_DEPARTMENT_CONTAINER = By.id("select2-filter-by-department-container");
    By JOB_LIST = By.id("jobs-list");
    By CLOSE_COOKIES_BUTTON = By.id("wt-cli-reject-btn");

    // Priority 4: CSS Selector (Class Name falls under this or is lower priority)
    By JOB_ITEM = By.cssSelector(".position-list-item");
    By POSITION_TITLE = By.cssSelector(".position-title");
    By POSITION_DEPARTMENT = By.cssSelector(".position-department");
    By POSITION_LOCATION = By.cssSelector(".position-location");
    By FILTER_LOCATION_DROPDOWN_RESULTS = By.cssSelector("ul.select2-results__options");

    // Priority 5: Relative XPath (for text matching)
    By VIEW_ROLE_BUTTON = By.xpath(".//a[contains(text(), 'View Role')]");

    // Priority 5: Relative XPath (for dynamic dropdown options)
    static By locationOption(String location) {
     return By.xpath(".//ul[@class='select2-results__options']//li[contains(@class, 'select2-results__option') and contains(normalize-space(.), '" + location + "')]");
    }
    
    static By dropdownOptionByText(String text) {
        return By.xpath(".//ul[@class='select2-results__options']//li[contains(@class, 'select2-results__option') and contains(normalize-space(.), '" + text + "')]");
    }

    // Priority 4: CSS Selector (for attribute matching)
    static By departmentValue(String department) {
        return By.cssSelector("span[title='" + department + "']");
    }
}
