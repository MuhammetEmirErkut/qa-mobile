package com.company.mobile.drivers;

import com.company.mobile.config.ConfigurationManager;
import com.company.mobile.config.FrameworkConfig;
import com.company.mobile.constants.FrameworkConstants;
import com.company.mobile.enums.PlatformType;
import com.company.mobile.exceptions.FrameworkException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;

public final class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class);

    private DriverFactory() {}

    public static AppiumDriver createDriverInstance(PlatformType platformType) {
        FrameworkConfig config = ConfigurationManager.getConfiguration();
        AppiumDriver driver;
        String serverUrl = config.appiumServerUrl();

        try {
            var appiumServerUri = URI.create(serverUrl).toURL();
            log.info("Appium Driver başlatılıyor. Hedef Platform: {}, Server: {}", platformType, serverUrl);

            switch (platformType) {
                case ANDROID -> {
                    UiAutomator2Options options = new UiAutomator2Options();
                    options.setDeviceName(config.androidDeviceName());
                    options.setUdid(config.androidUdid());
                    
                    String apkPath = FrameworkConstants.APPS_PATH + File.separator + config.androidAppFile();
                    File apkFile = new File(apkPath);
                    if (apkFile.exists()) {
                        options.setApp(apkPath);
                    }
                    options.setAppPackage(config.androidAppPackage());
                    options.setAppActivity(config.androidAppActivity());

                    options.setAutoGrantPermissions(true);
                    options.setNewCommandTimeout(FrameworkConstants.APPIUM_COMMAND_TIMEOUT);
                    options.setCapability("appium:chromedriverAutodownload", true);

                    driver = new AndroidDriver(appiumServerUri, options);
                }
                case IOS -> {
                    XCUITestOptions options = new XCUITestOptions();
                    options.setDeviceName(config.iosDeviceName());
                    options.setUdid(config.iosUdid());

                    String appPath = FrameworkConstants.APPS_PATH + File.separator + config.iosAppFile();
                    File appFile = new File(appPath);
                    if (appFile.exists()) {
                        options.setApp(appPath);
                    }
                    options.setBundleId(config.iosBundleId());

                    options.setWdaLaunchTimeout(FrameworkConstants.WDA_LAUNCH_TIMEOUT);
                    options.setNewCommandTimeout(FrameworkConstants.APPIUM_COMMAND_TIMEOUT);
                    options.setCapability("appium:autoAcceptAlerts", true);
                    options.setCapability("appium:connectHardwareKeyboard", true);
                    options.setCapability("appium:includeSafariInWebviews", true);
                    options.setCapability("appium:webviewConnectTimeout", 20000);

                    driver = new IOSDriver(appiumServerUri, options);
                }
                case MOBILE_CHROME -> {
                    UiAutomator2Options options = new UiAutomator2Options();
                    options.withBrowserName("Chrome");
                    options.setCapability("appium:chromedriverAutodownload", true);
                    driver = new AndroidDriver(appiumServerUri, options);
                }
                case MOBILE_SAFARI -> {
                    XCUITestOptions options = new XCUITestOptions();
                    options.withBrowserName("Safari");
                    driver = new IOSDriver(appiumServerUri, options);
                }
                default -> throw new FrameworkException("Desteklenmeyen platform türü: " + platformType);
            }
        } catch (MalformedURLException e) {
            throw new FrameworkException("Geçersiz Appium Server URL: " + serverUrl, e);
        }

        return driver;
    }
}
