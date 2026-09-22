package com.company.mobile.pages;

import com.company.mobile.enums.WaitStrategy;
import com.company.mobile.utils.ContextUtils;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class WebviewScreen extends BasePage {

    private final By inputUrl = AppiumBy.accessibilityId("urlInput");
    private final By btnNavigate = AppiumBy.accessibilityId("navigateBtn");

    public WebviewScreen enterUrlAndNavigate(String url) {
        sendKeys(inputUrl, url, "URL Input Field");
        click(btnNavigate, "Navigate Button");
        return this;
    }

    public WebviewScreen switchToWebViewContext() {
        ContextUtils.switchToWebView();
        return this;
    }

    public WebviewScreen switchToNativeContext() {
        ContextUtils.switchToNative();
        return this;
    }
}
