package com.company.mobile.enums;

public enum MobileContext {
    NATIVE("NATIVE_APP"),
    WEBVIEW("WEBVIEW");

    private final String value;

    MobileContext(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
