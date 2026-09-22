package com.company.mobile.pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class HomeScreen extends BasePage {

    private final By btnLoginScreen = AppiumBy.accessibilityId("Login Screen");
    private final By btnEchoBox = AppiumBy.accessibilityId("Echo Box");
    private final By btnDualWebview = By.xpath("//*[@content-desc='Dual Webview Demo' or @text='Dual Webview Demo' or contains(@content-desc, 'Dual Webview') or contains(@text, 'Dual Webview') or contains(@name, 'Dual Webview') or contains(@label, 'Dual Webview')]");
    private final By btnListDemo = AppiumBy.accessibilityId("List Demo");
    private final By btnClipboardDemo = AppiumBy.accessibilityId("Clipboard Demo");

    public LoginScreen navigateToLoginScreen() {
        dismissSystemAlertIfPresent();
        click(btnLoginScreen, "Login Screen Menu Item");
        return new LoginScreen();
    }

    public EchoScreen navigateToEchoScreen() {
        dismissSystemAlertIfPresent();
        click(btnEchoBox, "Echo Box Menu Item");
        return new EchoScreen();
    }

    public DualWebviewScreen navigateToDualWebview() {
        dismissSystemAlertIfPresent();
        click(btnDualWebview, "Dual Webview Menu Item");
        return new DualWebviewScreen();
    }

    public boolean isHomeScreenLoaded() {
        return isElementDisplayed(btnLoginScreen);
    }
}
