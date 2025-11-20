package com.insider.pages;

import com.insider.locators.JobsPageLocators;
import com.insider.utils.WebDriverUtils;
import com.insider.utils.methods.ClickUtils;
import com.insider.utils.methods.DropdownUtils;
import com.insider.utils.methods.AssertionUtils;
import com.insider.utils.methods.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class JobsPage extends BasePage implements JobsPageLocators {

    // Özel timeout süresi için wait objesi (20 saniye)
    private static final int FILTER_TIMEOUT_SECONDS = 20;
    private WebDriverWait filterWait;

    public JobsPage(WebDriver driver) {
        super(driver);
        // filterJobs için özel timeout ile wait objesi oluştur
        this.filterWait = WebDriverUtils.createWait(driver, FILTER_TIMEOUT_SECONDS);
    }

 

    @Step("İşleri filtrele - Lokasyon: '{location}', Departman: '{department}'")
    public void filterJobs(String location, String department) {
        // Wait for the filter to be ready using WaitUtils (özel timeout ile)
        WaitUtils.waitForVisibilityWithDynamicWait(filterWait, FILTER_LOCATION_CONTAINER);
        WaitUtils.waitForClickableWithDynamicWait(filterWait, FILTER_LOCATION_CONTAINER);
        
        // Filter Location - scroll to container
        scrollToElement(FILTER_LOCATION_CONTAINER);
        
        // Filter Department - wait for department value to be visible using WaitUtils (özel timeout ile)
        By departmentValue = JobsPageLocators.departmentValue(department);
        WaitUtils.waitForVisibilityWithDynamicWait(filterWait, departmentValue);
        WaitUtils.waitForClickableWithDynamicWait(filterWait, departmentValue);
        //WaitUtils.waitWithStaticWait(2000);

        // Çerez pop-up'ını kapat
        if (isDisplayed(CLOSE_COOKIES_BUTTON)) {
            click(CLOSE_COOKIES_BUTTON);
        }      
        // Open dropdown using utility method (özel timeout ile)
        DropdownUtils.openDropdownWithDynamicWait(driver, filterWait, FILTER_LOCATION_CONTAINER);
        WaitUtils.waitWithStaticWait(1000);
                // Select dropdown option by text using utility method (özel timeout ile)
        DropdownUtils.selectDropdownOptionByTextWithDynamicWait(driver, filterWait, FILTER_LOCATION_DROPDOWN_RESULTS, location);
        //WaitUtils.waitWithStaticWait(60000);

    }

    public boolean isJobListPresent() {
        WaitUtils.waitForVisibilityWithDynamicWait(filterWait, JOB_LIST);
        scrollToElement(JOB_LIST);
        return isDisplayed(JOB_LIST);
    }

    /**
     * İş listesinin görünür olduğunu doğrula
     */
    @Step("İş listesinin görünür olduğunu doğrula")
    public void verifyJobListIsDisplayed() {
        //scrollToElement(JOB_LIST);
        WaitUtils.waitForClickableWithDynamicWait(filterWait, JOB_LIST);

        AssertionUtils.assertElementDisplayedWithDynamicWait(
                driver, wait, JOB_LIST, 
                "İş listesi görünür değil");
        attachScreenshot("İş Listesi Doğrulama");
    }

    public int getJobCount() {
        List<WebElement> jobs = WaitUtils.waitForAllElementsPresentWithDynamicWait(wait, JOB_ITEM);
        return jobs.size();
    }

    public boolean checkJobDetails(String location, String department) {
        try {
            verifyAllJobsMatchFilters(location, department);
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    /**
     * Tüm iş ilanlarının belirtilen filtre kriterlerine uygun olduğunu doğrula
     */
    @Step("Tüm iş ilanlarının filtre kriterlerine uygun olduğunu doğrula - Lokasyon: '{location}', Departman: '{department}'")
    public void verifyAllJobsMatchFilters(String location, String department) {
        List<WebElement> jobs = WaitUtils.waitForAllElementsPresentWithDynamicWait(wait, JOB_ITEM);

        for (WebElement job : jobs) {
            WebDriverUtils.scrollToWebElement(driver, job);

            String title = WebDriverUtils.getTextFromChildElement(job, POSITION_TITLE);
            String dept = WebDriverUtils.getTextFromChildElement(job, POSITION_DEPARTMENT);
            String loc = WebDriverUtils.getTextFromChildElement(job, POSITION_LOCATION);

            if (!isJobMatching(title, dept, loc, location, department)) {
                Assert.fail("Beklenen değer bulunamadı. Beklenen: Lokasyon='" + location + "', Departman='" + department + "', Pozisyon='Quality Assurance'");
            }
        }

        attachScreenshot("İş İlanları Filtre Doğrulama - " + location + " - " + department);
    }

    private boolean isJobMatching(String title, String dept, String loc, String expectedLocation, String expectedDepartment) {
        boolean positionMatches = title.contains("Quality Assurance");
        boolean departmentMatches = dept.contains(expectedDepartment);
        boolean locationMatches = loc.contains(expectedLocation);
        return positionMatches && departmentMatches && locationMatches;
    }

    @Step("View Role butonuna tıkla (Index: {index})")
    public void clickViewRole(int index) {
        // Use click utility method with dynamic wait
        ClickUtils.clickByIndexWithJsDynamicWait(driver, wait, VIEW_ROLE_BUTTON, index);
    }

    /**
     * URL'in belirtilen metni içerdiğini doğrula
     */
    @Step("URL'in '{expectedUrlPart}' içerdiğini doğrula")
    public void verifyUrlContains(String expectedUrlPart) {
        AssertionUtils.assertUrlContainsWithDynamicWait(
                driver, wait, expectedUrlPart, 
                "URL '" + expectedUrlPart + "' içermiyor");
        attachScreenshot("URL Doğrulama - " + expectedUrlPart);
    }
}
