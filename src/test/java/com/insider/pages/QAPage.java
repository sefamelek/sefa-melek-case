package com.insider.pages;

import com.insider.locators.QAPageLocators;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class QAPage extends BasePage implements QAPageLocators {

    public QAPage(WebDriver driver) {
        super(driver);
    }

    @Step("Tüm QA işlerini görüntüle butonuna tıkla")
    public void clickSeeAllQaJobs() {
        scrollToElement(SEE_ALL_QA_JOBS_BUTTON);
        clickWithJs(SEE_ALL_QA_JOBS_BUTTON);
    }
}
