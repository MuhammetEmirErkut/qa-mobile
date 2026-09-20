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

    @Test(description = "Başarılı kullanıcı girişi ve çıkış senaryosu")
    public void testValidLoginAndLogout() {
        HomeScreen homeScreen = new HomeScreen();
        LoginScreen loginScreen = homeScreen.navigateToLoginScreen();

        loginScreen.performLogin("alice", "mypassword");

        // 1. Logout butonu göründü mü?
        Assertions.assertThat(loginScreen.isLogoutButtonDisplayed())
                .as("Giriş başarılı olduktan sonra Logout butonu görünmelidir.")
                .isTrue();

        // 2. Logout butonuna tıkla
        loginScreen.clickLogout();

        // 3. Tekrar giriş ekranına (Login Butonuna) dönüldü mü doğrula
        Assertions.assertThat(loginScreen.isLoginButtonDisplayed())
                .as("Çıkış yapıldıktan sonra tekrar Login butonu görünmelidir.")
                .isTrue();
    }


    @Test(description = "Geçersiz kimlik bilgileriyle başarısız giriş senaryosu")
    @Story("Negatif Giriş Senaryosu")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Yanlış şifre girildiğinde hata mesajı görüntülenmelidir.")
    public void testInvalidLoginShowsError() {
        HomeScreen homeScreen = new HomeScreen();
        LoginScreen loginScreen = homeScreen.navigateToLoginScreen();

        loginScreen.performLogin("alice", "wrong_password");

        String statusMessage = loginScreen.getStatusMessage();
        Assertions.assertThat(statusMessage)
                .as("Hata mesajı geçersiz giriş uyarısını içermelidir.")
                .containsIgnoringCase("Invalid");
    }
}
