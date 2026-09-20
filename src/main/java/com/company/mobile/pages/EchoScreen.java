package com.company.mobile.pages;

import com.company.mobile.enums.WaitStrategy;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class EchoScreen extends BasePage {

    private final By inputMessage = AppiumBy.accessibilityId("messageInput");
    private final By btnSave = AppiumBy.accessibilityId("messageSaveBtn");
    private final By txtSavedMessage = By.xpath("//*[@name='savedMessage' or @resource-id='savedMessage' or @content-desc='savedMessage']");

    public EchoScreen enterMessage(String message) {
        sendKeys(inputMessage, message, "Mesaj Giriş Alanı");
        return this;
    }

    public EchoScreen clickSave() {
        click(btnSave, "Kaydet Butonu");
        return this;
    }

    public String getSavedMessage() {
        return getText(txtSavedMessage, WaitStrategy.VISIBLE);
    }
}
