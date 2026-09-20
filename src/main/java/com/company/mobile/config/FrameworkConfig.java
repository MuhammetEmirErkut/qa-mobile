package com.company.mobile.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:${user.dir}/src/test/resources/config.properties"
})
public interface FrameworkConfig extends Config {

    @Key("appium.server.url")
    @DefaultValue("http://127.0.0.1:4723")
    String appiumServerUrl();

    @Key("platform.name")
    @DefaultValue("ANDROID")
    String platformName();

    @Key("android.device.name")
    @DefaultValue("Pixel_7_API_34")
    String androidDeviceName();

    @Key("android.udid")
    @DefaultValue("emulator-5554")
    String androidUdid();

    @Key("android.app.package")
    @DefaultValue("com.example.testapp")
    String androidAppPackage();

    @Key("android.app.activity")
    @DefaultValue("com.example.testapp.MainActivity")
    String androidAppActivity();

    @Key("android.app.file")
    @DefaultValue("test.apk")
    String androidAppFile();

    @Key("ios.device.name")
    @DefaultValue("iPhone 15 Pro")
    String iosDeviceName();

    @Key("ios.platform.version")
    @DefaultValue("17.4")
    String iosPlatformVersion();

    @Key("ios.udid")
    @DefaultValue("auto")
    String iosUdid();

    @Key("ios.bundle.id")
    @DefaultValue("com.example.testapp")
    String iosBundleId();

    @Key("ios.app.file")
    @DefaultValue("test.app")
    String iosAppFile();

    @Key("retry.failed.tests")
    @DefaultValue("true")
    boolean retryFailedTests();
}
