package com.company.mobile.listeners;

import com.company.mobile.config.ConfigurationManager;
import com.company.mobile.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log = LogManager.getLogger(RetryAnalyzer.class);
    private int counter = 0;

    @Override
    public boolean retry(ITestResult result) {
        boolean retryEnabled = ConfigurationManager.getConfiguration().retryFailedTests();
        if (retryEnabled && counter < FrameworkConstants.MAX_RETRY_COUNT) {
            counter++;
            log.warn("Test failed! Retrying execution ({}/{}): {}",
                    counter, FrameworkConstants.MAX_RETRY_COUNT, result.getName());
            return true;
        }
        return false;
    }
}
