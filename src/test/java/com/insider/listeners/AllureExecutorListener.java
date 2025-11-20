package com.insider.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Allure Executor Listener
 * Creates executor.json file in allure-results directory with project information
 * This will display "Sefa Melek" as executor in Allure report
 */
public class AllureExecutorListener implements ISuiteListener {

    private static final String EXECUTOR_FILE = "target/allure-results/executor.json";
    
    @Override
    public void onStart(ISuite suite) {
        createExecutorJson();
    }

    @Override
    public void onFinish(ISuite suite) {
        // Executor json is created at start, no need to update at finish
    }

    /**
     * Creates executor.json file with project information
     * This file will be read by Allure to display executor information in the report
     */
    private void createExecutorJson() {
        try {
            // Create allure-results directory if it doesn't exist
            File allureResultsDir = new File("target/allure-results");
            if (!allureResultsDir.exists()) {
                allureResultsDir.mkdirs();
            }

            // Create executor.json file
            File executorFile = new File(EXECUTOR_FILE);
            executorFile.getParentFile().mkdirs();

            // Write executor information
            try (FileWriter writer = new FileWriter(executorFile)) {
                writer.write("{\n");
                writer.write("  \"name\": \"Sefa Melek\",\n");
                writer.write("  \"type\": \"maven\",\n");
                writer.write("  \"buildName\": \"Insider Automation\",\n");
                writer.write("  \"buildOrder\": 1\n");
                writer.write("}\n");
            }

        } catch (IOException e) {
            // Silent fail - if executor.json can't be created, report will still work
            System.err.println("Failed to create executor.json: " + e.getMessage());
        }
    }
}

