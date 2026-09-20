package com.company.mobile.constants;

import java.io.File;
import java.time.Duration;

public final class FrameworkConstants {

    private FrameworkConstants() {}

    public static final String USER_DIR = System.getProperty("user.dir");
    public static final String RESOURCES_PATH = USER_DIR + File.separator + "src" + File.separator + "test" + File.separator + "resources";
    public static final String CONFIG_FILE_PATH = RESOURCES_PATH + File.separator + "config.properties";
    public static final String CAPS_PATH = RESOURCES_PATH + File.separator + "caps";
    public static final String TEST_DATA_PATH = RESOURCES_PATH + File.separator + "testdata";
    public static final String APPS_PATH = USER_DIR + File.separator + "apps";

    public static final Duration EXPLICIT_WAIT_TIMEOUT = Duration.ofSeconds(15);
    public static final Duration POLLING_INTERVAL = Duration.ofMillis(500);
    public static final Duration APPIUM_COMMAND_TIMEOUT = Duration.ofSeconds(180);
    public static final Duration WDA_LAUNCH_TIMEOUT = Duration.ofSeconds(120);

    public static final int MAX_RETRY_COUNT = 1;
}
