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
        sendKeys(inputUsername, username, "Kullanıcı Adı");
        return this;
    }

    public LoginScreen enterPassword(String password) {
        sendKeys(inputPassword, password, "Şifre");
        return this;
    }

    public LoginScreen clickLogin() {
        click(btnLogin, "Giriş Butonu");
        return this;
    }

    public LoginScreen performLogin(String username, String password) {
        return enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }

    public String getStatusMessage() {
        return getText(txtStatusMessage, WaitStrategy.VISIBLE);
    }

    public boolean isLogoutButtonDisplayed() {
        dismissSystemAlertIfPresent();
        return isElementDisplayed(btnLogout);
    }

    public LoginScreen clickLogout() {
        dismissSystemAlertIfPresent();
        click(btnLogout, "Çıkış Yap (Logout) Butonu");
        return this;
    }

    public boolean isLoginButtonDisplayed() {
        dismissSystemAlertIfPresent();
        return isElementDisplayed(btnLogin);
    }
}
