package com.company.mobile.pages;

import com.company.mobile.enums.WaitStrategy;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class LoginScreen extends BasePage {

    private final By inputUsername = AppiumBy.accessibilityId("username");
    private final By inputPassword = AppiumBy.accessibilityId("password");
    private final By btnLogin = AppiumBy.accessibilityId("loginBtn");
    private final By txtStatusMessage = AppiumBy.xpath("//*[contains(@text, 'Invalid') or contains(@text, 'logged in') or contains(@label, 'Invalid') or contains(@label, 'logged in') or contains(@value, 'Invalid') or contains(@value, 'logged in')]");
    private final By btnLogout = AppiumBy.accessibilityId("Logout");

    public LoginScreen enterUsername(String username) {
        sendKeys(inputUsername, username, "Username Input Field");
        return this;
    }

    public LoginScreen enterPassword(String password) {
        sendKeys(inputPassword, password, "Password Input Field");
        return this;
    }

    public LoginScreen clickLogin() {
        click(btnLogin, "Login Button");
        return this;
    }

    public LoginScreen performLogin(String username, String password) {
        return enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }

    public String getStatusMessage() {
        try {
            // Check for native alert popup dialog first
            org.openqa.selenium.Alert alert = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            alert.accept();
            return alertText;
        } catch (Exception e) {
            // Check for in-view element or dialog text message
            By fallbackLocator = By.xpath("//*[@resource-id='android:id/message' or @resource-id='android:id/alertTitle' or contains(@text, 'Invalid') or contains(@text, 'logged in') or contains(@label, 'Invalid') or contains(@label, 'logged in') or contains(@value, 'Invalid') or contains(@value, 'logged in')]");
            return getText(fallbackLocator, WaitStrategy.PRESENCE);
        }
    }

    public boolean isLogoutButtonDisplayed() {
        dismissSystemAlertIfPresent();
        return isElementDisplayed(btnLogout);
    }

    public LoginScreen clickLogout() {
        dismissSystemAlertIfPresent();
        click(btnLogout, "Logout Button");
        return this;
    }

    public boolean isLoginButtonDisplayed() {
        dismissSystemAlertIfPresent();
        return isElementDisplayed(btnLogin);
    }
}
