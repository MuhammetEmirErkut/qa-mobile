package com.company.mobile.pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class HomeScreen extends BasePage {

    private final By btnLoginScreen = AppiumBy.accessibilityId("Login Screen");
    private final By btnEchoBox = AppiumBy.accessibilityId("Echo Box");
    private final By btnDualWebview = By.xpath("//*[contains(@name, 'Dual Webview') or contains(@label, 'Dual Webview') or contains(@text, 'Dual Webview')]");
    private final By btnListDemo = AppiumBy.accessibilityId("List Demo");
    private final By btnClipboardDemo = AppiumBy.accessibilityId("Clipboard Demo");

    public LoginScreen navigateToLoginScreen() {
        dismissSystemAlertIfPresent();
        click(btnLoginScreen, "Login Screen Menü Öğesi");
        return new LoginScreen();
    }

    public EchoScreen navigateToEchoScreen() {
        dismissSystemAlertIfPresent();
        click(btnEchoBox, "Echo Box Menü Öğesi");
        return new EchoScreen();
    }

    public DualWebviewScreen navigateToDualWebview() {
        dismissSystemAlertIfPresent();
        click(btnDualWebview, "Dual Webview Menü Öğesi");
        return new DualWebviewScreen();
    }

    public boolean isHomeScreenLoaded() {
        return isElementDisplayed(btnLoginScreen);
    }
}
