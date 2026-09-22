# 📱 Appium Capabilities & Inspector Configurations

This directory contains W3C-compliant Desired Capabilities configuration templates used for both **automated test execution** and **Appium Inspector** sessions.

---

## 🍏 iOS Simulator (TheApp.app)

**Dosya**: [`ios-caps.json`](file:///Users/erkut/projects/qa-mobile/src/test/resources/caps/ios-caps.json)

### Appium Inspector JSON Representation:
```json
{
  "platformName": "iOS",
  "appium:automationName": "XCUITest",
  "appium:deviceName": "iPhone 17 Pro",
  "appium:app": "/Users/erkut/projects/qa-mobile/apps/TheApp.app",
  "appium:bundleId": "com.appiumpro.the_app",
  "appium:noReset": false,
  "appium:wdaLaunchTimeout": 120000,
  "appium:newCommandTimeout": 180,
  "appium:shouldTerminateApp": true
}
```

---

## 🤖 Android Emulator (TheApp.apk)

**Dosya**: [`android-caps.json`](file:///Users/erkut/projects/qa-mobile/src/test/resources/caps/android-caps.json)

### Appium Inspector JSON Representation:
```json
{
  "platformName": "Android",
  "appium:automationName": "UiAutomator2",
  "appium:deviceName": "Medium_Phone",
  "appium:app": "/Users/erkut/projects/qa-mobile/apps/TheApp.apk",
  "appium:appPackage": "com.appiumpro.the_app",
  "appium:appActivity": "com.appiumpro.the_app.MainActivity",
  "appium:autoGrantPermissions": true,
  "appium:chromedriverAutodownload": true,
  "appium:disableWindowAnimation": true,
  "appium:newCommandTimeout": 180
}
```

---

## 🌐 Mobil Web (Cross-Browser)

### iOS Mobile Safari:
```json
{
  "platformName": "iOS",
  "appium:automationName": "XCUITest",
  "appium:deviceName": "iPhone 17 Pro",
  "browserName": "Safari"
}
```

### Android Mobile Chrome:
```json
{
  "platformName": "Android",
  "appium:automationName": "UiAutomator2",
  "appium:deviceName": "Medium_Phone",
  "browserName": "Chrome",
  "appium:chromedriverAutodownload": true
}
```
