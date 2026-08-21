package ru.otus.config;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;

@Singleton
@Getter
public class TestConfig {

    private final String appiumHost;
    private final String databaseUrl;
    private final String databaseUserName;
    private final String databasePassword;
    private final String apkStorageHost;

    @Inject
    public TestConfig() {
        this.appiumHost = System.getProperty("appiumHost", "127.0.0.1");
        this.databaseUrl = System.getProperty("databaseUrl");
        this.databaseUserName = System.getProperty("databaseUserName");
        this.databasePassword = System.getProperty("databasePassword");
        this.apkStorageHost = System.getProperty("apkStorageHost", "http://wiremock:8080");
    }

    public String getFullAppUrl() {
        return apkStorageHost + "/wishlist.apk";
    }
}