package com.company.mobile.utils;

import com.company.mobile.drivers.DriverManager;
import com.company.mobile.exceptions.FrameworkException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.SupportsContextSwitching;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public final class ContextUtils {

    private static final Logger log = LogManager.getLogger(ContextUtils.class);

    private ContextUtils() {}

    public static void switchToWebView() {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver instanceof SupportsContextSwitching contextDriver) {
            long endTime = System.currentTimeMillis() + 15000;
            while (System.currentTimeMillis() < endTime) {
                Set<?> contexts = (Set<?>) contextDriver.getContextHandles();
                log.info("Mevcut context listesi: {}", contexts);
                for (Object contextObj : contexts) {
                    String contextName = (contextObj instanceof java.util.Map<?, ?> map && map.containsKey("id"))
                            ? String.valueOf(map.get("id"))
                            : String.valueOf(contextObj);

                    if (contextName.toUpperCase().contains("WEBVIEW")) {
                        contextDriver.context(contextName);
                        log.info("WebView context'ine başarıyla geçildi: {}", contextName);
                        return;
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}
            }
            throw new FrameworkException("Zaman aşımı: Uygulama içinde herhangi bir WEBVIEW context'i bulunamadı!");
        } else {
            throw new FrameworkException("Mevcut driver context switching özelliğini desteklemiyor!");
        }
    }

    public static void switchToNative() {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver instanceof SupportsContextSwitching contextDriver) {
            contextDriver.context("NATIVE_APP");
            log.info("NATIVE_APP context'ine geri dönüldü.");
        }
    }

    public static String getCurrentContext() {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver instanceof SupportsContextSwitching contextDriver) {
            return contextDriver.getContext();
        }
        return "NATIVE_APP";
    }
}
