package com.insider.tests;

import com.insider.listeners.ScreenshotListener;
import com.insider.pages.CareersPage;
import com.insider.pages.HomePage;
import com.insider.pages.JobsPage;
import com.insider.pages.QAPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.logging.Level;

@Listeners({ ScreenshotListener.class })
public class BaseTest {

    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    // Orijinal System.err'i sakla (sonsuz döngüyü önlemek için)
    private static final PrintStream ORIGINAL_ERR = System.err;
    
    static {
        // ASCII Art Banner
        printBanner();
        
        // Selenium'un java.util.logging (JUL) uyarılarını bastır
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(java.util.logging.Level.SEVERE);
        java.util.logging.Logger.getLogger("org.openqa.selenium.devtools").setLevel(java.util.logging.Level.SEVERE);
        java.util.logging.Logger.getLogger("org.openqa.selenium.devtools.CdpVersionFinder").setLevel(java.util.logging.Level.SEVERE);
        java.util.logging.Logger.getLogger("org.openqa.selenium.chromium").setLevel(java.util.logging.Level.SEVERE);
        java.util.logging.Logger.getLogger("org.openqa.selenium.chromium.ChromiumDriver").setLevel(java.util.logging.Level.SEVERE);
        
        // System.err'i filter eden PrintStream oluştur (CDP uyarılarını bastır)
        System.setErr(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // Tek byte için kontrol yapmadan geç
            }
            
            @Override
            public void write(byte[] b, int off, int len) {
                String message = new String(b, off, len);
                // Sadece CDP ile ilgili uyarıları bastır, diğer hataları göster
                if (!message.contains("Unable to find CDP") && 
                    !message.contains("Unable to find version of CDP") &&
                    !message.contains("CdpVersionFinder findNearestMatch") &&
                    !message.contains("ChromiumDriver lambda$new$5")) {
                    // Orijinal System.err'e yaz (gerçek hatalar için)
                    ORIGINAL_ERR.print(message);
                }
                // CDP uyarılarını tamamen sessizce yok say
            }
        }));
    }

    @BeforeMethod
    @Parameters({"browser", "headless"})
    public void setUp(@Optional("chrome") String browser, @Optional("false") String headless) throws java.net.MalformedURLException {
        WebDriver webDriver;
        String gridUrl = System.getProperty("selenium.grid.url");
        boolean isHeadless = Boolean.parseBoolean(headless);

        if (gridUrl != null && !gridUrl.isEmpty()) {
            // Remote Execution
            if (browser.equalsIgnoreCase("chrome")) {
                ChromeOptions options = createChromeOptions(isHeadless);
                webDriver = new org.openqa.selenium.remote.RemoteWebDriver(java.net.URI.create(gridUrl).toURL(),
                        options);
            } else if (browser.equalsIgnoreCase("firefox")) {
                FirefoxOptions options = createFirefoxOptions(isHeadless);
                webDriver = new org.openqa.selenium.remote.RemoteWebDriver(java.net.URI.create(gridUrl).toURL(),
                        options);
            } else {
                throw new IllegalArgumentException("Browser not supported for grid: " + browser);
            }
        } else {
            // Local Execution
            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = createChromeOptions(isHeadless);
                webDriver = new ChromeDriver(options);
            } else if (browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = createFirefoxOptions(isHeadless);
                webDriver = new FirefoxDriver(options);
            } else {
                throw new IllegalArgumentException("Browser not supported: " + browser);
            }
        }

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        // Headless modda viewport boyutunu açıkça ayarla
        if (isHeadless) {
            // Headless modda window.maximize() güvenilir çalışmayabilir
            // Bu yüzden viewport boyutunu açıkça ayarlıyoruz
            webDriver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
        } else {
            webDriver.manage().window().maximize();
        }
        
        driver.set(webDriver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    // ==================== PAGE FACTORY METHODS ====================

    /**
     * Ana sayfayı açar ve HomePage nesnesi döndürür
     */
    @Step("Ana sayfayı aç")
    protected HomePage openHomePage() {
        HomePage homePage = new HomePage(getDriver());
        homePage.navigateTo("https://useinsider.com/");
        return homePage;
    }

    /**
     * Kariyer sayfasını açar ve CareersPage nesnesi döndürür
     */
    @Step("Kariyer sayfasını aç")
    protected CareersPage openCareersPage() {
        return new CareersPage(getDriver());
    }

    /**
     * QA sayfasını açar ve QAPage nesnesi döndürür
     */
    @Step("QA sayfasını aç")
    protected QAPage openQAPage() {
        return new QAPage(getDriver());
    }

    /**
     * İşler sayfasını açar ve JobsPage nesnesi döndürür
     */
    @Step("İşler sayfasını aç")
    protected JobsPage openJobsPage() {
        return new JobsPage(getDriver());
    }

    // ==================== NAVIGATION METHODS ====================

    /**
     * Belirtilen URL'ye gider
     */
    @Step("'{url}' adresine git")
    protected void goToPage(String url) {
        getDriver().get(url);
    }

    /**
     * QA kariyer sayfasına gider
     */
    @Step("QA kariyer sayfasına git")
    protected void goToQACareersPage() {
        goToPage("https://useinsider.com/careers/quality-assurance/");
    }

    // ==================== HELPER METHODS ====================

    /**
     * ChromeOptions oluşturur ve CDP uyarılarını bastırır
     * @param headless true ise headless mode aktif olur
     */
    private ChromeOptions createChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        
        // Headless modu parametreye göre ayarla
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        // Chrome'un kendi log seviyesini ayarla (3 = sadece fatal hatalar)
        options.addArguments("--log-level=3");
        // Chrome'un logging output'unu devre dışı bırak
        options.addArguments("--disable-logging");
        options.addArguments("--silent");
        
        // Selenium logging preferences - CDP uyarılarını bastır
        LoggingPreferences loggingPrefs = new LoggingPreferences();
        loggingPrefs.enable(LogType.BROWSER, Level.SEVERE);
        loggingPrefs.enable(LogType.DRIVER, Level.SEVERE);
        loggingPrefs.enable(LogType.PERFORMANCE, Level.SEVERE);
        options.setCapability("goog:loggingPrefs", loggingPrefs);
        
        // CDP uyarılarını bastırmak için experimental options
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-logging"});
        options.setExperimentalOption("useAutomationExtension", false);
        
        return options;
    }

    /**
     * FirefoxOptions oluşturur
     * @param headless true ise headless mode aktif olur
     */
    private FirefoxOptions createFirefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        
        // Headless modu parametreye göre ayarla
        if (headless) {
            options.addArguments("--headless");
        }
        
        return options;
    }

    /**
     * ASCII Art Banner yazdırır (banner.txt dosyasından okur veya terminal genişliğine göre kompakt gösterir)
     */
    private static void printBanner() {
        System.out.println();
        
        // Terminal genişliğini kontrol et
        int terminalWidth = getTerminalWidth();
        int bannerWidth = 80; // banner.txt yaklaşık 80 karakter genişliğinde
        
        if (terminalWidth >= bannerWidth) {
            // Yeterli genişlik varsa banner.txt'den oku
            try (InputStream inputStream = BaseTest.class.getClassLoader().getResourceAsStream("banner.txt")) {
                if (inputStream != null) {
                    // Banner dosyasını satır satır oku ve yazdır
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                        
                        String line;
                        while ((line = reader.readLine()) != null) {
                            // Her satırı direkt yazdır - encoding korunur
                            System.out.print(line);
                            System.out.print("\n");
                        }
                    }
                } else {
                    // Banner dosyası bulunamazsa kompakt banner göster
                    printCompactBanner();
                }
            } catch (Exception e) {
                // Hata durumunda kompakt banner göster
                printCompactBanner();
            }
        } else {
            // Yeterli genişlik yoksa kompakt banner göster
            printCompactBanner();
        }
        
        System.out.println();
    }
    
    /**
     * Kompakt banner'ı dosyadan okuyup yazdırır
     */
    private static void printCompactBanner() {
        try (InputStream inputStream = BaseTest.class.getClassLoader().getResourceAsStream("compact-banner.txt")) {
            if (inputStream != null) {
                // Kompakt banner dosyasını satır satır oku ve yazdır
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                    
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Her satırı direkt yazdır - encoding korunur
                        System.out.print(line);
                        System.out.print("\n");
                    }
                }
            } else {
                // Kompakt banner dosyası bulunamazsa basit banner göster
                System.out.println("╔═══════════════════════════════════════════════════════╗");
                System.out.println("║        INSIDER AUTOMATION BY SEFA MELEK                  ║");
                System.out.println("╚═══════════════════════════════════════════════════════╝");
            }
        } catch (Exception e) {
            // Hata durumunda basit banner göster
            System.out.println("INSIDER AUTOMATION BY SEFA MELEK");
        }
    }
    
    /**
     * Terminal genişliğini döndürür (yaklaşık)
     */
    private static int getTerminalWidth() {
        try {
            // Java'da terminal genişliğini almak için system property'leri kullan
            String columns = System.getenv("COLUMNS");
            if (columns != null && !columns.isEmpty()) {
                try {
                    return Integer.parseInt(columns.trim());
                } catch (NumberFormatException e) {
                    // Parse edilemezse devam et
                }
            }
            
            // Unix/Linux/Mac için tput kullan
            if (!System.getProperty("os.name").toLowerCase().contains("win")) {
                try (java.util.Scanner s = new java.util.Scanner(
                        new ProcessBuilder("tput", "cols").start().getInputStream())) {
                    if (s.hasNextInt()) {
                        return s.nextInt();
                    }
                } catch (Exception e) {
                    // Hata durumunda varsayılan genişlik
                }
            }
            
            // Windows veya diğer durumlar için varsayılan genişlik
            return 120;
        } catch (Exception e) {
            // Hata durumunda varsayılan genişlik
            return 120;
        }
    }
}
