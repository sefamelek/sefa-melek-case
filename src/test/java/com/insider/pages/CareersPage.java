package com.insider.pages;

import com.insider.locators.CareersPageLocators;
import com.insider.utils.methods.JavaScriptUtils;
import com.insider.utils.methods.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CareersPage extends BasePage implements CareersPageLocators {

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLocationsBlockDisplayed() {
        return isDisplayed(LOCATIONS_BLOCK);
    }

    public boolean isTeamsBlockDisplayed() {
        return isDisplayed(TEAMS_BLOCK);
    }

    public boolean isLifeAtInsiderBlockDisplayed() {
        return isDisplayed(LIFE_AT_INSIDER_BLOCK);
    }

    /**
     * Lokasyon bloğunun görünür olduğunu doğrula
     */
    @Step("Lokasyon bloğunun görünür olduğunu doğrula")
    public void verifyLocationsBlockIsDisplayed() {
        logger.info("Lokasyon bloğu kontrol ediliyor...");
        
        // Arka planda varlık kontrolü
        boolean isDisplayed = isLocationsBlockDisplayed();
        logger.info("Lokasyon bloğu görünür: " + isDisplayed);
        
        // İlgili alana scroll et
        scrollToElement(LOCATIONS_BLOCK);
        
        // Elementin görünür olmasını bekle
        WebElement element = WaitUtils.waitForVisibilityWithDynamicWait(wait, LOCATIONS_BLOCK);
        
        // Elementin tamamen ekranda görünür olduğundan emin ol
        JavaScriptUtils.waitForElementFullyVisible(driver, element, 5);
        
        // Ekran görüntüsü al
        attachScreenshot("Lokasyon Bloğu Doğrulama");
    }

    /**
     * Takımlar bloğunun görünür olduğunu doğrula
     */
    @Step("Takımlar bloğunun görünür olduğunu doğrula")
    public void verifyTeamsBlockIsDisplayed() {
        logger.info("Takımlar bloğu kontrol ediliyor...");
        
        // Arka planda varlık kontrolü
        boolean isDisplayed = isTeamsBlockDisplayed();
        logger.info("Takımlar bloğu görünür: " + isDisplayed);
        
        // İlgili alana scroll et
        scrollToElement(TEAMS_BLOCK);
        
        // Elementin görünür olmasını bekle
        WebElement element = WaitUtils.waitForVisibilityWithDynamicWait(wait, TEAMS_BLOCK);
        
        // Elementin tamamen ekranda görünür olduğundan emin ol
        JavaScriptUtils.waitForElementFullyVisible(driver, element, 5);
        
        // Ekran görüntüsü al
        attachScreenshot("Takımlar Bloğu Doğrulama");
    }

    /**
     * Life at Insider bloğunun görünür olduğunu doğrula
     */
    @Step("Life at Insider bloğunun görünür olduğunu doğrula")
    public void verifyLifeAtInsiderBlockIsDisplayed() {
        logger.info("Life at Insider bloğu kontrol ediliyor...");
        
        // Arka planda varlık kontrolü
        boolean isDisplayed = isLifeAtInsiderBlockDisplayed();
        logger.info("Life at Insider bloğu görünür: " + isDisplayed);
        
        // Elementin görünür olmasını bekle
        WebElement element = WaitUtils.waitForVisibilityWithDynamicWait(wait, LIFE_AT_INSIDER_BLOCK);
        
        // İlgili alana scroll et
        scrollToElement(LIFE_AT_INSIDER_BLOCK);
        
        // Scroll animasyonunun tamamlanmasını bekle (dinamik - headless mod için optimize edilmiş)
        // Bu metod hem pozisyon stabilitesini hem de element görünürlüğünü kontrol eder
        JavaScriptUtils.waitForScrollToComplete(driver, element, 8);

        // Elementin tamamen ekranda görünür olduğundan emin ol (ekstra kontrol)
        JavaScriptUtils.waitForElementFullyVisible(driver, element, 3);
        
        // Ekran görüntüsü al
        attachScreenshot("Life at Insider Bloğu Doğrulama");
    }

    public void goToQAJobs() {
        scrollToElement(SEE_ALL_TEAMS_BUTTON);
        clickWithJs(SEE_ALL_TEAMS_BUTTON);

        scrollToElement(QA_TEAM_LINK);
        clickWithJs(QA_TEAM_LINK);

        clickWithJs(SEE_ALL_QA_JOBS_BUTTON);
    }
}
