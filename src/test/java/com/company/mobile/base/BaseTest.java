package com.company.mobile.base;

import com.company.mobile.config.ConfigurationManager;
import com.company.mobile.drivers.DriverFactory;
import com.company.mobile.drivers.DriverManager;
import com.company.mobile.enums.PlatformType;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    protected final Logger log = LogManager.getLogger(this.getClass());

    @BeforeMethod
    @Parameters({"platform"})
    public void setUp(@Optional String platform) {
        String sysProp = System.getProperty("platform");
        String targetPlatform = (sysProp != null && !sysProp.trim().isEmpty())
                ? sysProp
                : ((platform != null && !platform.trim().isEmpty())
                    ? platform
                    : ConfigurationManager.getConfiguration().platformName());

        PlatformType platformType = PlatformType.valueOf(targetPlatform.toUpperCase());
        log.info("Test başlatılıyor. Seçilen Platform: {}", platformType);

        AppiumDriver driver = DriverFactory.createDriverInstance(platformType);
        DriverManager.setDriver(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (DriverManager.getDriver() != null) {
            log.info("Test oturumu sonlandırılıyor ve driver kapatılıyor.");
            try {
                DriverManager.getDriver().quit();
            } catch (Exception e) {
                log.warn("Driver kapatılırken hata oluştu: {}", e.getMessage());
            } finally {
                DriverManager.unloadDriver();
            }
        }
    }
}
