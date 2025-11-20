package com.insider.locators;

import org.openqa.selenium.By;

public interface CareersPageLocators {
    // Priority 1: ID
    By LOCATIONS_BLOCK = By.id("career-our-location");
    By TEAMS_BLOCK = By.id("career-find-our-calling");

    
    // Priority 4: CSS Selector
   By LIFE_AT_INSIDER_BLOCK = By.cssSelector("section[data-id='a8e7b90']");
    By SEE_ALL_TEAMS_BUTTON = By.cssSelector("a.btn.loadmore");
    // Priority 5: Relative XPath (for text matching)
    By QA_TEAM_LINK = By.xpath(".//h3[contains(text(), 'Quality Assurance')]");
    By SEE_ALL_QA_JOBS_BUTTON = By.xpath(".//a[contains(text(), 'See all QA jobs')]");
    //By LIFE_AT_INSIDER_BLOCK = By.xpath("//h2[normalize-space()='Life at Insider']/ancestor::section[1]");

}
