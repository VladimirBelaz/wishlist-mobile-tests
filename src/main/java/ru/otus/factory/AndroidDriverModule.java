package ru.otus.factory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;

public class AndroidDriverModule extends AbstractModule {

    @Provides
    private WebDriver webDriver (AndroidDriverFactory factory) {
        return factory.create();
    }

    @Provides
    @Singleton
    private Capabilities capabilities() {
        return new UiAutomator2Options()
                .setApp("http://wiremock:8080/wishlist.apk")
                .fullReset()
                .clearDeviceLogsOnStart();
    }

}
