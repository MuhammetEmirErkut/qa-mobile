package com.company.mobile.pages;

import com.company.mobile.constants.FrameworkConstants;
import com.company.mobile.drivers.DriverManager;
import com.company.mobile.enums.WaitStrategy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public abstract class BasePage {

    protected final Logger log = LogManager.getLogger(this.getClass());
    protected final AppiumDriver driver;
    protected final FluentWait<AppiumDriver> wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new FluentWait<>(driver)
                .withTimeout(FrameworkConstants.EXPLICIT_WAIT_TIMEOUT)
                .pollingEvery(FrameworkConstants.POLLING_INTERVAL)
                .ignoring(Exception.class);
        PageFactory.initElements(new AppiumFieldDecorator(driver, FrameworkConstants.EXPLICIT_WAIT_TIMEOUT), this);
    }

    protected WebElement waitForElement(By locator, WaitStrategy waitStrategy) {
        return switch (waitStrategy) {
            case CLICKABLE -> wait.until(ExpectedConditions.elementToBeClickable(locator));
            case PRESENCE -> wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            case VISIBLE -> wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            case NONE -> driver.findElement(locator);
        };
    }

    protected void click(By locator, WaitStrategy waitStrategy, String elementName) {
        WebElement element = waitForElement(locator, waitStrategy);
        element.click();
        log.info("'{}' elementine tıklandı.", elementName);
    }

    protected void click(By locator, String elementName) {
        click(locator, WaitStrategy.CLICKABLE, elementName);
    }

    protected void sendKeys(By locator, String text, WaitStrategy waitStrategy, String elementName) {
        WebElement element = waitForElement(locator, waitStrategy);
        element.click();
        element.clear();
        element.sendKeys(text);
        log.info("'{}' alanına '{}' metni yazıldı.", elementName, text);
    }

    protected void sendKeys(By locator, String text, String elementName) {
        sendKeys(locator, text, WaitStrategy.VISIBLE, elementName);
    }

    protected String getText(By locator, WaitStrategy waitStrategy) {
        WebElement element = waitForElement(locator, waitStrategy);
        return element.getText();
    }

    public void dismissSystemAlertIfPresent() {
        try {
            // Android System Dialog Buttons (OS compatibility, permission or battery warnings)
            By androidAlertBtn = By.xpath("//*[@resource-id='android:id/button1' and (contains(@text, 'OK') or contains(@text, 'Close')) and ../..//*[@resource-id='android:id/alertTitle' or contains(@text, 'built for an older')]] | //*[@text=\"Don't Show Again\"]");
            if (!driver.findElements(androidAlertBtn).isEmpty()) {
                driver.findElement(androidAlertBtn).click();
                log.info("Android sistem uyarısı kapatıldı.");
            }
        } catch (Exception ignored) {}

        try {
            // iOS System Dialog & Keychain AutoFill Prompt Buttons (Not Now / Şimdi Değil / Close)
            By iosAlertBtn = By.xpath("//XCUIElementTypeButton[@name='Not Now' or @name='Şimdi Değil' or @name='Cancel' or @name='Vazgeç' or @name='Close' or @name='Kapat']");
            if (!driver.findElements(iosAlertBtn).isEmpty()) {
                driver.findElement(iosAlertBtn).click();
                log.info("iOS sistem/parola uyarısı kapatıldı.");
            }
        } catch (Exception ignored) {}
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            return waitForElement(locator, WaitStrategy.VISIBLE).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
