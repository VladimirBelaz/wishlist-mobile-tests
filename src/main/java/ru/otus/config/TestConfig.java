package ru.otus.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestConfig {

    public static String appUrl() {
        return System.getProperty(
                "appUrl",
                "http://wiremock:8080/wishlist.apk"
        );
    }

    public static String appiumHost() {
        return System.getProperty(
                "appiumHost",
                "127.0.0.1"
        );
    }

    public static String databaseUrl() {
        return System.getProperty("databaseUrl");
    }

    public static String databaseUserName() {
        return System.getProperty("databaseUserName");
    }

    public static String databasePassword() {
        return System.getProperty("databasePassword");
    }
}