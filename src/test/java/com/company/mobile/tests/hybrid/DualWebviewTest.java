package com.company.mobile.tests.hybrid;

import com.company.mobile.base.BaseTest;
import com.company.mobile.pages.DualWebviewScreen;
import com.company.mobile.pages.HomeScreen;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Epic("Hybrid App Automation")
@Feature("Context Switching & WebView")
public class DualWebviewTest extends BaseTest {

    @Test(description = "Switch from native to webview mode and verify HTML elements")
    @Story("Dual Webview Context Switch")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that the driver can switch between NATIVE_APP and WEBVIEW contexts when embedded webview is opened.")
    public void testDualWebviewContextSwitching() {
        HomeScreen homeScreen = new HomeScreen();
        DualWebviewScreen webviewScreen = homeScreen.navigateToDualWebview();

        // 1. Verify initial context is NATIVE_APP
        Assertions.assertThat(webviewScreen.getCurrentContextName())
                .as("Initial context should be NATIVE_APP.")
                .isEqualTo("NATIVE_APP");

        // 2. Switch to WEBVIEW context
        webviewScreen.switchToWebViewContext();

        // 3. Verify active context is now WEBVIEW
        Assertions.assertThat(webviewScreen.getCurrentContextName())
                .as("Active context should successfully switch to WEBVIEW.")
                .containsIgnoringCase("WEBVIEW");

        // 4. Verify web page loaded and inspect DOM content
        String webContent = webviewScreen.getWebPageBodyText();
        Assertions.assertThat(webContent)
                .as("WebView body content should not be empty.")
                .isNotEmpty();

        // 5. Switch back to NATIVE_APP context
        webviewScreen.switchToNativeContext();
        Assertions.assertThat(webviewScreen.getCurrentContextName())
                .as("Context should return to NATIVE_APP after test completion.")
                .isEqualTo("NATIVE_APP");
    }
}
