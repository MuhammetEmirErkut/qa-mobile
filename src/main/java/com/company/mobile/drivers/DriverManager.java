package com.company.mobile.drivers;

import io.appium.java_client.AppiumDriver;

public final class DriverManager {

    private static final ThreadLocal<AppiumDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    private DriverManager() {}

    public static AppiumDriver getDriver() {
        return DRIVER_THREAD_LOCAL.get();
    }

    public static void setDriver(AppiumDriver driver) {
        DRIVER_THREAD_LOCAL.set(driver);
    }

    public static void unloadDriver() {
        DRIVER_THREAD_LOCAL.remove();
    }
}
