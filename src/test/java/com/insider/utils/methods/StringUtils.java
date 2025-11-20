package com.insider.utils.methods;

import java.util.List;

/**
 * Utility class for string operations
 * Contains reusable methods for string manipulation and formatting
 */
public class StringUtils {

    /**
     * Join list of strings with newline separator
     */
    public static String joinWithNewline(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return "";
        }
        return String.join("\n", strings);
    }

    /**
     * Join list of strings with custom separator
     */
    public static String join(List<String> strings, String separator) {
        if (strings == null || strings.isEmpty()) {
            return "";
        }
        return String.join(separator, strings);
    }

    /**
     * Create error message for validation failures
     */
    public static String createValidationErrorMessage(
            int failedCount, 
            int totalCount, 
            String expectedCriteria, 
            List<String> failedItems) {
        
        String header = "Toplam " + totalCount + " öğeden " + failedCount + " tanesi kriterlere uymuyor";
        String criteria = "Beklenen: " + expectedCriteria;
        String items = joinWithNewline(failedItems);
        
        return header + "\n" + criteria + "\n\n" + items;
    }

    /**
     * Check if string is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Check if string is not empty
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Trim string (null-safe)
     */
    public static String trim(String str) {
        return str == null ? "" : str.trim();
    }

    /**
     * Remove all whitespace from string
     */
    public static String removeWhitespace(String str) {
        return str == null ? "" : str.replaceAll("\\s+", "");
    }

    /**
     * Convert to lowercase (null-safe)
     */
    public static String toLowerCase(String str) {
        return str == null ? "" : str.toLowerCase();
    }

    /**
     * Convert to uppercase (null-safe)
     */
    public static String toUpperCase(String str) {
        return str == null ? "" : str.toUpperCase();
    }

    /**
     * Check if string contains text (case-insensitive)
     */
    public static boolean containsIgnoreCase(String str, String searchText) {
        return str != null && str.toLowerCase().contains(searchText.toLowerCase());
    }

    /**
     * Get substring between two strings
     */
    public static String getSubstringBetween(String str, String start, String end) {
        if (str == null || start == null || end == null) {
            return "";
        }
        int startIndex = str.indexOf(start);
        if (startIndex == -1) {
            return "";
        }
        startIndex += start.length();
        int endIndex = str.indexOf(end, startIndex);
        if (endIndex == -1) {
            return "";
        }
        return str.substring(startIndex, endIndex);
    }
}

