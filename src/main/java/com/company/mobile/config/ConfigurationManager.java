package com.company.mobile.config;

import org.aeonbits.owner.ConfigCache;

public final class ConfigurationManager {

    private ConfigurationManager() {}

    public static FrameworkConfig getConfiguration() {
        return ConfigCache.getOrCreate(FrameworkConfig.class);
    }
}
