package com.company.mobile.pages;

import com.company.mobile.utils.ContextUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class DualWebviewScreen extends BasePage {

    // Native Elements (visible in NATIVE_APP context)
    private final By headerTitle = AppiumBy.accessibilityId("Dual Webview");

    // Web / HTML Elements (visible only after switching to WEBVIEW context)
    private final By webHeaderH1 = By.tagName("h1");
    private final By webBody = By.tagName("body");

    public DualWebviewScreen switchToWebViewContext() {
        ContextUtils.switchToWebView();
        return this;
    }

    public DualWebviewScreen switchToNativeContext() {
        ContextUtils.switchToNative();
        return this;
    }

    public String getCurrentContextName() {
        return ContextUtils.getCurrentContext();
    }

    public String getWebPageBodyText() {
        return getText(webBody, com.company.mobile.enums.WaitStrategy.PRESENCE);
    }
}
