package com.insider.listeners;

import com.insider.tests.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ScreenshotListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotListener.class);
    private static final String LOG_FILE_PATH = "logs/insider-automation.log";
    private final ThreadLocal<List<String>> testLogs = new ThreadLocal<>();
    private final ThreadLocal<Long> testStartTime = new ThreadLocal<>();

    @Attachment(value = "Page Screenshot", type = "image/png")
    public byte[] saveScreenshot(WebDriver driver) {
        if (driver != null) {
            try {
                return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            } catch (Exception e) {
                logger.error("Ekran görüntüsü alınamadı: " + e.getMessage());
                return new byte[0];
            }
        }
        return new byte[0];
    }

    @Attachment(value = "Test Logs", type = "text/plain", fileExtension = ".txt")
    public String saveLogsToAllure(List<String> logs) {
        if (logs == null || logs.isEmpty()) {
            return "Log bulunamadı";
        }
        return String.join("\n", logs);
    }

    private List<String> readLogsFromFile(long startTime, long endTime) {
        List<String> relevantLogs = new ArrayList<>();
        try {
            if (Files.exists(Paths.get(LOG_FILE_PATH))) {
                List<String> allLogs = Files.readAllLines(Paths.get(LOG_FILE_PATH));
                
                // Log dosyası çok büyükse son 3000 satırı al
                int maxLines = 3000;
                int startIndex = Math.max(0, allLogs.size() - maxLines);
                List<String> recentLogs = new ArrayList<>(allLogs.subList(startIndex, allLogs.size()));
                
                // Tüm logları ekle (filtreleme yapmadan - test logları genellikle dosyanın sonunda)
                relevantLogs.addAll(recentLogs);
                
                // Eğer çok fazla log varsa son 2000 satırı al
                if (relevantLogs.size() > 2000) {
                    relevantLogs = relevantLogs.subList(relevantLogs.size() - 2000, relevantLogs.size());
                }
            } else {
                relevantLogs.add("Log dosyası bulunamadı: " + LOG_FILE_PATH);
            }
        } catch (IOException e) {
            logger.error("Log dosyası okunamadı: " + e.getMessage());
            relevantLogs.add("Log dosyası okunamadı: " + e.getMessage());
        }
        return relevantLogs;
    }

    private void attachScreenshot(WebDriver driver, String attachmentName) {
        try {
            byte[] screenshot = saveScreenshot(driver);
            if (screenshot != null && screenshot.length > 0) {
                Allure.getLifecycle().addAttachment(
                    attachmentName,
                    "image/png",
                    "png",
                    screenshot
                );
            }
        } catch (Exception e) {
            logger.error("Ekran görüntüsü Allure'a eklenemedi: " + e.getMessage());
        }
    }

    private void attachLogsToAllure(List<String> logs, String attachmentName) {
        try {
            String logsText = saveLogsToAllure(logs);
            Allure.getLifecycle().addAttachment(
                attachmentName,
                "text/plain",
                "txt",
                logsText.getBytes()
            );
        } catch (Exception e) {
            logger.error("Loglar Allure'a eklenemedi: " + e.getMessage());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String testDescription = result.getMethod().getDescription();
        String displayName = testDescription != null && !testDescription.isEmpty() 
                ? testDescription 
                : testName;
        
        // Test başlangıç zamanını kaydet
        testStartTime.set(System.currentTimeMillis());
        testLogs.set(new ArrayList<>());
        
        logger.info("═══════════════════════════════════════════════════════════");
        logger.info("▶ TEST BAŞLADI: " + displayName);
        logger.info("═══════════════════════════════════════════════════════════");
        System.out.println("▶ TEST BAŞLADI: " + displayName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String testDescription = result.getMethod().getDescription();
        String displayName = testDescription != null && !testDescription.isEmpty() 
                ? testDescription 
                : testName;
        
        long duration = result.getEndMillis() - result.getStartMillis();
        long startTime = testStartTime.get() != null ? testStartTime.get() : result.getStartMillis();
        
        logger.info("═══════════════════════════════════════════════════════════");
        logger.info("✅ TEST TAMAMLANDI: " + displayName + " (Süre: " + duration + " ms)");
        logger.info("═══════════════════════════════════════════════════════════");
        System.out.println("✅ TEST TAMAMLANDI: " + displayName + " (Süre: " + duration + " ms)");
        
        // Ekran görüntüsü al ve Allure'a ekle
        Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();
        if (driver != null) {
            attachScreenshot(driver, "Son Ekran Görüntüsü - Başarılı");
        }
        
        // Logları oku ve Allure'a ekle
        List<String> logs = readLogsFromFile(startTime, result.getEndMillis());
        if (!logs.isEmpty()) {
            attachLogsToAllure(logs, "Test Logları - " + displayName);
        }
        
        // ThreadLocal'ları temizle
        testLogs.remove();
        testStartTime.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String testDescription = result.getMethod().getDescription();
        String displayName = testDescription != null && !testDescription.isEmpty() 
                ? testDescription 
                : testName;
        
        long duration = result.getEndMillis() - result.getStartMillis();
        long startTime = testStartTime.get() != null ? testStartTime.get() : result.getStartMillis();
        
        logger.error("═══════════════════════════════════════════════════════════");
        logger.error("❌ TEST BAŞARISIZ: " + displayName + " (Süre: " + duration + " ms)");
        logger.error("Hata: " + result.getThrowable().getMessage());
        logger.error("═══════════════════════════════════════════════════════════");
        System.err.println("❌ TEST BAŞARISIZ: " + displayName + " (Süre: " + duration + " ms)");
        System.err.println("Hata: " + result.getThrowable().getMessage());
        
        // Ekran görüntüsü al ve Allure'a ekle
        Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();
        if (driver != null) {
            attachScreenshot(driver, "Son Ekran Görüntüsü - Başarısız");
        }
        
        // Logları oku ve Allure'a ekle
        List<String> logs = readLogsFromFile(startTime, result.getEndMillis());
        if (!logs.isEmpty()) {
            attachLogsToAllure(logs, "Test Logları - " + displayName);
        }
        
        // Hata detaylarını da ekle
        if (result.getThrowable() != null) {
            StringBuilder errorDetails = new StringBuilder();
            errorDetails.append("Hata Mesajı: ").append(result.getThrowable().getMessage()).append("\n\n");
            errorDetails.append("Stack Trace:\n");
            for (StackTraceElement element : result.getThrowable().getStackTrace()) {
                errorDetails.append(element.toString()).append("\n");
            }
            Allure.getLifecycle().addAttachment(
                "Hata Detayları",
                "text/plain",
                "txt",
                errorDetails.toString().getBytes()
            );
        }
        
        // ThreadLocal'ları temizle
        testLogs.remove();
        testStartTime.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String testDescription = result.getMethod().getDescription();
        String displayName = testDescription != null && !testDescription.isEmpty() 
                ? testDescription 
                : testName;
        
        logger.warn("═══════════════════════════════════════════════════════════");
        logger.warn("⏭ TEST ATLANDI: " + displayName);
        logger.warn("═══════════════════════════════════════════════════════════");
        System.out.println("⏭ TEST ATLANDI: " + displayName);
        
        // ThreadLocal'ları temizle
        testLogs.remove();
        testStartTime.remove();
    }
}

