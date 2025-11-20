package com.insider.pages;

import com.insider.locators.JobsPageLocators;
import com.insider.utils.WebDriverUtils;
import com.insider.utils.methods.AssertionUtils;
import com.insider.utils.methods.ClickUtils;
import com.insider.utils.methods.DropdownUtils;
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
        DropdownUtils.openDropdownWithDynamicWait(driver, filterWait, FILTER_DEPARTMENT_CONTAINER);
        DropdownUtils.selectDropdownOptionByTextWithDynamicWait(driver, filterWait, FILTER_LOCATION_DROPDOWN_RESULTS, department);
        
        // Department bilgisi geldikten sonra iş listesinde seçilen department'a ait işlerin listelendiğini bekle
       //waitForDepartmentJobsToBeListed(department);
        
         
        
        // Open dropdown using utility method (özel timeout ile)
        DropdownUtils.openDropdownWithDynamicWait(driver, filterWait, FILTER_LOCATION_CONTAINER);
        // Select dropdown option by text using utility method (özel timeout ile)
        DropdownUtils.selectDropdownOptionByTextWithDynamicWait(driver, filterWait, FILTER_LOCATION_DROPDOWN_RESULTS, location);

    }

    public boolean isJobListPresent() {
        WaitUtils.waitForVisibilityWithDynamicWait(filterWait, JOB_LIST);
        scrollToElement(JOB_LIST);
        return isDisplayed(JOB_LIST);
    }



    /**
     * İş listesinin görünür olduğunu doğrula ve filtrelerin doğru çalıştığını kontrol et
     */
    @Step("İş listesinin görünür olduğunu doğrula - Lokasyon: '{location}', Departman: '{department}'")
    public void verifyJobListIsDisplayed(String location, String department) {
        new JobValidator().verifyWithFilters(location, department);
        attachScreenshot("İş Listesi Doğrulama - " + location + " - " + department);
    }
    
    /**
     * İş listesinin görünür olduğunu doğrula (filtre kontrolü olmadan - geriye dönük uyumluluk için)
     */
    @Step("İş listesinin görünür olduğunu doğrula")
    public void verifyJobListIsDisplayed() {
        new JobValidator().verifyVisible();
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
        new JobValidator().verifyAllMatchFilters(location, department);
        attachScreenshot("İş İlanları Filtre Doğrulama - " + location + " - " + department);
    }

    // ==================== INNER CLASS: JobValidator ====================
    
    /**
     * İş listesi doğrulama işlemlerini yöneten inner class
     * Validation mantığını JobsPage'den ayırarak daha modüler bir yapı sağlar
     */
    private class JobValidator {
        
        /**
         * İş listesinin görünür olduğunu doğrula (basit kontrol)
         */
        void verifyVisible() {
            WaitUtils.waitForClickableWithDynamicWait(filterWait, JOB_LIST);
            AssertionUtils.assertElementDisplayedWithDynamicWait(
                    driver, wait, JOB_LIST, 
                    "İş listesi görünür değil");
        }
        
        /**
         * İş listesinin görünür olduğunu ve filtrelerle eşleştiğini doğrula
         */
        void verifyWithFilters(String location, String department) {
            verifyVisible();
            verifyNotEmpty();
            verifyAtLeastOneMatches(location, department);
        }
        
        /**
         * Tüm işlerin filtrelerle eşleştiğini doğrula
         */
        void verifyAllMatchFilters(String location, String department) {
            List<WebElement> jobs = WaitUtils.waitForAllElementsPresentWithDynamicWait(wait, JOB_ITEM);
            for (WebElement job : jobs) {
                JobInfo jobInfo = extractJobInfo(job);
                if (!matchesFilters(jobInfo, location, department)) {
                    Assert.fail("Beklenen değer bulunamadı. Beklenen: Lokasyon='" + location + 
                            "', Departman='" + department + "', Pozisyon='Quality Assurance'");
                }
            }
        }
        
        /**
         * İş listesinin boş olmadığını doğrula
         */
        private void verifyNotEmpty() {
            List<WebElement> jobs = WaitUtils.waitForAllElementsPresentWithDynamicWait(wait, JOB_ITEM);
            Assert.assertTrue(jobs.size() > 0, 
                "Filtreleme sonrası hiç iş bulunamadı. Filtreler doğru çalışmıyor olabilir.");
        }
        
        /**
         * En az bir işin filtrelerle eşleştiğini doğrula
         */
        private void verifyAtLeastOneMatches(String location, String department) {
            List<WebElement> jobs = WaitUtils.waitForAllElementsPresentWithDynamicWait(wait, JOB_ITEM);
            boolean found = jobs.stream()
                    .map(this::extractJobInfo)
                    .anyMatch(jobInfo -> matchesFilters(jobInfo, location, department));
            
            Assert.assertTrue(found, 
                "Listede belirtilen filtrelerle eşleşen hiçbir iş bulunamadı. " +
                "Beklenen: Lokasyon='" + location + "', Departman='" + department + "', Pozisyon='Quality Assurance'");
        }
        
        /**
         * İş öğesinden bilgileri çıkarır
         */
        private JobInfo extractJobInfo(WebElement job) {
            WebDriverUtils.scrollToWebElement(driver, job);
            String title = WebDriverUtils.getTextFromChildElement(job, POSITION_TITLE);
            String dept = WebDriverUtils.getTextFromChildElement(job, POSITION_DEPARTMENT);
            String loc = WebDriverUtils.getTextFromChildElement(job, POSITION_LOCATION);
            return new JobInfo(title, dept, loc);
        }
        
        /**
         * İş bilgilerinin filtrelerle eşleşip eşleşmediğini kontrol eder
         */
        private boolean matchesFilters(JobInfo jobInfo, String expectedLocation, String expectedDepartment) {
            return jobInfo.title.contains("Quality Assurance")
                    && jobInfo.department.contains(expectedDepartment)
                    && jobInfo.location.contains(expectedLocation);
        }
    }
    
    /**
     * İş bilgilerini tutan veri sınıfı
     */
    private static class JobInfo {
        final String title;
        final String department;
        final String location;
        
        JobInfo(String title, String department, String location) {
            this.title = title;
            this.department = department;
            this.location = location;
        }
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
