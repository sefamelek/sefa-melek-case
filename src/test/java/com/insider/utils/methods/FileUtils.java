package com.insider.utils.methods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Paths;

/**
 * Utility class for file upload and download operations
 * Contains methods for handling file inputs and downloads
 */
public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Upload file to file input with dynamic wait
     */
    public static void uploadFileWithDynamicWait(WebDriver driver, WebDriverWait wait, By fileInputLocator, String filePath) {
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(fileInputLocator));
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + filePath);
        }
        String absolutePath = file.getAbsolutePath();
        fileInput.sendKeys(absolutePath);
        logger.debug("File uploaded: " + absolutePath);
    }

    /**
     * Upload file to WebElement file input
     */
    public static void uploadFileToElement(WebElement fileInput, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + filePath);
        }
        fileInput.sendKeys(file.getAbsolutePath());
        logger.debug("File uploaded: " + filePath);
    }

    /**
     * Get file path relative to project root
     */
    public static String getFilePathFromResources(String fileName) {
        return Paths.get("src", "test", "resources", fileName).toAbsolutePath().toString();
    }

    /**
     * Check if file exists
     */
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * Wait for file to be downloaded (check if file exists in download directory)
     */
    public static boolean waitForFileDownload(String downloadPath, String fileName, int maxWaitSeconds) {
        File downloadFile = new File(downloadPath, fileName);
        long endTime = System.currentTimeMillis() + (maxWaitSeconds * 1000L);
        
        while (System.currentTimeMillis() < endTime) {
            if (downloadFile.exists()) {
                logger.debug("File downloaded: " + fileName);
                return true;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        logger.warn("File download timeout: " + fileName);
        return false;
    }

    /**
     * Delete file if exists
     */
    public static boolean deleteFileIfExists(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            boolean deleted = file.delete();
            logger.debug("File deleted: " + filePath + " - " + deleted);
            return deleted;
        }
        return false;
    }

    /**
     * Get file size in bytes
     */
    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.length();
        }
        return -1;
    }
}

