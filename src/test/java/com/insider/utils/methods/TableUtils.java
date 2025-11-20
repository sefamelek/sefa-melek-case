package com.insider.utils.methods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for table operations
 * Contains methods for reading table data, finding rows, columns
 */
public class TableUtils {
    private static final Logger logger = LoggerFactory.getLogger(TableUtils.class);

    /**
     * Get table row count with dynamic wait
     */
    public static int getRowCountWithDynamicWait(WebDriver driver, WebDriverWait wait, By tableLocator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(tableLocator));
        WebElement table = driver.findElement(tableLocator);
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        return rows.size();
    }

    /**
     * Get table column count with dynamic wait
     */
    public static int getColumnCountWithDynamicWait(WebDriver driver, WebDriverWait wait, By tableLocator, int rowIndex) {
        wait.until(ExpectedConditions.presenceOfElementLocated(tableLocator));
        WebElement table = driver.findElement(tableLocator);
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        if (rowIndex < rows.size()) {
            WebElement row = rows.get(rowIndex);
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.isEmpty()) {
                cells = row.findElements(By.tagName("th"));
            }
            return cells.size();
        }
        return 0;
    }

    /**
     * Get cell text by row and column index with dynamic wait
     */
    public static String getCellTextWithDynamicWait(WebDriver driver, WebDriverWait wait, By tableLocator, int rowIndex, int colIndex) {
        wait.until(ExpectedConditions.presenceOfElementLocated(tableLocator));
        WebElement table = driver.findElement(tableLocator);
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        
        if (rowIndex < rows.size()) {
            WebElement row = rows.get(rowIndex);
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.isEmpty()) {
                cells = row.findElements(By.tagName("th"));
            }
            if (colIndex < cells.size()) {
                return cells.get(colIndex).getText().trim();
            }
        }
        return null;
    }

    /**
     * Get all row data as list of strings with dynamic wait
     */
    public static List<String> getRowDataWithDynamicWait(WebDriver driver, WebDriverWait wait, By tableLocator, int rowIndex) {
        wait.until(ExpectedConditions.presenceOfElementLocated(tableLocator));
        WebElement table = driver.findElement(tableLocator);
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        List<String> rowData = new ArrayList<>();
        
        if (rowIndex < rows.size()) {
            WebElement row = rows.get(rowIndex);
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.isEmpty()) {
                cells = row.findElements(By.tagName("th"));
            }
            for (WebElement cell : cells) {
                rowData.add(cell.getText().trim());
            }
        }
        return rowData;
    }

    /**
     * Get all table data as 2D list with dynamic wait
     */
    public static List<List<String>> getAllTableDataWithDynamicWait(WebDriver driver, WebDriverWait wait, By tableLocator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(tableLocator));
        WebElement table = driver.findElement(tableLocator);
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        List<List<String>> tableData = new ArrayList<>();
        
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.isEmpty()) {
                cells = row.findElements(By.tagName("th"));
            }
            List<String> rowData = new ArrayList<>();
            for (WebElement cell : cells) {
                rowData.add(cell.getText().trim());
            }
            if (!rowData.isEmpty()) {
                tableData.add(rowData);
            }
        }
        return tableData;
    }

    /**
     * Find row index containing text with dynamic wait
     */
    public static int findRowIndexByTextWithDynamicWait(WebDriver driver, WebDriverWait wait, By tableLocator, String searchText) {
        wait.until(ExpectedConditions.presenceOfElementLocated(tableLocator));
        WebElement table = driver.findElement(tableLocator);
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getText().contains(searchText)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Click cell by row and column index with dynamic wait
     */
    public static void clickCellWithDynamicWait(WebDriver driver, WebDriverWait wait, By tableLocator, int rowIndex, int colIndex) {
        wait.until(ExpectedConditions.presenceOfElementLocated(tableLocator));
        WebElement table = driver.findElement(tableLocator);
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        
        if (rowIndex < rows.size()) {
            WebElement row = rows.get(rowIndex);
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.isEmpty()) {
                cells = row.findElements(By.tagName("th"));
            }
            if (colIndex < cells.size()) {
                cells.get(colIndex).click();
                logger.debug("Clicked cell at row " + rowIndex + ", col " + colIndex);
            }
        }
    }
}

