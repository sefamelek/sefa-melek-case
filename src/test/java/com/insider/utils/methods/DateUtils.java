package com.insider.utils.methods;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for date and time operations
 * Contains methods for formatting dates and generating date strings
 */
public class DateUtils {

    /**
     * Get current date as string in format yyyy-MM-dd
     */
    public static String getCurrentDateAsString() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * Get current date as string with custom format
     */
    public static String getCurrentDateAsString(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Get current date and time as string
     */
    public static String getCurrentDateTimeAsString() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Get current date and time as string with custom format
     */
    public static String getCurrentDateTimeAsString(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Format date to string
     */
    public static String formatDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Format date time to string
     */
    public static String formatDateTime(LocalDateTime dateTime, String pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Get date N days from today
     */
    public static LocalDate getDateDaysFromToday(int days) {
        return LocalDate.now().plusDays(days);
    }

    /**
     * Get date N days from today as string
     */
    public static String getDateDaysFromTodayAsString(int days, String pattern) {
        return getDateDaysFromToday(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Get date N months from today
     */
    public static LocalDate getDateMonthsFromToday(int months) {
        return LocalDate.now().plusMonths(months);
    }

    /**
     * Get date N years from today
     */
    public static LocalDate getDateYearsFromToday(int years) {
        return LocalDate.now().plusYears(years);
    }

    /**
     * Get timestamp for unique naming
     */
    public static String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    }

    /**
     * Get timestamp with milliseconds
     */
    public static String getTimestampWithMillis() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
    }
}

