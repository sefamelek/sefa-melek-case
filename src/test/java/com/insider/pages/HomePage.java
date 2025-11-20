package com.insider.pages;

import com.insider.locators.HomePageLocators;
import com.insider.utils.methods.AssertionUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage implements HomePageLocators {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Çerezleri kabul et")
    public void acceptCookies() {
        if (isDisplayed(ACCEPT_COOKIES_BUTTON)) {
            click(ACCEPT_COOKIES_BUTTON);
        }
    }

    @Step("Company menüsünden Careers sayfasına git")
    public void navigateToCareersPage() {
        click(COMPANY_MENU);
        click(CAREERS_LINK);
    }

    @Step("Sayfa başlığını al")
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * Sayfa başlığının belirtilen metni içerdiğini doğrula
     */
    @Step("Sayfa başlığının '{expectedText}' içerdiğini doğrula")
    public void verifyTitleContains(String expectedText) {
        logger.info("Insider ana sayfası başlığı kontrol ediliyor - Beklenen: '" + expectedText + "'");
        
        try {
            String actualTitle = driver.getTitle();
            AssertionUtils.assertTitleContainsWithDynamicWait(
                    driver, wait, expectedText, 
                    "Sayfa başlığı '" + expectedText + "' içermiyor");
            logger.info("✅ Insider ana sayfası başlığı doğrulandı - Gerçek başlık: '" + actualTitle + "'");
        } catch (AssertionError e) {
            logger.error("❌ Insider ana sayfası başlığı doğrulanamadı: " + e.getMessage());
            throw e;
        }
        
        attachScreenshot("Sayfa Başlığı Doğrulama - " + expectedText);
    }
}
