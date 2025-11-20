package com.insider.tests;

import com.insider.pages.CareersPage;
import com.insider.pages.HomePage;
import com.insider.pages.JobsPage;
import com.insider.pages.QAPage;
import org.testng.annotations.Test;

public class InsiderCareerTest extends BaseTest {

    // Test verileri - tekrar eden değerler
    private static final String TEST_LOCATION = "Istanbul, Turkiye";
    private static final String TEST_DEPARTMENT = "Quality Assurance";

    @Test(priority = 1, description = "1- Insider ana sayfasının açıldığını ve başlığın doğru olduğunu doğrula")
    public void testHomePageOpensAndDisplaysCorrectTitle() {
        // Insider ana sayfasının açıldığını ve başlığın doğru olduğunu doğrula
        HomePage homePage = openHomePage();
        homePage.acceptCookies();
        homePage.verifyTitleContains("Insider");
    }

    @Test(priority = 2, description = "2- Company → Careers menüsüne gir ve sayfa içeriklerinin görünür olduğunu doğrula")
    public void testNavigateToCareersPageAndVerifyContent() {
        // "Company" → "Careers" menüsüne gir ve sayfa içeriklerinin görünür olduğunu doğrula
        HomePage homePage = openHomePage();
        homePage.acceptCookies();
        homePage.navigateToCareersPage();

        CareersPage careersPage = openCareersPage();
        careersPage.verifyLocationsBlockIsDisplayed();
        careersPage.verifyTeamsBlockIsDisplayed();
        careersPage.verifyLifeAtInsiderBlockIsDisplayed();
    }

    @Test(priority = 3, description = "3- QA işlerini filtrele ve iş listesinin görünür olduğunu doğrula")
    public void testFilterQAJobs() {
        // QA işlerini filtrele ve iş listesinin görünür olduğunu doğrula
        goToQACareersPage();
        QAPage qaPage = openQAPage();
        qaPage.clickSeeAllQaJobs();

        // Filtreleri uygula: Location → "Istanbul, Turkey", Department → "Quality Assurance"
        JobsPage jobsPage = openJobsPage();
        jobsPage.filterJobs(TEST_LOCATION, TEST_DEPARTMENT);
        jobsPage.verifyJobListIsDisplayed(TEST_LOCATION, TEST_DEPARTMENT);
    }

    @Test(priority = 4, description = "4- Listelenen tüm iş ilanlarının filtrelere uygun olduğunu doğrula")
    public void testVerifyFilteredJobsMatchCriteria() {
        // Listelenen tüm iş ilanlarının belirtilen filtre kriterlerine uygun olduğunu doğrula
        goToQACareersPage();
        QAPage qaPage = openQAPage();
        qaPage.clickSeeAllQaJobs();

        // Filtreleri uygula
        JobsPage jobsPage = openJobsPage();
        jobsPage.filterJobs(TEST_LOCATION, TEST_DEPARTMENT);
        jobsPage.verifyJobListIsDisplayed(TEST_LOCATION, TEST_DEPARTMENT);
        jobsPage.verifyAllJobsMatchFilters(TEST_LOCATION, TEST_DEPARTMENT);
    }

    @Test(priority = 5, description = "5- View Role butonuna tıkla ve lever.co'ya yönlendirdiğini doğrula")
    public void testViewRoleButtonRedirectsToLever() {
        // "View Role" butonuna tıkla ve lever.co'ya yönlendirdiğini doğrula
        goToQACareersPage();
        QAPage qaPage = openQAPage();
        qaPage.clickSeeAllQaJobs();

        JobsPage jobsPage = openJobsPage();
        jobsPage.filterJobs(TEST_LOCATION, TEST_DEPARTMENT);
        jobsPage.verifyJobListIsDisplayed(TEST_LOCATION, TEST_DEPARTMENT);
        jobsPage.clickViewRole(0);
        jobsPage.switchToNewTab();
        jobsPage.verifyUrlContains("lever.co");
    }
}
