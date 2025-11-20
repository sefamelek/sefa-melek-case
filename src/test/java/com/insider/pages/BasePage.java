package com.insider.pages;

import com.insider.utils.WebDriverUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Base Page class that all page objects extend
 * Delegates WebDriver operations to WebDriverUtils
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = WebDriverUtils.createWait(driver);
    }

    @Step("'{url}' adresine git")
    public void navigateTo(String url) {
        WebDriverUtils.navigateTo(driver, url);
    }

    @Step("Elemente tıkla")
    public void click(By locator) {
        WebDriverUtils.click(driver, wait, locator);
    }

    @Step("Elemente '{text}' yaz")
    public void sendKeys(By locator, String text) {
        WebDriverUtils.sendKeys(driver, wait, locator, text);
    }

    @Step("Element text'ini al")
    public String getText(By locator) {
        return WebDriverUtils.getText(driver, wait, locator);
    }

    public boolean isDisplayed(By locator) {
        return WebDriverUtils.isDisplayed(driver, wait, locator);
    }

    @Step("Elemente scroll yap")
    public void scrollToElement(By locator) {
        WebDriverUtils.scrollToElement(driver, wait, locator);
    }

    @Step("JavaScript ile elemente tıkla")
    public void clickWithJs(By locator) {
        WebDriverUtils.clickWithJs(driver, wait, locator);
    }

    @Step("Yeni sekmeye geç")
    public void switchToNewTab() {
        WebDriverUtils.switchToNewTab(driver);
    }

    /**
     * Ekran görüntüsünü Allure raporuna ekle (sadece test çalışırken)
     */
    protected void attachScreenshot(String name) {
        try {
            // Test context'inin aktif olup olmadığını kontrol et
            // getCurrentTestCaseOrStep() test çalışmıyorsa Optional.empty() döner veya exception fırlatabilir
            Optional<String> testCaseId = null;
            try {
                testCaseId = Allure.getLifecycle().getCurrentTestCaseOrStep();
            } catch (Exception e) {
                // Test context yoksa sessizce çık
                return;
            }
            
            if (testCaseId == null || !testCaseId.isPresent()) {
                // Test context yok, screenshot eklemeye çalışma
                return;
            }

            // Test context var, screenshot al ve ekle
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.getLifecycle().addAttachment(name, "image/png", "png", screenshot);
            logger.debug("Ekran görüntüsü Allure'a eklendi: " + name);
        } catch (Exception e) {
            // Herhangi bir hata durumunda sessizce devam et
            // Bu hatalar console'a yazılabilir ama log4j2.xml'de AllureLifecycle ERROR seviyesine ayarlanmış
            // Bu yüzden bu hatalar sadece log dosyasına yazılır, console'da görünmez
        }
    }
}
