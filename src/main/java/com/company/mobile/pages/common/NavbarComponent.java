package com.company.mobile.pages.common;

import com.company.mobile.pages.BasePage;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class NavbarComponent extends BasePage {

    private final By btnMenu = AppiumBy.accessibilityId("menu-button");
    private final By btnNotifications = AppiumBy.accessibilityId("notification-icon");
    private final By txtTitle = AppiumBy.accessibilityId("header-title");

    public NavbarComponent clickMenu() {
        click(btnMenu, "Menu Button");
        return this;
    }

    public NavbarComponent clickNotifications() {
        click(btnNotifications, "Notifications Button");
        return this;
    }

    public String getHeaderTitle() {
        return getText(txtTitle, com.company.mobile.enums.WaitStrategy.VISIBLE);
    }
}
