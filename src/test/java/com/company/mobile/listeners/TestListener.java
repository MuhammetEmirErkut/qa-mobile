package com.company.mobile.listeners;

import com.company.mobile.drivers.DriverManager;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        log.info("=== Test Suite Started: {} ===", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("=== Test Suite Completed: {} ===", context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info(">> Test Starting: {}.{} <<", result.getTestClass().getName(), result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("PASSED: {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("FAILED: {}.{} - Error: {}",
                result.getTestClass().getName(),
                result.getMethod().getMethodName(),
                result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown Error");

        if (DriverManager.getDriver() != null) {
            saveScreenshotPNG(result.getMethod().getMethodName());
            savePageSource(result.getMethod().getMethodName());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("SKIPPED: {}.{}", result.getTestClass().getName(), result.getMethod().getMethodName());
    }

    @Attachment(value = "Failure Screenshot - {0}", type = "image/png")
    public byte[] saveScreenshotPNG(String testName) {
        try {
            return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            log.error("Failed to capture screenshot: {}", e.getMessage());
            return new byte[0];
        }
    }

    @Attachment(value = "Page Source (DOM) - {0}", type = "text/plain")
    public String savePageSource(String testName) {
        try {
            return DriverManager.getDriver().getPageSource();
        } catch (Exception e) {
            return "Failed to get DOM Page Source: " + e.getMessage();
        }
    }
}
