package com.company.mobile.tests.auth;

import com.company.mobile.base.BaseTest;
import com.company.mobile.pages.HomeScreen;
import com.company.mobile.pages.LoginScreen;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Epic("Authentication Module")
@Feature("Login Tests")
public class LoginTest extends BaseTest {

    @Test(description = "Verify successful user login and logout flow")
    @Story("Positive Authentication Flow")
    @Severity(SeverityLevel.BLOCKER)
    @Description("User should be able to log in with valid credentials and successfully log out.")
    public void testValidLoginAndLogout() {
        HomeScreen homeScreen = new HomeScreen();
        LoginScreen loginScreen = homeScreen.navigateToLoginScreen();

        loginScreen.performLogin("alice", "mypassword");

        // 1. Verify Logout button is displayed
        Assertions.assertThat(loginScreen.isLogoutButtonDisplayed())
                .as("Logout button should be displayed after successful login.")
                .isTrue();

        // 2. Click Logout button
        loginScreen.clickLogout();

        // 3. Verify redirected back to Login screen (Login button is displayed)
        Assertions.assertThat(loginScreen.isLoginButtonDisplayed())
                .as("Login button should be displayed after logout.")
                .isTrue();
    }


    @Test(description = "Verify invalid credentials show appropriate error message")
    @Story("Negative Authentication Flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Appropriate error message should be displayed when invalid password is provided.")
    public void testInvalidLoginShowsError() {
        HomeScreen homeScreen = new HomeScreen();
        LoginScreen loginScreen = homeScreen.navigateToLoginScreen();

        loginScreen.performLogin("alice", "wrong_password");

        String statusMessage = loginScreen.getStatusMessage();
        Assertions.assertThat(statusMessage)
                .as("Status message should indicate invalid credentials.")
                .containsIgnoringCase("Invalid");
    }
}
