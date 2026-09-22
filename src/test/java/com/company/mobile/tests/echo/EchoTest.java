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

    @Test(description = "Verify saving and displaying echo message on screen")
    @Story("Echo Message Flow")
    @Severity(SeverityLevel.NORMAL)
    @Description("When a user enters and saves a message, the saved message must be visible on the screen.")
    public void testSaveMessage() {
        String testMessage = "Appium Test Automation Message";

        HomeScreen homeScreen = new HomeScreen();
        EchoScreen echoScreen = homeScreen.navigateToEchoScreen();

        echoScreen.enterMessage(testMessage)
                  .clickSave();

        String savedText = echoScreen.getSavedMessage();
        Assertions.assertThat(savedText)
                .as("Saved text should match the entered text exactly.")
                .isEqualTo(testMessage);
    }
}
