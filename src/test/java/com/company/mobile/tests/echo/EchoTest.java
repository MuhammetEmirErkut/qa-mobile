package com.company.mobile.tests.echo;

import com.company.mobile.base.BaseTest;
import com.company.mobile.pages.EchoScreen;
import com.company.mobile.pages.HomeScreen;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

@Epic("Core Features")
@Feature("Echo Box")
public class EchoTest extends BaseTest {

    @Test(description = "Mesaj kaydetme ve ekranda doğrulama senaryosu")
    @Story("Echo Message Flow")
    @Severity(SeverityLevel.NORMAL)
    @Description("Kullanıcı bir mesaj yazıp kaydettiğinde, kaydedilen mesaj ekranda görünmelidir.")
    public void testSaveMessage() {
        String testMessage = "Appium Test Automation Message";

        HomeScreen homeScreen = new HomeScreen();
        EchoScreen echoScreen = homeScreen.navigateToEchoScreen();

        echoScreen.enterMessage(testMessage)
                  .clickSave();

        String savedText = echoScreen.getSavedMessage();
        Assertions.assertThat(savedText)
                .as("Kaydedilen metin girilen metin ile birebir aynı olmalıdır.")
                .isEqualTo(testMessage);
    }
}
