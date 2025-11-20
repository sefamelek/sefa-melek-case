package com.insider.locators;

import org.openqa.selenium.By;

public interface QAPageLocators {
    // Priority 4: CSS Selector (href attribute contains open-positions and qualityassurance)
    By SEE_ALL_QA_JOBS_BUTTON = By.cssSelector("a.btn[href*='open-positions'][href*='qualityassurance']");
}
