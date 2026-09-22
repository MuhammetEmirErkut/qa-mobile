package com.company.mobile.pages.ios;

import com.company.mobile.pages.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class IOSLoginPage extends BasePage {

    private final By inputUsername = AppiumBy.accessibilityId("username-input");
    private final By inputPassword = AppiumBy.accessibilityId("password-input");
    private final By btnLogin = AppiumBy.accessibilityId("login-button");
    private final By txtErrorMessage = AppiumBy.iOSClassChain("**/XCUIElementTypeStaticText[`name == 'error_message'`]");
    private final By txtWelcomeHeader = AppiumBy.iOSNsPredicateString("name == 'welcome_header' AND visible == 1");

    public IOSLoginPage enterUsername(String username) {
        sendKeys(inputUsername, username, "Username Field");
        return this;
    }

    public IOSLoginPage enterPassword(String password) {
        sendKeys(inputPassword, password, "Password Field");
        return this;
    }

    public IOSLoginPage clickLogin() {
        click(btnLogin, "Login Button");
        return this;
    }

    public String getErrorMessage() {
        return getText(txtErrorMessage, com.company.mobile.enums.WaitStrategy.VISIBLE);
    }

    public boolean isWelcomeHeaderDisplayed() {
        return isElementDisplayed(txtWelcomeHeader);
    }
}
